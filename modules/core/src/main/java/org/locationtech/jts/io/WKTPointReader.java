/*
 * Copyright (c) 2016 Vivid Solutions.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.locationtech.jts.io;


import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.PointCoordinateSequence;
import org.locationtech.jts.geom.impl.PointCoordinateSequenceFactory;
import org.locationtech.jts.util.Assert;
import org.locationtech.jts.util.AssertionFailedException;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Converts a geometry in Well-Known Text format to a {@link Geometry}.
 * <p>
 * <code>WKTReader</code> supports
 * extracting <code>Geometry</code> objects from either {@link Reader}s or
 *  {@link String}s. This allows it to function as a parser to read <code>Geometry</code>
 *  objects from text blocks embedded in other data formats (e.g. XML). <P>
 * <p>
 *  A <code>WKTReader</code> is parameterized by a <code>GeometryFactory</code>,
 *  to allow it to create <code>Geometry</code> objects of the appropriate
 *  implementation. In particular, the <code>GeometryFactory</code>
 *  determines the <code>PrecisionModel</code> and <code>SRID</code> that is
 *  used. <P>
 *
 *  The <code>WKTReader</code> converts all input numbers to the precise
 *  internal representation.
 *
 * <h3>Notes:</h3>
 * <ul>
 * <li>Keywords are case-insensitive.
 * <li>The reader supports non-standard "LINEARRING" tags.
 * <li>The reader uses <tt>Double.parseDouble</tt> to perform the conversion of ASCII
 * numbers to floating point.  This means it supports the Java
 * syntax for floating point literals (including scientific notation).
 * </ul>
 *
 * <h3>Syntax</h3>
 * The following syntax specification describes the version of Well-Known Text
 * supported by JTS.
 * (The specification uses a syntax language similar to that used in
 * the C and Java language specifications.)
 * <p>
 * As of version 1.15, JTS can read (but not write) WKT Strings including Z, M or ZM
 * in the name of the geometry type (ex. POINT Z, LINESTRINGZM).
 * Note that it only makes the reader more flexible, but JTS could already read
 * 3D coordinates from WKT String and still can't read 4D coordinates.
 *
 * <blockquote><pre>
 * <i>WKTGeometry:</i> one of<i>
 *
 *       WKTPoint  WKTLineString  WKTLinearRing  WKTPolygon
 *       WKTMultiPoint  WKTMultiLineString  WKTMultiPolygon
 *       WKTGeometryCollection</i>
 *
 * <i>WKTPoint:</i> <b>POINT</b><i>[Dimension]</i> <b>( </b><i>Coordinate</i> <b>)</b>
 *
 * <i>WKTLineString:</i> <b>LINESTRING</b><i>[Dimension]</i> <i>CoordinateSequence</i>
 *
 * <i>WKTLinearRing:</i> <b>LINEARRING</b><i>[Dimension]</i> <i>CoordinateSequence</i>
 *
 * <i>WKTPolygon:</i> <b>POLYGON</b><i>[Dimension]</i> <i>CoordinateSequenceList</i>
 *
 * <i>WKTMultiPoint:</i> <b>MULTIPOINT</b><i>[Dimension]</i> <i>CoordinateSingletonList</i>
 *
 * <i>WKTMultiLineString:</i> <b>MULTILINESTRING</b><i>[Dimension]</i> <i>CoordinateSequenceList</i>
 *
 * <i>WKTMultiPolygon:</i>
 *         <b>MULTIPOLYGON</b><i>[Dimension]</i> <b>(</b> <i>CoordinateSequenceList {</i> , <i>CoordinateSequenceList }</i> <b>)</b>
 *
 * <i>WKTGeometryCollection: </i>
 *         <b>GEOMETRYCOLLECTION</b><i>[Dimension]</i> <b> (</b> <i>WKTGeometry {</i> , <i>WKTGeometry }</i> <b>)</b>
 *
 * <i>CoordinateSingletonList:</i>
 *         <b>(</b> <i>CoordinateSingleton {</i> <b>,</b> <i>CoordinateSingleton }</i> <b>)</b>
 *         | <b>EMPTY</b>
 *         
 * <i>CoordinateSingleton:</i>
 *         <b>(</b> <i>Coordinate</i> <b>)</b>
 *         | <b>EMPTY</b>
 *
 * <i>CoordinateSequenceList:</i>
 *         <b>(</b> <i>CoordinateSequence {</i> <b>,</b> <i>CoordinateSequence }</i> <b>)</b>
 *         | <b>EMPTY</b>
 *
 * <i>CoordinateSequence:</i>
 *         <b>(</b> <i>Coordinate {</i> , <i>Coordinate }</i> <b>)</b>
 *         | <b>EMPTY</b>
 *
 * <i>Coordinate:
 *         Number Number Number<sub>opt</sub></i>
 *
 * <i>Number:</i> A Java-style floating-point number (including <tt>NaN</tt>, with arbitrary case)
 *
 * <i>Dimension:</i>
 *         <b>Z</b>|<b> Z</b>|<b>M</b>|<b> M</b>|<b>ZM</b>|<b> ZM</b>
 *
 * </pre></blockquote>
 *
 *
 *@version 1.7
 * @see WKTWriter
 */
public class WKTPointReader
{
  private static final String EMPTY = "EMPTY";
  private static final String COMMA = ",";
  private static final String L_PAREN = "(";
  private static final String R_PAREN = ")";
  private static final String NAN_SYMBOL = "NaN";

  private GeometryFactory geometryFactory;
  private PrecisionModel precisionModel;
  private StreamTokenizer tokenizer;
  // Not yet used (useful if we want to read Z, M and ZM WKT)
  private boolean z;
  private boolean m;

  /**
   * Creates a reader that creates objects using the default {@link GeometryFactory}.
   */
  public WKTPointReader() {
    this(new GeometryFactory());
  }

  /**
   *  Creates a reader that creates objects using the given
   *  {@link GeometryFactory}.
   *
   *@param  geometryFactory  the factory used to create <code>Geometry</code>s.
   */
  public WKTPointReader(GeometryFactory geometryFactory) {
    this.geometryFactory = geometryFactory;
    precisionModel = geometryFactory.getPrecisionModel();
  }

  /**
   * Reads a Well-Known Text representation of a {@link Geometry}
   * from a {@link String}.
   *
   * @param wellKnownText
   *            one or more &lt;Geometry Tagged Text&gt; strings (see the OpenGIS
   *            Simple Features Specification) separated by whitespace
   * @return a <code>Geometry</code> specified by <code>wellKnownText</code>
   * @throws ParseException
   *             if a parsing problem occurs
   */
  public Point read(String wellKnownText) throws ParseException {
    StringReader reader = new StringReader(wellKnownText);
    try {
      return read(reader);
    }
    finally {
      reader.close();
    }
  }

  /**
   * Reads a Well-Known Text representation of a {@link Geometry}
   * from a {@link Reader}.
   *
   *@param  reader           a Reader which will return a &lt;Geometry Tagged Text&gt;
   *      string (see the OpenGIS Simple Features Specification)
   *@return                  a <code>Geometry</code> read from <code>reader</code>
   *@throws  ParseException  if a parsing problem occurs
   */
  public Point read(Reader reader) throws ParseException {
    tokenizer = new StreamTokenizer(reader);
    // set tokenizer to NOT parse numbers
    tokenizer.resetSyntax();
    tokenizer.wordChars('a', 'z');
    tokenizer.wordChars('A', 'Z');
    tokenizer.wordChars(128 + 32, 255);
    tokenizer.wordChars('0', '9');
    tokenizer.wordChars('-', '-');
    tokenizer.wordChars('+', '+');
    tokenizer.wordChars('.', '.');
    tokenizer.whitespaceChars(0, ' ');
    tokenizer.commentChar('#');
    z = false;
    m = false;

    try {
      return readGeometryTaggedText();
    }
    catch (IOException e) {
      throw new ParseException(e.toString());
    }
  }

  private Coordinate getPreciseCoordinate()
      throws IOException, ParseException
  {
    Coordinate coord = new Coordinate();
    coord.x = getNextNumber();
    coord.y = getNextNumber();
    if (isNumberNext()) {
        coord.z = getNextNumber();
    }
    if (isNumberNext()) {
      getNextNumber(); // ignore M value
    }
    precisionModel.makePrecise(coord);
    return coord;
  }

  private boolean isNumberNext() throws IOException {
    int type = tokenizer.nextToken();
    tokenizer.pushBack();
    return type == StreamTokenizer.TT_WORD;
  }

  /**
   * Parses the next number in the stream.
   * Numbers with exponents are handled.
   * <tt>NaN</tt> values are handled correctly, and
   * the case of the "NaN" symbol is not significant. 
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next token must be a number.
   *@return                  the next number in the stream
   *@throws  ParseException  if the next token is not a valid number
   *@throws  IOException     if an I/O error occurs
   */
  private double getNextNumber() throws IOException,
      ParseException {
    int type = tokenizer.nextToken();
    switch (type) {
      case StreamTokenizer.TT_WORD:
      {
      	if (tokenizer.sval.equalsIgnoreCase(NAN_SYMBOL)) {
      		return Double.NaN;
      	}
      	else {
	        try {
	          return Double.parseDouble(tokenizer.sval);
	        }
	        catch (NumberFormatException ex) {
	          parseErrorWithLine("Invalid number: " + tokenizer.sval);
	        }
      	}
      }
    }
    parseErrorExpected("number");
    return 0.0;
  }
  /**
   *  Returns the next EMPTY or L_PAREN in the stream as uppercase text.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next token must be EMPTY or L_PAREN.
   *@return                  the next EMPTY or L_PAREN in the stream as uppercase
   *      text.
   *@throws  ParseException  if the next token is not EMPTY or L_PAREN
   *@throws  IOException     if an I/O error occurs
   */
  private String getNextEmptyOrOpener() throws IOException, ParseException {
    String nextWord = getNextWord();
    if (nextWord.equalsIgnoreCase("Z")) {
      z = true;
      nextWord = getNextWord();
    }
    else if (nextWord.equalsIgnoreCase("M")) {
      m = true;
      nextWord = getNextWord();
    }
    else if (nextWord.equalsIgnoreCase("ZM")) {
      z = true;
      m = true;
      nextWord = getNextWord();
    }
    if (nextWord.equals(EMPTY) || nextWord.equals(L_PAREN)) {
      return nextWord;
    }
    parseErrorExpected(EMPTY + " or " + L_PAREN);
    return null;
  }

  /**
   *  Returns the next R_PAREN or COMMA in the stream.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next token must be R_PAREN or COMMA.
   *@return                  the next R_PAREN or COMMA in the stream
   *@throws  ParseException  if the next token is not R_PAREN or COMMA
   *@throws  IOException     if an I/O error occurs
   */
  private String getNextCloserOrComma() throws IOException, ParseException {
    String nextWord = getNextWord();
    if (nextWord.equals(COMMA) || nextWord.equals(R_PAREN)) {
      return nextWord;
    }
    parseErrorExpected(COMMA + " or " + R_PAREN);
    return null;
  }

  /**
   *  Returns the next R_PAREN in the stream.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next token must be R_PAREN.
   *@return                  the next R_PAREN in the stream
   *@throws  ParseException  if the next token is not R_PAREN
   *@throws  IOException     if an I/O error occurs
   */
  private String getNextCloser() throws IOException, ParseException {
    String nextWord = getNextWord();
    if (nextWord.equals(R_PAREN)) {
      return nextWord;
    }
    parseErrorExpected(R_PAREN);
    return null;
  }

  /**
   *  Returns the next word in the stream.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next token must be a word.
   *@return                  the next word in the stream as uppercase text
   *@throws  ParseException  if the next token is not a word
   *@throws  IOException     if an I/O error occurs
   */
  private String getNextWord() throws IOException, ParseException {
    int type = tokenizer.nextToken();
    switch (type) {
    case StreamTokenizer.TT_WORD:

      String word = tokenizer.sval;
      if (word.equalsIgnoreCase(EMPTY))
          return EMPTY;
      return word;

    case '(': return L_PAREN;
    case ')': return R_PAREN;
    case ',': return COMMA;
    }
    parseErrorExpected("word");
    return null;
  }

  /**
   *  Returns the next word in the stream.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next token must be a word.
   *@return                  the next word in the stream as uppercase text
   *@throws  ParseException  if the next token is not a word
   *@throws  IOException     if an I/O error occurs
   */
  private String lookaheadWord() throws IOException, ParseException {
  	String nextWord = getNextWord();
  	tokenizer.pushBack();
    return nextWord;
  }

  /**
   * Throws a formatted ParseException reporting that the current token
   * was unexpected.
   *
   * @param expected a description of what was expected
   * @throws ParseException
   * @throws AssertionFailedException if an invalid token is encountered
   */
  private void parseErrorExpected(String expected)
      throws ParseException
  {
    // throws Asserts for tokens that should never be seen
    if (tokenizer.ttype == StreamTokenizer.TT_NUMBER)
      Assert.shouldNeverReachHere("Unexpected NUMBER token");
    if (tokenizer.ttype == StreamTokenizer.TT_EOL)
      Assert.shouldNeverReachHere("Unexpected EOL token");

    String tokenStr = tokenString();
    parseErrorWithLine("Expected " + expected + " but found " + tokenStr);
  }

  private void parseErrorWithLine(String msg)
  throws ParseException
  {
    throw new ParseException(msg + " (line " + tokenizer.lineno() + ")");
  }
  
  /**
   * Gets a description of the current token
   *
   * @return a description of the current token
   */
  private String tokenString()
  {
    switch (tokenizer.ttype) {
      case StreamTokenizer.TT_NUMBER:
        return "<NUMBER>";
      case StreamTokenizer.TT_EOL:
        return "End-of-Line";
      case StreamTokenizer.TT_EOF: return "End-of-Stream";
      case StreamTokenizer.TT_WORD: return "'" + tokenizer.sval + "'";
    }
    return "'" + (char) tokenizer.ttype + "'";
  }

  /**
   *  Creates a <code>Geometry</code> using the next token in the stream.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next tokens must form a &lt;Geometry Tagged Text&gt;.
   *@return                  a <code>Geometry</code> specified by the next token
   *      in the stream
   *@throws  ParseException  if the coordinates used to create a <code>Polygon</code>
   *      shell and holes do not form closed linestrings, or if an unexpected
   *      token was encountered
   *@throws  IOException     if an I/O error occurs
   */
  private Point readGeometryTaggedText() throws IOException, ParseException {
    String type;
    
    try{
    	type = getNextWord().toUpperCase();
//      if (type.endsWith("Z")) z = true;
//      if (type.endsWith("M")) m = true;
    }catch(IOException e){
    	return null;
    }catch(ParseException e){
    	return null;
    }
    
    if (type.startsWith("POINT")) {
      return readPointText();
    }
    parseErrorWithLine("Unknown geometry type: " + type);
    // should never reach here
    return null;
  }

  /**
   *  Creates a <code>Point</code> using the next token in the stream.
   *
   *@param  tokenizer        tokenizer over a stream of text in Well-known Text
   *      format. The next tokens must form a &lt;Point Text&gt;.
   *@return                  a <code>Point</code> specified by the next token in
   *      the stream
   *@throws  IOException     if an I/O error occurs
   *@throws  ParseException  if an unexpected token was encountered
   */
  private Point readPointText() throws IOException, ParseException {
    String nextToken = getNextEmptyOrOpener();
    if (nextToken.equals(EMPTY)) {
      return geometryFactory.createPoint();
    }
    CoordinateSequence ptCS = new PointCoordinateSequence(getNextNumber(), getNextNumber());
    Point point = geometryFactory.createPoint(ptCS);

    getNextCloser();
    return point;
  }
}


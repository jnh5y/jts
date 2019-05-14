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

package test.jts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.Ordinate;
import org.locationtech.jts.io.WKTReaderBreakBuild;

import junit.framework.TestCase;
import org.locationtech.jts.io.WKTReaderBreakBuild;

/**
 * A base class for Geometry tests which provides various utility methods.
 * 
 * @author mbdavis
 *
 */

public abstract class GeometryTestCase extends TestCase{

  final GeometryFactory geomFactory;
  
  final WKTReaderBreakBuild reader;

  protected GeometryTestCase(String name)
  {
    this(name, CoordinateArraySequenceFactory.instance());
  }

  protected GeometryTestCase(String name, CoordinateSequenceFactory coordinateSequenceFactory) {
    super(name);
    geomFactory = new GeometryFactory(coordinateSequenceFactory);
    reader = new WKTReaderBreakBuild(geomFactory);
  }

  protected void checkEqual(Geometry expected, Geometry actual) {
    Geometry actualNorm = actual.norm();
    Geometry expectedNorm = expected.norm();
    boolean equal = actualNorm.equalsExact(expectedNorm);
    if (! equal) {
      System.out.println("FAIL - Expected = " + expectedNorm
          + " actual = " + actualNorm );
    }
    assertTrue(equal);
  }

  protected void checkEqual(Collection expected, Collection actual) {
    checkEqual(toGeometryCollection(expected),toGeometryCollection(actual) );
  }

  GeometryCollection toGeometryCollection(Collection geoms) {
    return geomFactory.createGeometryCollection(GeometryFactory.toGeometryArray(geoms));
  }
  
  /**
   * Reads a {@link Geometry} from a WKT string using a custom {@link GeometryFactory}.
   *  
   * @param geomFactory the custom factory to use
   * @param wkt the WKT string
   * @return the geometry read
   */
  protected static Geometry read(GeometryFactory geomFactory, String wkt) {
    WKTReaderBreakBuild reader = new WKTReaderBreakBuild(geomFactory);
    try {
       return reader.read(wkt);
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  protected Geometry read(String wkt) {
    return read(reader, wkt);
  }

  public static Geometry read(WKTReaderBreakBuild reader, String wkt) {
    try {
      return reader.read(wkt);
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
  protected List readList(String[] wkt) {
    ArrayList geometries = new ArrayList(wkt.length);
    for (int i = 0; i < wkt.length; i++) {
      geometries.add(read(wkt[i]));
    }
    return geometries;
  }

  public static List readList(WKTReaderBreakBuild reader, String[] wkt) {
    ArrayList geometries = new ArrayList(wkt.length);
    for (int i = 0; i < wkt.length; i++) {
      geometries.add(read(reader, wkt[i]));
    }
    return geometries;
  }

  /**
   * Gets a {@link WKTReaderBreakBuild} to read geometries from WKT with expected ordinates.
   *
   * @param ordinateFlags a set of expected ordinates
   * @return a {@code WKTReader}
   */
  public static WKTReaderBreakBuild getWKTReader(EnumSet<Ordinate> ordinateFlags) {
    return getWKTReader(ordinateFlags, new PrecisionModel());
  }

  /**
   * Gets a {@link WKTReaderBreakBuild} to read geometries from WKT with expected ordinates.
   *
   * @param ordinateFlags a set of expected ordinates
   * @param scale         a scale value to create a {@link PrecisionModel}
   *
   * @return a {@code WKTReader}
   */
  public static WKTReaderBreakBuild getWKTReader(EnumSet<Ordinate> ordinateFlags, double scale) {
    return getWKTReader(ordinateFlags, new PrecisionModel(scale));
  }

  /**
   * Gets a {@link WKTReaderBreakBuild} to read geometries from WKT with expected ordinates.
   *
   * @param ordinateFlags a set of expected ordinates
   * @param precisionModel a precision model
   *
   * @return a {@code WKTReader}
   */
  public static WKTReaderBreakBuild getWKTReader(EnumSet<Ordinate> ordinateFlags, PrecisionModel precisionModel) {

    WKTReaderBreakBuild result;

    if (!ordinateFlags.contains(Ordinate.X)) ordinateFlags.add(Ordinate.X);
    if (!ordinateFlags.contains(Ordinate.Y)) ordinateFlags.add(Ordinate.Y);

    if (ordinateFlags.size() == 2)
    {
      result = new WKTReaderBreakBuild(new GeometryFactory(precisionModel, 0, CoordinateArraySequenceFactory.instance()));
      result.setIsOldJtsCoordinateSyntaxAllowed(false);
    }
    else if (ordinateFlags.contains(Ordinate.Z))
      result = new WKTReaderBreakBuild(new GeometryFactory(precisionModel, 0, CoordinateArraySequenceFactory.instance()));
    else if (ordinateFlags.contains(Ordinate.M)) {
      result = new WKTReaderBreakBuild(new GeometryFactory(precisionModel, 0,
              PackedCoordinateSequenceFactory.DOUBLE_FACTORY));
      result.setIsOldJtsCoordinateSyntaxAllowed(false);
    }
    else
      result = new WKTReaderBreakBuild(new GeometryFactory(precisionModel, 0, PackedCoordinateSequenceFactory.DOUBLE_FACTORY));

    return result;
  }

  /**
   * Checks two {@link CoordinateSequence}s for equality. The following items are checked:
   * <ul>
   *   <li>size</li><li>dimension</li><li>ordinate values</li>
   * </ul>

   * @param seq1 a sequence
   * @param seq2 another sequence
   * @return {@code true} if both sequences are equal
   */
  public static boolean checkEqual(CoordinateSequence seq1, CoordinateSequence seq2) {
    return checkEqual(seq1, seq2, 0d);
  }

  /**
   * Checks two {@link CoordinateSequence}s for equality. The following items are checked:
   * <ul>
   *   <li>size</li><li>dimension</li><li>ordinate values with {@code tolerance}</li>
   * </ul>

   * @param seq1 a sequence
   * @param seq2 another sequence
   * @return {@code true} if both sequences are equal
   */
  public static boolean checkEqual(CoordinateSequence seq1, CoordinateSequence seq2, double tolerance) {
    if (seq1.getDimension() != seq2.getDimension())
      return false;
    return checkEqual(seq1, seq2, seq1.getDimension(), tolerance);
  }

  /**
   * Checks two {@link CoordinateSequence}s for equality. The following items are checked:
   * <ul>
   *   <li>size</li><li>dimension up to {@code dimension}</li><li>ordinate values</li>
   * </ul>

   * @param seq1 a sequence
   * @param seq2 another sequence
   * @return {@code true} if both sequences are equal
   */
  public static boolean checkEqual(CoordinateSequence seq1, CoordinateSequence seq2, int dimension) {
    return checkEqual(seq1, seq2, dimension, 0d);
  }

  /**
   * Checks two {@link CoordinateSequence}s for equality. The following items are checked:
   * <ul>
   *   <li>size</li><li>dimension up to {@code dimension}</li><li>ordinate values with {@code tolerance}</li>
   * </ul>

   * @param seq1 a sequence
   * @param seq2 another sequence
   * @return {@code true} if both sequences are equal
   */
  public static boolean checkEqual(CoordinateSequence seq1, CoordinateSequence seq2, int dimension, double tolerance) {
    if (seq1 != null && seq2 == null) return false;
    if (seq1 == null && seq2 != null) return false;

    if (seq1.size() != seq2.size()) return false;

    if (seq1.getDimension() < dimension)
      throw new IllegalArgumentException("dimension too high for seq1");
    if (seq2.getDimension() < dimension)
      throw new IllegalArgumentException("dimension too high for seq2");

    for (int i = 0; i < seq1.size(); i++) {
      for (int j = 0; j < dimension; j++) {
        double val1 = seq1.getOrdinate(i, j);
        double val2 = seq2.getOrdinate(i, j);
        if (Double.isNaN(val1)) {
          if (!Double.isNaN(val2)) return false;
        }
        else if (Math.abs(val1 - val2) > tolerance)
          return false;
      }
    }

    return true;
  }

  /**
   * Gets a {@link CoordinateSequenceFactory} that can create sequences
   * for ordinates defined in the provided bit-pattern.
   * @param ordinateFlags a bit-pattern of ordinates
   * @return a {@code CoordinateSequenceFactory}
   */
  public static CoordinateSequenceFactory getCSFactory(EnumSet<Ordinate> ordinateFlags)
  {
    if (ordinateFlags.contains(Ordinate.M))
        return PackedCoordinateSequenceFactory.DOUBLE_FACTORY;

    return CoordinateArraySequenceFactory.instance();
  }
}

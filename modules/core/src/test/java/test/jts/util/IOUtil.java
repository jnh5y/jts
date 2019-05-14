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

package test.jts.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReaderBreakBuild;

public class IOUtil {
	  public static Geometry read(String wkt)
	  {
		  WKTReaderBreakBuild rdr = new WKTReaderBreakBuild();
	    try {
	      return rdr.read(wkt);
	    }
	    catch (ParseException ex) {
	      throw new RuntimeException(ex);
	    }
	  }

}

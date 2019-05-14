package org.locationtech.jtstest;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReaderBreakBuild;
import org.locationtech.jts.io.WKTReaderBreakBuild;

public class TestUtil {
  
  public static Geometry readWKT(String wkt) {
    WKTReaderBreakBuild reader = new WKTReaderBreakBuild();
    try {
      return reader.read(wkt);
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}

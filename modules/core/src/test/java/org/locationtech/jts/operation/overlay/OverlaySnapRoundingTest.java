package org.locationtech.jts.operation.overlay;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;

import test.jts.GeometryTestCase;

public class OverlaySnapRoundingTest extends GeometryTestCase {

  public OverlaySnapRoundingTest(String name) {
    super(name);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(OverlaySnapRoundingTest.class);
  }

  Geometry a = read("POLYGON ((2.4073917408015575 48.87286388272396, 2.4087603199086653 48.87735674585846, 2.410804066510286 48.877355501447255, 2.4108027911162724 48.87645676815331, 2.4128465021908547 48.876455488709546, 2.4128451914147386 48.875556754773875, 2.412163966209875 48.87555718514279, 2.41216331674309 48.87510781792284, 2.4114820974312012 48.87510824439347, 2.4114814538798046 48.87465887700433, 2.412843880684065 48.87465802017547, 2.412843225335768 48.87420865262779, 2.4128432253357674 48.87420865262779, 2.4121607189885275 48.87331034738674, 2.4073917408015575 48.87286388272396))");
  Geometry b = read("MULTIPOLYGON (((2.4114814538798046 48.87465887700433, 2.4121639662098744 48.87555718514279, 2.4128451914147386 48.875556754773875, 2.4128438806840644 48.87465802017547, 2.4114814538798046 48.87465887700433)), ((2.414204306008521 48.873309044649815, 2.4128419146732525 48.87330991703566, 2.4128451914147386 48.875556754773875, 2.412163966209875 48.87555718514279, 2.412164615687918 48.87600655219706, 2.413187131946889 48.876455268645934, 2.4108027911162724 48.87645676815331, 2.410804066510286 48.877355501447255, 2.4094415687867565 48.877356334949575, 2.413531707222926 48.87915125224533, 2.414204306008521 48.873309044649815)))");

  public void testCrossingNarrowGap() {
    Geometry a = read("POLYGON ((10 20, 22 20, 22 10, 10 10, 10 20))");
    Geometry b = read("MULTIPOLYGON (((20 5, 20 15, 30 15, 20 5)), ((20 25, 20 15.1, 30 15, 20 25)))");
    Geometry expected = read("POLYGON ((20 20, 22 20, 22 15, 22 10, 20 10, 20 15, 20 20))");
    checkIntersection(a, b, 1, expected );
  }

  public void testUnionNarrowGap() {
    Geometry a = read("POLYGON ((20 5, 20 15, 30 15, 20 5))");
    Geometry b = read("POLYGON ((20 25, 20 15.1, 30 15, 20 25))");
    Geometry expected = read("POLYGON ((20 5, 20 15, 20 25, 30 15, 20 5))");
    checkOverlay(a, b, OverlayOp.UNION, 1, expected );
  }

  // Direct intersection Fails.
  public void testCase() {
    // Throws exception
    a.intersection(b);
  }

  public void testOverlaySR() {
    overlaySR(a, b,1);
    overlaySR(a, b,10);
    overlaySR(a, b,100);

    // Starts failing at this resolution.
    overlaySR(a, b,1000);
    overlaySR(a, b,10000);
  }

  public void overlaySR(Geometry a, Geometry b, double scale) {
    PrecisionModel pm = new PrecisionModel(scale);
    Geometry result = OverlayOp.overlayOp(a, b, OverlayOp.UNION, pm);
    Geometry result2 = OverlayOp.overlayOp(a, b, OverlayOp.INTERSECTION, pm);
    System.out.println(" SR Union:        " + result);
    System.out.println(" SR Intersection: " + result2);
  }

  public void testOverlaySRPreNoded() {

    overlaySRPrenoded(a, b,1);
    overlaySRPrenoded(a, b,10);
    overlaySRPrenoded(a, b,100);
    overlaySRPrenoded(a, b,1000);
    overlaySRPrenoded(a, b,10000);
  }

  // JNH: Naive approach to pre-Snapping geometries.
  public void overlaySRPrenoded(Geometry a, Geometry b, double scale) {
    PrecisionModel pm = new PrecisionModel(scale);
    Geometry a1 = OverlayOp.overlayOp(a, a, OverlayOp.UNION, pm);
    Geometry b1 = OverlayOp.overlayOp(b, b, OverlayOp.UNION, pm);

    System.out.println("Precision " + scale);
    System.out.println(" Pre-snapped A: " + a1);
    System.out.println(" Pre-snapped B: " + b1);

    Geometry result = OverlayOp.overlayOp(a1, b1, OverlayOp.UNION, pm);
    Geometry result2 = OverlayOp.overlayOp(a1, b1, OverlayOp.INTERSECTION, pm);
    System.out.println(" Pre-Snapped SR Union:        " + result);
    System.out.println(" Pre-Snapped SR Intersection: " + result2);
  }

  
  private void checkOverlay(Geometry a, Geometry b, int op, double scale, Geometry expected) {
    PrecisionModel pm = new PrecisionModel(scale);
    Geometry result = OverlayOp.overlayOp(a, b, op, pm);
    boolean isCorrect = expected.equalsExact(result);
    assertTrue(isCorrect);
  }
  
  private void checkIntersection(Geometry a, Geometry b, double scale, Geometry expected) {
    Geometry result = intersection(a, b, scale);
    boolean isCorrect = expected.equalsExact(result);
    if (! isCorrect) System.out.println("Result: " +result);
    assertTrue(isCorrect);
  }

  public static Geometry intersection(Geometry a, Geometry b, double scaleFactor) {
    PrecisionModel pm = new PrecisionModel(scaleFactor);
    Geometry result = OverlayOp.overlayOp(a, b, OverlayOp.INTERSECTION, pm);
    return result;
  }

}

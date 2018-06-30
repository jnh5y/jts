package org.locationtech.jts.jmh;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.math3.util.FastMath;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;
import org.locationtech.jts.geom.impl.PointCoordinateSequenceFactory;
import org.locationtech.jts.geom.util.SineStarFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTPointReader;
import org.locationtech.jts.io.WKTReader;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class WKTReaderBench {
    static String point = "POINT(1 2)";
    static WKTReader reader = new WKTReader();

    static PackedCoordinateSequenceFactory csf =
            new PackedCoordinateSequenceFactory(PackedCoordinateSequenceFactory.DOUBLE,2);
    static GeometryFactory gf = new GeometryFactory(csf);
    static WKTReader reader2 = new WKTReader(gf);

    static CoordinateSequenceFactory pcsf = new PointCoordinateSequenceFactory();
    static GeometryFactory pgf = new GeometryFactory(pcsf);
    static WKTReader pointReader = new WKTReader(pgf);

    static WKTPointReader pointReader2 = new WKTPointReader();

    @Benchmark
    public void run13() {
        try {
            reader.read(point);
        } catch (ParseException e) { }
    }

    @Benchmark
    public void run10() {
        try {
            reader2.read(point);
        } catch (ParseException e) { }
    }

    @Benchmark
    public void run7() {
        try {
            pointReader.read(point);
        } catch (ParseException e) { }
    }

    @Benchmark
    public void run4() {
        try {
            pointReader2.read(point);
        } catch (ParseException e) { }
    }
}

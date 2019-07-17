package org.locationtech.jts.jmh;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.math3.util.FastMath;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.SineStarFactory;
import org.locationtech.jts.io.WKTReader;
import org.openjdk.jmh.annotations.*;

//@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 3, time = 20, timeUnit = TimeUnit.SECONDS)
public class BufferBench {
    static Random rng = new Random();
    static WKTReader reader = new WKTReader();
    static Geometry point = readGeometry("POINT(1 2)");

    static SineStarFactory ssf = new SineStarFactory();
    static {
        ssf.setEnvelope(new Envelope(0, 100, 0, 100));
        ssf.setNumPoints(10000);
    }
    static Geometry polygon = ssf.createSineStar();

    AtomicBoolean firstTime = new AtomicBoolean(true);

    void firstTime() {
        if (firstTime.get()) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {}
            firstTime.set(false);
        }
    }

    static Geometry readGeometry(String wkt){
        try {
            return reader.read(wkt);
        } catch (Exception e) {

        }
        return null;
    }

    @Benchmark
    public void bufferPoint1000() {
        firstTime();
        point.buffer(1.0, 1000);
    }

    @Benchmark
    public void bufferPoint100() {
        firstTime();
        point.buffer(1.0, 100);
    }

    @Benchmark
    public void bufferPoint10() {
        firstTime();
        point.buffer(1.0, 10);
    }

    @Benchmark
    public void bufferPolygon() {
        firstTime();
        polygon.buffer(1.0, 1000);
    }

//    @Benchmark
//    public double javaAtan2() {
//        double y = rng.nextDouble();
//        double x = rng.nextDouble();
//
//        return Math.atan2(y, x);
//    }
//
//    @Benchmark
//    public double fastAtan2() {
//        double y = rng.nextDouble();
//        double x = rng.nextDouble();
//
//        return FastMath.atan2(y, x);
//    }

}

package org.locationtech.jts.jmh;

import java.util.concurrent.TimeUnit;

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
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
public class BufferBench {

    static WKTReader reader = new WKTReader();
    static Geometry point = readGeometry("POINT(1 2)");

    static SineStarFactory ssf = new SineStarFactory();
    static {
        ssf.setEnvelope(new Envelope(0, 100, 0, 100));
        ssf.setNumPoints(10000);
    }
    static Geometry polygon = ssf.createSineStar();


    static Geometry readGeometry(String wkt){
        try {
            return reader.read(wkt);
        } catch (Exception e) {

        }
        return null;
    }

    @Benchmark
    public void bufferPoint() {
        point.buffer(1.0, 1000);
    }

    @Benchmark
    public void bufferPolygon() {
        polygon.buffer(1.0, 1000);
    }

}

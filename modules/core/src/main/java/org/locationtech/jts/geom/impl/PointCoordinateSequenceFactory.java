package org.locationtech.jts.geom.impl;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFactory;

public class PointCoordinateSequenceFactory implements CoordinateSequenceFactory {
    @Override
    public CoordinateSequence create(Coordinate[] coordinates) {
        return new PointCoordinateSequence(coordinates[0].x, coordinates[0].y);
    }

    @Override
    public CoordinateSequence create(CoordinateSequence coordSeq) {
        return new PointCoordinateSequence(coordSeq.getX(0), coordSeq.getY(0));
    }

    @Override
    public CoordinateSequence create(int size, int dimension) {
        return new PointCoordinateSequence();
    }
}

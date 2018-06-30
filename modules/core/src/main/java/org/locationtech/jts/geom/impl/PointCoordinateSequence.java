package org.locationtech.jts.geom.impl;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;

public class PointCoordinateSequence implements CoordinateSequence {
    private double x, y;

    public PointCoordinateSequence() {

    }

    public PointCoordinateSequence(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Coordinate getCoordinate(int i) {
        return new Coordinate(x, y);
    }

    @Override
    public Coordinate getCoordinateCopy(int i) {
        return new Coordinate(x, y);
    }

    @Override
    public void getCoordinate(int index, Coordinate coord) {
        new Coordinate(x, y);
    }

    @Override
    public double getX(int index) {
        return x;
    }

    @Override
    public double getY(int index) {
        return y;
    }

    @Override
    public double getOrdinate(int index, int ordinateIndex) {
        if (ordinateIndex == 0) {
            return x;
        } else {
            return y;
        }
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public void setOrdinate(int index, int ordinateIndex, double value) {
        if (ordinateIndex == 0) {
            x = value;
        } else {
            y = value;
        }
    }

    @Override
    public Coordinate[] toCoordinateArray() {
        return new Coordinate[]{ new Coordinate(x, y) };
    }

    @Override
    public Envelope expandEnvelope(Envelope env) {
        env.expandToInclude(x, y);
        return env;
    }

    @Override
    public Object clone() {
        return copy();
    }

    @Override
    public CoordinateSequence copy() {
        return new PointCoordinateSequence(x, y);
    }
}

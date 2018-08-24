package com.lzpeng.photograph.core.bean;

import lombok.Getter;

@Getter
public class Point3D extends Point2D {
    private final double Z;

    public Point3D(double x, double y, double z) {
        super(x, y);
        Z = z;
    }
}

package com.lzpeng.photograph.core;

import com.lzpeng.photograph.algo.AbstractParamAdjustment;
import com.lzpeng.photograph.core.bean.Point2D;
import com.lzpeng.photograph.core.bean.Point3D;
import com.lzpeng.photograph.util.AngleUtil;

import Jama.Matrix;


import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class PhotoGraphAdjustment extends AbstractParamAdjustment {

    private OutElement initialValue;
    private List<Point2D> picturePoints;
    private List<Point3D> landPoints;
    private double x0;
    private double y0;
    private double f;
    private final double m;
    private double[] a;
    private double[] b;
    private double[] c;

    private PhotoGraphAdjustment(PhotoGraphSurveyData data) {
        this.picturePoints = data.getPicturePoints();
        this.landPoints = data.getLandPoints();
        this.x0 = data.getInElement().getX0();
        this.y0 = data.getInElement().getY0();
        this.f = data.getInElement().getF();
        this.m = data.getM();
        this.n = data.getLandPoints().size();
        init();
    }

    public static PhotoGraphAdjustment create(PhotoGraphSurveyData data) {
        return new PhotoGraphAdjustment(data);
    }

    private void init() {
        double xs = landPoints.stream().mapToDouble(point -> point.getX()).average().getAsDouble();
        double ys = landPoints.stream().mapToDouble(point -> point.getY()).average().getAsDouble();
        double zs = f * m + landPoints.stream().mapToDouble(point -> point.getZ()).average().getAsDouble();
        initialValue = OutElement.builder()
                .xs(xs)
                .ys(ys)
                .zs(zs)
                .phiOrA(0)
                .omegaOrAlpha(0)
                .kappa(0)
                .type(AngleElementType.FOK)
                .build();
        initialX = initialValue.getMatrix();
        Matrix r = initialValue.getR();
        a = r.getMatrix(0, 0, 0, 2).getArrayCopy()[0];
        b = r.getMatrix(1, 1, 0, 2).getArrayCopy()[0];
        c = r.getMatrix(2, 2, 0, 2).getArrayCopy()[0];
    }

    @Override
    public Matrix a() {
        if (this.aMatrix == null) {
            double omega = initialValue.getOmegaOrAlpha();
            double kappa = initialValue.getKappa();
            int size = landPoints.size();
            double[][] data = new double[2 * size][6];
            for (int i = 0; i < size; i++) {
                Point3D point3D = landPoints.get(i);
                double inverseZ = 1 / getZByEquation(point3D);
                double x = getxByEquation(point3D) - x0;
                double y = getyByEquation(point3D) - y0;
                data[2 * i][0] = inverseZ * (a[0] * f + a[2] * x);
                data[2 * i][1] = inverseZ * (b[0] * f + b[2] * x);
                data[2 * i][2] = inverseZ * (c[0] * f + c[2] * x);
                data[2 * i][3] = y * sin(omega) - (x / f * (x * cos(kappa) - y * sin(kappa)) + f * cos(kappa)) * cos(omega);
                data[2 * i][4] = -f * sin(kappa) - x / f * (x * sin(kappa) + y * cos(kappa));
                data[2 * i][5] = y;
                data[2 * i + 1][0] = inverseZ * (a[1] * f + a[2] * y);
                data[2 * i + 1][1] = inverseZ * (b[1] * f + b[2] * y);
                data[2 * i + 1][2] = inverseZ * (c[1] * f + c[2] * y);
                data[2 * i + 1][3] = -x * sin(omega) - (y / f * (x * cos(kappa) - y * sin(kappa)) - f * sin(kappa)) * cos(omega);
                data[2 * i + 1][4] = -f * cos(kappa) - y / f * (x * sin(kappa) + y * cos(kappa));
                data[2 * i + 1][5] = -x;
            }
            this.aMatrix = new Matrix(data);
        }
        return this.aMatrix;
    }

    @Override
    public Matrix l() {
        if (this.lMatrix == null) {
            int size = landPoints.size();
            double[] data = new double[2 * size];
            for (int i = 0; i < size; i++) {
                data[2 * i] = picturePoints.get(i).getX() - getxByEquation(landPoints.get(i));
                data[2 * i + 1] = picturePoints.get(i).getY() - getyByEquation(landPoints.get(i));
            }
            this.lMatrix = new Matrix(data, 2 * size);
        }
        return this.lMatrix;
    }

    @Override
    public Matrix p() {
        if (this.pMatrix == null) {
            int size = 2 * landPoints.size();
            this.pMatrix = Matrix.identity(size, size);
        }
        return this.pMatrix;
    }

    @Override
    public Matrix get() {
        Matrix x;
        do {
            aMatrix = null;
            lMatrix = null;
            x = x();
            initialX = initialX.plus(x);
            initialValue = OutElement.builder()
                    .xs(initialX.get(0, 0))
                    .ys(initialX.get(1, 0))
                    .zs(initialX.get(2, 0))
                    .phiOrA(initialX.get(3, 0))
                    .omegaOrAlpha(initialX.get(4, 0))
                    .kappa(initialX.get(5, 0))
                    .type(AngleElementType.FOK)
                    .build();
            Matrix r = initialValue.getR();
            a = r.getMatrix(0, 0, 0, 2).getArrayCopy()[0];
            b = r.getMatrix(1, 1, 0, 2).getArrayCopy()[0];
            c = r.getMatrix(2, 2, 0, 2).getArrayCopy()[0];
        } while (
                abs(x.get(3, 0)) > AngleUtil.degreesToRadians(0.0006)
                        || abs(x.get(4, 0)) > AngleUtil.degreesToRadians(0.0006)
                        || abs(x.get(5, 0)) > AngleUtil.degreesToRadians(0.0006));
        return initialX;
    }

    public OutElement getResult() {
        return initialValue;
    }
    public OutElement getQQResult() {
        OutElement result = OutElement.builder()
                .xs(m().get(0, 0))
                .ys(m().get(1, 0))
                .zs(m().get(2, 0))
                .phiOrA(m().get(3, 0))
                .omegaOrAlpha(m().get(4, 0))
                .kappa(m().get(5, 0))
                .type(AngleElementType.FOK)
                .build();
        return result;
    }

    private double getXByEquation(Point3D landPoint) {
        return a[0] * (landPoint.getX() - initialValue.getXs()) + b[0] * (landPoint.getY() - initialValue.getYs()) + c[0] * (landPoint.getZ() - initialValue.getZs());
    }

    private double getYByEquation(Point3D landPoint) {
        return a[1] * (landPoint.getX() - initialValue.getXs()) + b[1] * (landPoint.getY() - initialValue.getYs()) + c[1] * (landPoint.getZ() - initialValue.getZs());
    }

    private double getZByEquation(Point3D landPoint) {
        return a[2] * (landPoint.getX() - initialValue.getXs()) + b[2] * (landPoint.getY() - initialValue.getYs()) + c[2] * (landPoint.getZ() - initialValue.getZs());
    }

    private double getxByEquation(Point3D landPoint) {
        return -f * getXByEquation(landPoint) / getZByEquation(landPoint);
    }

    private double getyByEquation(Point3D landPoint) {
        return -f * getYByEquation(landPoint) / getZByEquation(landPoint);
    }
}

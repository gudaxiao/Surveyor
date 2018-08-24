package com.lzpeng.photograph.core;


import com.lzpeng.photograph.util.AngleUtil;

import java.io.Serializable;

import Jama.Matrix;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

//外方位元素
@Getter
@Builder
public class OutElement implements Serializable {
    //三个线元素
    private final double xs;
    private final double ys;
    private final double zs;
    //三个角元素
    private double phiOrA;
    private double omegaOrAlpha;
    private double kappa;
    private AngleElementType type;
    //R阵
    private Matrix r;

    public OutElement init() {
        phiOrA = AngleUtil.degreesToRadians(phiOrA);
        omegaOrAlpha = AngleUtil.degreesToRadians(omegaOrAlpha);
        kappa = AngleUtil.degreesToRadians(kappa);
        return this;
    }

    /**
     * a1, a2, a3
     * b1, b2, b3
     * c1, c2, c3
     *
     * @return
     */
    public Matrix getR() {
        if (this.r == null) {
            this.r = R1().times(R2()).times(R3());
        }
        return this.r;
    }

    private Matrix R1() {
        Matrix result = null;
        switch (type) {
            case FOK:
                result = new Matrix(new double[][]{
                        {Math.cos(phiOrA), 0, -Math.sin(phiOrA)},
                        {0, 1, 0},
                        {Math.sin(phiOrA), 0, Math.cos(phiOrA)},
                });
                break;
            case OFK2:
                result = new Matrix(new double[][]{
                        {1, 0, 0},
                        {0, Math.cos(omegaOrAlpha), -Math.sin(omegaOrAlpha)},
                        {0, Math.sin(omegaOrAlpha), Math.cos(omegaOrAlpha)},
                });
                break;
            case AAKV:
                result = new Matrix(new double[][]{
                        {Math.cos(omegaOrAlpha), Math.sin(omegaOrAlpha), 0},
                        {-Math.sin(omegaOrAlpha), Math.cos(omegaOrAlpha), 0},
                        {0, 0, 1},
                });
                break;
        }
        return result;
    }

    private Matrix R2() {
        Matrix result = null;
        switch (type) {
            case OFK2:
                result = new Matrix(new double[][]{
                        {Math.cos(phiOrA), 0, -Math.sin(phiOrA)},
                        {0, 1, 0},
                        {Math.sin(phiOrA), 0, Math.cos(phiOrA)},
                });
                break;
            case FOK:
            case AAKV:
                result = new Matrix(new double[][]{
                        {1, 0, 0},
                        {0, Math.cos(omegaOrAlpha), -Math.sin(omegaOrAlpha)},
                        {0, Math.sin(omegaOrAlpha), Math.cos(omegaOrAlpha)},
                });
                break;
        }
        return result;
    }

    private Matrix R3() {
        Matrix result = new Matrix(new double[][]{
                {Math.cos(kappa), -Math.sin(kappa), 0},
                {Math.sin(kappa), Math.cos(kappa), 0},
                {0, 0, 1},
        });
        return result;
    }

    public Matrix getMatrix() {
        return new Matrix(new double[]{xs, ys, zs, phiOrA, omegaOrAlpha, kappa}, 6);
    }

}

package com.lzpeng.photograph.algo;

import Jama.Matrix;

import java.util.function.Supplier;

/**
 * 摄影测量 P74
 */
public abstract class AbstractParamAdjustment{

    protected Matrix aMatrix;
    protected Matrix lMatrix;
    protected Matrix pMatrix;
    protected Matrix initialX;


    private Matrix vMatrix;
    private Matrix qMatrix;
    private Double m0;
    protected int n;
    private Matrix mMatrix;


    /**
     * V = A*X - L
     * 系数矩阵
     *
     * @return
     */
    protected abstract Matrix a();

    /**
     * V = A*X - L
     * 常数向量
     *
     * @return
     */
    protected abstract Matrix l();

    /**
     * 权阵
     *
     * @return
     */
    protected abstract Matrix p();

    /**
     * 法方程系数矩阵
     *
     * @return
     */
    private Matrix n() {
        return a().transpose().times(p()).times(a());
    }

    /**
     * 法方程常数向量
     *
     * @return
     */
    private Matrix b() {
        return a().transpose().times(p()).times(l());
    }

    /**
     * 求解x
     *
     * @return
     */
    protected Matrix x() {
        return n().inverse().times(b());
    }


    /**
     * V = A*X - L
     * 改正数向量
     *
     * @return
     */
    private Matrix v() {
        if (this.vMatrix == null)
            this.vMatrix = a().times(x()).minus(l());
        return this.vMatrix;
    }

    /**
     * 求协因数阵
     *
     * @return
     */
    private Matrix q() {
        if (this.qMatrix == null)
            this.qMatrix = n().inverse();
        return this.qMatrix;
    }

    /**
     * 求单位权中误差
     *
     * @return
     */
    public Double m0() {
//        v().transpose().print(20,18);
        if (this.m0 == null)
            this.m0 = Math.sqrt(v().transpose().times(p()).times(v()).get(0, 0) / (2 * n - 6));
        return this.m0;
    }

    /**
     * 求中误差
     *
     * @param i
     * @return
     */
    private Double m(int i) {
        return m0() * Math.sqrt(q().get(i, i));
    }

    /**
     * 求中误差向量
     *
     * @return
     */
    public Matrix m() {
        if (this.mMatrix == null) {
            double[] data = new double[a().getColumnDimension()];
            for (int i = 0; i < a().getColumnDimension(); i++) {
                data[i] = m(i);
            }
            this.mMatrix = new Matrix(data, 6);
        }
        return this.mMatrix;
    }

    public abstract Matrix get();
}

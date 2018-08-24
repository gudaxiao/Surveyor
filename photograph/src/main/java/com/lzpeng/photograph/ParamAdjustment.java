package com.lzpeng.photograph;

import Jama.Matrix;

import java.io.IOException;

/**
 * @author Administrator
 *
 */
public abstract class ParamAdjustment {
    /**
     * 观测值权阵
     */
    protected Matrix P;
    /**
     * 观测值向量
     */
    protected Matrix L;
    /**
     * 观测值改正数向量
     */
    protected Matrix V;
    /**
     * 平差后观测值协因数阵
     */
    protected Matrix QLL;
    /**
     * 观测值改正数协因数阵
     */
    protected Matrix Qvv;
    /**
     * 参数近似值向量
     */
    protected Matrix X0;
    /**
     * 参数改正数向量
     */
    protected Matrix dX;
    /**
     * 平差后参数协因数阵
     */
    protected Matrix Qxx;
    /**
     * 间接平差法方程BTPB*X-BTPl=0中的B
     */
    protected Matrix B;
    /**
     * 间接平差法方程BTPB*X-BTPl=0中的l
     */
    protected Matrix l;
    /**
     * 间接平差法方程BTPB*X-BTPl=0中的BTPB,令Nbb=BTPB
     */
    protected Matrix Nbb;
    /**
     * 间接平差法方程BTPB*X-BTPl=0中的BTPl,令W=BTPl
     */
    protected Matrix W;
    /**
     * pvv
     */
    protected double pvv;
    /**
     * 单位权中误差
     */
    protected double m_mu;


    /**
     * 点名存储,返回点名对应的点号
     * @param name
     * @param pName
     */
    protected int getStationNumber(String name, String[] pName) {
        for (int i = 0; i < pName.length; i++) {
            if (pName[i] != null) {
                if (pName[i].equals(name)) return i;//将待查的点名与已经存入的点比较
            } else {
                pName[i] = name; return i;//待查点是一个新的点名,并加入pName中
            }
        }
        return -1;//pName数组已经存满,且没有待查点名
    }
    protected abstract void ca_X0();//近似值计算
    protected abstract void ca_B() ;//计算B
    protected abstract void ca_l() ;//计算l
    protected abstract void ca_m_mu() ;// 计算单位权中误差
    /**
     * 计算Nbb
     */
    protected void ca_Nbb() {
        Nbb = B.transpose().times(P).times(B);
    }
    /**
     * 计算W
     */
    protected void ca_W() {
        W = B.transpose().times(P).times(l);
    }
    /**
     * 计算pvv
     */
    protected void ca_pvv() {
        pvv = V.transpose().times(P).times(V).get(0, 0);
    }
    /**
     * 计算Qxx
     */
    protected void ca_Qxx() {
        Qxx = Nbb.inverse();
    }
    /**
     * 计算QLL
     */
    protected void ca_QLL() {
        QLL = B.times(Qxx).times(B.transpose());
    }
    /**
     * 计算Qvv
     */
    protected void ca_Qvv() {
        Qvv = P.inverse().minus(QLL);
    }
    /**
     * 组成法方程
     */
    protected void ca_BTPB() {
        ca_B();
        ca_l();
        ca_Nbb();
        ca_W();
    }
    /**
     * 参数平差值计算
     */
    protected void ca_dX() {
        dX = Nbb.inverse().times(W);
    }
    /**
     * 计算残差
     */
    protected void ca_V() {
        V = B.times(dX).minus(l);
    }
    /**
     * 精度评定
     */
    protected void ca_Q() {
        ca_pvv();
        ca_m_mu();
        ca_Qxx();
        ca_QLL();
        ca_Qvv();
    }
//    public abstract void inputData(String inputFileName) throws IOException ;//输入原始数据
//    public abstract void printData(String outputFileName) throws IOException;//输出原始数据
//    public abstract void printResult(String outputFileName) throws IOException;//输出平差结果
//    public abstract void LS_Adjust(String outputFileName) throws IOException;//最小二乘平差
//    public abstract void dataSnooping(double alpha, String outputFileName) throws IOException;//粗差探测
//    public abstract void FreeNetAdjust(String outputFileName) throws IOException;//自由网平差
//    public abstract void Quasi_Stable(String quasiStableFileName,String outputFileName) throws IOException;//拟稳平差
}

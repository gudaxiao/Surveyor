package com.lzpeng.photograph;

import com.lzpeng.photograph.algo.AbstractParamAdjustment;
import com.lzpeng.photograph.core.InElement;
import com.lzpeng.photograph.core.PhotoGraphAdjustment;
import com.lzpeng.photograph.core.PhotoGraphSurveyData;
import com.lzpeng.photograph.core.bean.Point2D;
import com.lzpeng.photograph.core.bean.Point3D;

import Jama.Matrix;
import java.util.Arrays;
public class App {

    public static void main(String[] args) {
        PhotoGraphSurveyData data = PhotoGraphSurveyData.builder()
                .m(40000)
                .inElement(new InElement(0, 0, 0.15324))
                .picturePoints(Arrays.asList(
                        new Point2D(-86.15/1e3,-68.99/1e3),
                        new Point2D(-53.4/1e3,82.21/1e3),
                        new Point2D(-14.78/1e3,-76.63/1e3),
                        new Point2D(10.46/1e3,64.43/1e3)))
                .landPoints(Arrays.asList(
                        new Point3D(36589.41,25273.32,2195.17),
                        new Point3D(37631.08,31324.51,728.69),
                        new Point3D(39100.97,24934.98,2386.5),
                        new Point3D(40426.54,30319.81,757.31)))
                .build();
        AbstractParamAdjustment adjustment = PhotoGraphAdjustment.create(data);
        Matrix x = adjustment.get();
        x.transpose().print(20,18);
        System.out.println(adjustment.m0());
        adjustment.m().transpose().print(20,18);

    }

}

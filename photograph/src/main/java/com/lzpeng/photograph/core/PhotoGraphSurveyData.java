package com.lzpeng.photograph.core;


import com.lzpeng.photograph.core.bean.Point2D;
import com.lzpeng.photograph.core.bean.Point3D;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PhotoGraphSurveyData {
    private double m;//相片比例尺
    private InElement inElement;//内方位元素
    private List<Point2D> picturePoints;//像点
    private List<Point3D> landPoints;//物点
}

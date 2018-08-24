package com.lzpeng.photograph.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

//内方位元素
@AllArgsConstructor
@Getter
public class InElement {
    private double x0;
    private double y0;
    /**
     * 摄影机焦距
     */
    private double f;

}

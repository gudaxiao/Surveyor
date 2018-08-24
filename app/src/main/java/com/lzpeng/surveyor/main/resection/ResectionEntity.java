package com.lzpeng.surveyor.main.resection;

import org.jsoup.nodes.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Administrator on 2018\5\27 0027.
 */

@Getter
@EqualsAndHashCode(callSuper=false)
public class ResectionEntity extends HtmlEntity {
    private final double xx;
    private final double xy;
    private final double dx;
    private final double dy;
    private final double dz;

    public ResectionEntity(String assetFileName, double xx, double xy, double dx, double dy, double dz) {
        super(assetFileName);
        this.xx = xx;
        this.xy = xy;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }


    @Override
    protected String outerHtml() {
        Element body = document.body();
        body.getElementById("dimian_x").text((dx == 0) ? "未知" : String.format("%.3f", dx));
        body.getElementById("dimian_y").text((dy == 0) ? "未知" : String.format("%.3f", dy));
        body.getElementById("dimian_z").text((dz == 0) ? "未知" : String.format("%.3f", dz));
        body.getElementById("xiang_x").text((xx == 0) ? "未知" : String.format("%.3f", xx));
        body.getElementById("xiang_y").text((xy == 0) ? "未知" : String.format("%.3f", xy));
        return document.outerHtml();
    }
}

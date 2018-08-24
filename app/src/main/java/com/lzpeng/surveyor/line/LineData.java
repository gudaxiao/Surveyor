package com.lzpeng.surveyor.line;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018\5\24 0024.
 */

@Entity
public class LineData {
    @Id(autoincrement = true)
    private Long id;
    @Convert(converter = LineType.Converter.class, columnType = Integer.class)
    private LineType type;
    @Unique
    private String roadName;
    private String roadNamePrefix;

    public LineData(LineType type, String roadName, String roadNamePrefix) {
        this.type = type;
        this.roadName = roadName;
        this.roadNamePrefix = roadNamePrefix;
    }

    public LineData(LineType type, String roadName) {
        this(type,roadName,"K");
    }

    public LineData(String roadName, String roadNamePrifix) {
        this(LineType.POINT, roadName, roadNamePrifix);
    }

    public LineData(String roadName) {
        this(LineType.POINT, roadName, "K");
    }

    @Generated(hash = 1137331833)
    public LineData(Long id, LineType type, String roadName,
            String roadNamePrefix) {
        this.id = id;
        this.type = type;
        this.roadName = roadName;
        this.roadNamePrefix = roadNamePrefix;
    }

    @Generated(hash = 657526810)
    public LineData() {
    }

    public LineType getType() {
        return type;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getRoadNamePrefix() {
        return roadNamePrefix;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public void setRoadNamePrefix(String roadNamePrefix) {
        this.roadNamePrefix = roadNamePrefix;
    }

    @Override
    public String toString() {
        return roadName;
    }
}

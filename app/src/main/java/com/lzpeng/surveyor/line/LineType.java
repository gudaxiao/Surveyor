package com.lzpeng.surveyor.line;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\5\24 0024.
 */

public enum LineType implements Serializable {
    POINT(0), LINE(1);

    private final int type;

    LineType(int type) {
        this.type = type;
    }

    public static class Converter implements PropertyConverter<LineType, Integer> {

        @Override
        public LineType convertToEntityProperty(Integer databaseValue) {
            switch (databaseValue) {
                case 0:
                    return LineType.POINT;
                case 1:
                    return LineType.LINE;
                default:
                    break;
            }
            return null;
        }

        @Override
        public Integer convertToDatabaseValue(LineType entityProperty) {
            return entityProperty.type;
        }
    }
}

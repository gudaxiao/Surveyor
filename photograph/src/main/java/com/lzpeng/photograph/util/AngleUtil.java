package com.lzpeng.photograph.util;

public class AngleUtil {

    //正割
    public static double sec(double rad) {
        return 1 / Math.cos(rad);
    }
    //余割
    public static double csc(double rad) {
        return 1 / Math.sin(rad);
    }
    //余切
    public static double cot(double rad) {
        return 1 / Math.tan(rad);
    }


    //度分秒----弧度
    public static double toRadians(double angle) {
        return Math.toRadians(angle);
    }

    public static double degreesToRadians(double angle) {
        return toRadians(degreesTodd(angle) + degreesToff(angle) / 60.0 + degreesTomm(angle) / 3600.0);
    }

    private static int degreesTodd(double angle) {
        return (int) (angle % 360 + 1E-10);
    }

    private static int degreesToff(double angle) {
        int f = (int) (angle * 100 + 1E-10) % 100;
        return f;
    }

    private static double degreesTomm(double angle) {
        double m = angle * 10000 % 100;
        return m;
    }
    public static double dms2rad(double v) {
        return degreesToRadians(v/ 1e4);
    }
    //弧度---度分秒
    public static double ToDegrees(double rad) {
        return Math.toDegrees(rad);
    }

    public static double radiansToDegrees(double rad) {
        return radiansTodd(rad) + radiansToff(rad) / 100.0 + radiansTomm(rad) / 10000.0;
    }

    private static int radiansTodd(double rad) {
        return (int) ToDegrees(rad);
    }

    private static int radiansToff(double rad) {
        return (int) ((ToDegrees(rad) - radiansTodd(rad)) * 60);
    }

    private static double radiansTomm(double rad) {
        return (ToDegrees(rad) - radiansTodd(rad) - radiansToff(rad) / 60.0) * 3600;
    }


    /**
     * 用dxdy计算方位角,返回值为弧度值
     */
    public static double azimuthBydxdy(double dx, double dy) {
        double ds = Math.sqrt(dx * dx + dy * dy);
        double azi = Math.acos(dx / (ds + 1e-15));
        return dy > 0 ? azi : (2 * Math.PI - azi);
    }

    /**
     * 用两点坐标计算方位角,返回值为弧度值
     */
    public static double azimuthByxyxy(double startX, double startY, double endX, double endY) {
        return azimuthBydxdy(endX - startX, endY - startY);
    }

    //随机数组
    public static int[] randomArray(int index) {
        int[] result = new int[index];
        for (int i = 0; i < result.length; ) {
            int value = (int) (Math.random() * index);
            int j = 0;
            for (; j < i; j++) {
                if (value == result[j]) {
                    break;
                }
            }
            if (j == i) {
                result[i] = value;
                i++;
            }
        }
        return result;
    }


}




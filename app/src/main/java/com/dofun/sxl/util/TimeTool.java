package com.dofun.sxl.util;

public class TimeTool {
    public static String getFormatHMS(long time) {
        time = time / 1000;//总秒数

        int s = (int) (time % 60);//秒
        int m = (int) (time / 60);//分
        int h = (int) (time / 3600);//小时
        return String.format("%02d:%02d", m, s);
    }
}

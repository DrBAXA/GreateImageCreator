package com.dv.image;

public class ColorUtil {
    public static Color getColor(int color){
        return new Color(getRed(color), getGreen(color), getBlue(color));
    }

    public static int getRed(int color){
        return (color & 0x00FF0000) >> (4*4);
    }

    public static int getGreen(int color){
        return (color & 0x0000FF00) >> (2*4);
    }

    public static int getBlue(int color){
        return (color & 0x000000FF);
    }
}

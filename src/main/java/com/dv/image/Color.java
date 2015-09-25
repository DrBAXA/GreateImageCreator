package com.dv.image;

import java.io.Serializable;

public class Color implements Serializable, Comparable<Color>{
    private int r;
    private int g;
    private int b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return r == color.r && g == color.g && b == color.b;

    }

    @Override
    public int hashCode() {
        int result = r;
        result = (result << 8) + g;
        result = (result << 8) + b;
        return result;
    }

    public int compareTo(Color c) {
        if (this.equals(c)) return 0;

        boolean sign = diff(c) >= 0;

        int res = (r-c.r)*(r-c.r) + (g-c.g)*(g-c.g) + (b-c.b)*(b-c.b);

        return sign ? res : (-1)*res;
    }

    private int diff(Color c){
        return (r-c.r)+(g-c.g)+(b-c.b);
    }
}

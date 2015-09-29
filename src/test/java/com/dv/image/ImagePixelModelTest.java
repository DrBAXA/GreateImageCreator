package com.dv.image;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ImagePixelModelTest {
    @Test
    public void processFileTest() throws Exception {
        Color[][] expect = {{new Color(191, 128,  64), new Color(50, 75,  75)},
                            {new Color(100, 150,  150), new Color(25, 38,  38)}};

        ImagePixelModel im = new ImagePixelModel(2, "8x8.png");

        assertArrayEquals(expect, im.getMatrix());
    }

    @Test
    public void getRedTest(){
        int color0 = 0x00000000;
        int color1 = 0x00AA0000;
        int color2 = 0x00AAFF00;
        int color3 = 0x50FF0000;
        int color4 = 0x00A00069;
        int color5 = 0x00110000;
        int color6 = 0x00010000;
        int color7 = 0x00100000;

        assertEquals(0, ColorUtil.getRed(color0));
        assertEquals(170, ColorUtil.getRed(color1));
        assertEquals(170, ColorUtil.getRed(color2));
        assertEquals(255, ColorUtil.getRed(color3));
        assertEquals(160, ColorUtil.getRed(color4));
        assertEquals(17, ColorUtil.getRed(color5));
        assertEquals(1, ColorUtil.getRed(color6));
        assertEquals(16, ColorUtil.getRed(color7));
    }

    @Test
    public void getGreenTest(){
        int color0 = 0x00000000;
        int color1 = 0x00AA0000;
        int color2 = 0x00AAFF00;
        int color3 = 0x50FF0000;
        int color4 = 0x00A00069;
        int color5 = 0x00110000;
        int color6 = 0x00010000;
        int color7 = 0x00100000;

        assertEquals(0, ColorUtil.getGreen(color0));
        assertEquals(0, ColorUtil.getGreen(color1));
        assertEquals(255, ColorUtil.getGreen(color2));
        assertEquals(0, ColorUtil.getGreen(color3));
        assertEquals(0, ColorUtil.getGreen(color4));
        assertEquals(0, ColorUtil.getGreen(color5));
        assertEquals(0, ColorUtil.getGreen(color6));
        assertEquals(0, ColorUtil.getGreen(color7));
    }

    @Test
    public void getBlueTest(){
        int color0 = 0x00000000;
        int color1 = 0x00AA0010;
        int color2 = 0x00AAFF00;
        int color3 = 0x50FF00AA;
        int color4 = 0x00A00069;
        int color5 = 0x00110000;
        int color6 = 0x00010000;
        int color7 = 0x00100011;

        assertEquals(0, ColorUtil.getBlue(color0));
        assertEquals(16, ColorUtil.getBlue(color1));
        assertEquals(0, ColorUtil.getBlue(color2));
        assertEquals(170, ColorUtil.getBlue(color3));
        assertEquals(105, ColorUtil.getBlue(color4));
        assertEquals(0, ColorUtil.getBlue(color5));
        assertEquals(0, ColorUtil.getBlue(color6));
        assertEquals(17, ColorUtil.getBlue(color7));
    }

    @Test
    public void getColorTest(){
        int res = 0X00FFAA66;
        Color expect = new Color(255, 170, 102);

        assertEquals(expect, ColorUtil.getColor(res));
    }
}

package com.dv.image;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImageCreatorTest {

    @Test
    public void getPixelsTest() throws IOException {
        Color[][] matrix1 = {{new Color(100,100,100), new Color(100,100,100)},
                             {new Color(100,100,0), new Color(100,100,0)}};

        ImageCreator ic = new ImageCreator("8x8.png", "some.png", 2);

        assertArrayEquals(matrix1, ic.getPixels(2,1).getMatrix());
    }

    @Test
    public void getSquareTest() throws IOException {
        ImageCreator ic = new ImageCreator("8x8.png", "some.png", 2);


    }
}
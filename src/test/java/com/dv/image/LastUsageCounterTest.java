package com.dv.image;

import org.junit.Before;
import org.junit.Test;

import static com.dv.image.ImagesPixelModelProcessor.*;


public class LastUsageCounterTest {


    LastUsageCounter counter = new LastUsageCounter();


    @Before
    public void init(){
        for (int i = 0; i < 25; i++) {
            counter.setLastUsage(i);
            counter.setLastUsage(i * 40);
            counter.setLastUsage(i * 200);
            counter.setLastUsage(i * 400);
        }

        int k = 0;

        /*for (int i = 0; i < 10000; i++) {
            if()
        }*/

    }

    @Test
    public void testIsNearest() throws Exception {

    }
}
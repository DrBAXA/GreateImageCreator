package com.dv.image;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColorTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testCompareTo() throws Exception {
        Color c = new Color(0,0,0);
        Color c1 = new Color(10,10,10);
        Color c2 = new Color(255,255,255);
        Color c3 = new Color(10,10,20);
        Color c4 = new Color(10,20,10);
        Color c5 = new Color(10,11,20);
        Color c6 = new Color(15,15,10);

        assertEquals(-300, c.compareTo(c1));
        assertEquals(-195075, c.compareTo(c2));
        assertEquals(-180075, c1.compareTo(c2));
        assertEquals(300, c1.compareTo(c));
        assertEquals(195075, c2.compareTo(c));
        assertEquals(180075, c2.compareTo(c1));

        assertTrue(c.compareTo(c3) == c.compareTo(c4));

        assertTrue(abs(c.compareTo(c3)) < abs(c.compareTo(c5)));
        assertTrue(abs(c.compareTo(c3)) > abs(c.compareTo(c6)));
    }

    @Test
    public void hashCodeAndEqualsTest(){
        Color c1 = new Color(10,10,10);
        Color c2 = new Color(10,10,10);

        assertTrue(c1.equals(c2));
        assertTrue(c1.hashCode() == c2.hashCode());
    }

    /*@Test
    public void randomHashCodeTest(){
        HashSet<Integer> hash = new HashSet<>(26777216, 1.99f);

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {
                    Color c = new Color(i,j,k);
                    hash.add(c.hashCode());
                }
            }
        }
        System.out.println(hash.size());
        assertTrue(16777216 - hash.size() < 100);
    }*/

    @Test(timeout = 1000)
    public void hashCodeTimeTest(){
        int s = 0;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {
                    Color c = new Color(i,j,k);
                    s += c.hashCode();
                }
            }
        }
        System.out.println(s);
    }




}
package com.dv.image;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImagesPixelModelProcessorTest {

    ImagesPixelModelProcessor processor;
    Collection<String> images;

    @Before
    public void init(){
        processor = mock(ImagesPixelModelProcessor.class, Mockito.CALLS_REAL_METHODS);

        images = new ArrayList<>();
        images.add("8x8.png");
        images.add("google_share.png");
        images.add("1.jpg");
        images.add("5.jpg");
        images.add("index.jpeg");
        images.add("wrong_file_name.png");

        when(processor.getImages()).thenReturn(images);

        processor.setResolution(4);
    }

    @Test
    public void processSingleTest(){
        assertFalse(processor.processSingleImage("wrong_file_name").isPresent());
        assertTrue(processor.processSingleImage(images.iterator().next()).isPresent());
    }

    @Test
    public void processTest(){
        assertEquals(5, processor.process().size());
        assertEquals(4, processor.process().get("8x8.png").getMatrix().length);
    }


}
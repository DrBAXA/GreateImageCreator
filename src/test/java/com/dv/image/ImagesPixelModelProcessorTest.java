package com.dv.image;

import junit.framework.TestCase;
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
    ImagePixelModel imagePixelModel;

    @Before
    public void init(){
        imagePixelModel = new ImagePixelModel(2, new Color[][] {{new Color(190, 128, 64), new Color(50, 75, 75)},
                                                                {new Color(100, 150, 150), new Color(25,38, 38)}});

        processor = mock(ImagesPixelModelProcessor.class, Mockito.CALLS_REAL_METHODS);

        images = new ArrayList<>();
        images.add("8x8.png");
        images.add("google_share.png");
        images.add("1.jpg");
        images.add("5.jpg");
        images.add("index.jpeg");
        images.add("wrong_file_name.png");

        when(processor.getImages()).thenReturn(images);

        processor.setResolution(2);
        processor.process();
    }

    @Test
    public void processSingleTest(){
        assertFalse(processor.processSingleImage("wrong_file_name").isPresent());
        assertTrue(processor.processSingleImage(images.iterator().next()).isPresent());
    }

    @Test
    public void processTest(){
        assertEquals(5, processor.getModelMap().size());
        assertEquals(2, processor.getModelMap().get("8x8.png").getMatrix().length);
    }

    @Test
    public void getMostAppropriateTest(){
        TestCase.assertEquals("8x8.png", processor.getMostAppropriate(imagePixelModel));
    }


}
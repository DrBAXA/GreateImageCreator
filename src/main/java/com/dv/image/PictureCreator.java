package com.dv.image;

import java.awt.image.BufferedImage;
import java.util.Map;

public class PictureCreator {

    private BufferedImage image;
    private Map<String, ImagePixelModel> pixelImages;

    public PictureCreator(Map<String, ImagePixelModel> pixelImages) {
        this.pixelImages = pixelImages;
    }

    protected ImagePixelModel getPixelModel(int size, int x,int y){
        return null;
    }

    protected String findMostAppropriateImage(ImagePixelModel model){
        return null;
    }


}

package com.dv.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static java.lang.Math.round;

public class ImagePixelModel implements Serializable{

    private int resolution;
    private String fileName;
    private Color[][] matrix;
    private BufferedImage image;

    public ImagePixelModel(int resolution, String fileName) throws IOException {
        this.resolution = resolution;
        this.fileName = fileName;
        this.matrix = new Color[resolution][resolution];
        process();
    }

    private void process() throws IOException {
        image = ImageIO.read(new File(fileName));

        for(int i = 0; i < resolution; i++){
            for(int j = 0; j < resolution; j++){
                matrix[i][j] = calcBlockColor(i, j);
            }
        }
    }

    protected Color calcBlockColor(int x, int y){
        int r = 0, g = 0, b = 0;
        float k = 0;
        for(int i = x*image.getWidth()/resolution; i < (x+1)*image.getWidth()/resolution && i < image.getWidth(); i++){
            for(int j = y*image.getWidth()/resolution; j < (y+1)*image.getWidth()/resolution && y < image.getHeight(); j++){
                int rgb = image.getRGB(j,i);
                r += getRed(rgb);
                g += getGreen(rgb);
                b += getBlue(rgb);
                k++;
            }
        }
        return new Color(round(r / k), round(g / k), round(b / k));
    }

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

    public Color[][] getMatrix() {
        return matrix;
    }
}

package com.dv.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import static java.lang.Math.round;

public class ImagePixelModel implements Serializable{

    private int resolution;
    private String fileName;
    private Color[][] matrix;

    public ImagePixelModel(int resolution, String fileName) throws IOException {
        this.resolution = resolution;
        this.fileName = fileName;
        this.matrix = new Color[resolution][resolution];
        BufferedImage image = ImageIO.read(new File(fileName));
        process(image);
    }

    protected void process(BufferedImage image){
        for(int i = 0; i < resolution; i++){
            for(int j = 0; j < resolution; j++){
                matrix[i][j] = calcBlockColor(image, i, j);
            }
        }
    }

    protected Color calcBlockColor(BufferedImage image, int x, int y){
        int r = 0, g = 0, b = 0;
        float k = 0;
        for(int i = x*image.getHeight()/resolution; i < (x+1)*image.getHeight()/resolution && i < image.getHeight(); i++){
            for(int j = y*image.getWidth()/resolution; j < (y+1)*image.getWidth()/resolution && y < image.getWidth(); j++){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImagePixelModel that = (ImagePixelModel) o;

        return resolution == that.resolution
                && fileName.equals(that.fileName)
                && Arrays.deepEquals(matrix, that.matrix);

    }

    @Override
    public int hashCode() {
        int result = resolution;
        result = 31 * result + fileName.hashCode();
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }


    public long compare(ImagePixelModel o) {
        if(this.equals(o)) return 0;
        if(this.resolution != o.resolution) throw new IllegalArgumentException();

        int diff = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j <matrix[0].length; j++) {
                int colorDiff = matrix[i][j].compareTo(o.matrix[i][j]);
                diff += colorDiff*colorDiff;
            }
        }
        return diff;
    }
}

package com.dv.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import static com.dv.image.ColorUtil.getBlue;
import static com.dv.image.ColorUtil.getGreen;
import static com.dv.image.ColorUtil.getRed;
import static java.lang.Math.round;

public class ImagePixelModel implements Serializable{

    private int resolution;
    private Color[][] matrix;

    public ImagePixelModel(int resolution, String fileName) throws IOException {
        this.resolution = resolution;
        this.matrix = new Color[resolution][resolution];
        BufferedImage image = ImageIO.read(new File(fileName));
        process(image);
    }

    public ImagePixelModel(int resolution, Color[][] matrix){
        if(resolution != matrix.length || resolution != matrix[0].length) throw new IllegalArgumentException("Matrix resolution and given is different!");
        this.resolution = resolution;
        this.matrix = matrix;
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
        int minValue = image.getHeight() < image.getWidth() ? image.getHeight() : image.getWidth();
        int partSize = minValue/resolution;

        for(int i = x*partSize; i < (x+1)*partSize; i++){
            for(int j = y*partSize; j < (y+1)*partSize; j++){
                int rgb = image.getRGB(j,i);
                r += getRed(rgb);
                g += getGreen(rgb);
                b += getBlue(rgb);
                k++;
            }
        }
        return new Color(round(r / k), round(g / k), round(b / k));
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
                && Arrays.deepEquals(matrix, that.matrix);

    }

    @Override
    public int hashCode() {
        int result = resolution;
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }


    public long compare(ImagePixelModel o) {
        if(this.equals(o)) return 0;
        if(this.resolution != o.resolution) throw new IllegalArgumentException();

        long diff = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j <matrix[0].length; j++) {
                long colorDiff = Math.abs(matrix[i][j].compareTo(o.matrix[i][j]));
                diff += colorDiff*colorDiff;
            }
        }
        return diff;
    }
}

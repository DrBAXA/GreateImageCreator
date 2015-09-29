package com.dv.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class ImageCreator {

    private static final Logger logger = LogManager.getLogger();

    private int resolution;
    private Collection<String> pixelFiles;
    private BufferedImage srcImage;
    private BufferedImage destImage;
    private File destImageFile;
    private Graphics graphics;

    private ImagesPixelModelProcessor processor;

    public ImageCreator(String sourceImage, String destinationImage, int resolution) throws IOException{
        this.srcImage = ImageIO.read(new File(sourceImage));
        destImageFile = new File(destinationImage);
        this.resolution = resolution;
        destImage = new BufferedImage(srcImage.getWidth()/resolution*60, srcImage.getHeight()/resolution*60, BufferedImage.TYPE_INT_RGB);
        graphics = destImage.getGraphics();
    }

    public void create() throws IOException, InterruptedException {
        prepareImage();
        writeFile();
    }

    private void prepareImage() throws IOException, InterruptedException {
        Thread[] threads = new Thread[4];

        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new ImageRunner(1, (srcImage.getWidth()/resolution)/4));
            threads[i].start();
        }

        for (int i = 0; i < 4; i++) {
            threads[i].join();
        }
    }

    private void runThreads(){

    }

    private void writeFile() throws IOException {
        ImageIO.write(destImage, "jpg", destImageFile);
    }

    private void write(BufferedImage img, int x, int y){
        graphics.drawImage(img.getScaledInstance(60,60,Image.SCALE_SMOOTH), x*60, y*60, null);
    }

    protected ImagePixelModel getPixels(int x, int y){
        if(x > srcImage.getWidth()) throw new IllegalArgumentException();
        if(y > srcImage.getHeight()) throw new IllegalArgumentException();

        Color[][] matrix = new Color[resolution][resolution];

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                matrix[j][i] = ColorUtil.getColor(srcImage.getRGB(x*resolution+i,y*resolution+j));
            }
        }

        return new ImagePixelModel(resolution, matrix);
    }

    public void setProcessor(ImagesPixelModelProcessor processor) {
        this.processor = processor;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ImagesPixelModelProcessor processor = new ImagesPixelModelDirectoryProcessor(1, "/home/vdanyliuk/Завантаження/1");
        processor.process();
        ImageCreator ic = new ImageCreator("/home/vdanyliuk/Завантаження/2.jpg", "/home/vdanyliuk/Завантаження/2_.jpg", 1);
        ic.setProcessor(processor);
        ic.create();
    }

    private class ImageRunner implements Runnable{

        private int y;
        private int length;

        public ImageRunner(int y, int length) {
            this.y = y;
            this.length = length;
        }

        @Override
        public void run() {
            for (int i = y*length; i < srcImage.getWidth()/resolution && i < (y+1)*length; i++) {
                for (int j = 0; j < srcImage.getHeight() / resolution; j++) {
                    BufferedImage partialImage = null;
                    try {
                        partialImage = ImageIO.read(new File(processor.getMostAppropriate(getPixels(i, j))));
                        write(partialImage, i, j);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

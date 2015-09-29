package com.dv.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public abstract class ImagesPixelModelProcessor {

    private static final Logger logger = LogManager.getLogger();

    private int resolution;

    public Map<String, ImagePixelModel> getModelMap() {
        return modelMap;
    }

    private Map<String, ImagePixelModel> modelMap;

    public ImagesPixelModelProcessor(int resolution) {
        this.resolution = resolution;
    }

    abstract public Collection<String> getImages();

    public final Map<String, ImagePixelModel> process(){
        modelMap = new TreeMap<>();

        getImages()
                .parallelStream()
                    .forEach((fileName) -> processSingleImage(fileName).ifPresent((val) -> modelMap.put(fileName, val)));

        return modelMap;
    }

    public String getMostAppropriate(ImagePixelModel pm){
        long min = Long.MAX_VALUE;
        String result = null;
        for (Map.Entry<String, ImagePixelModel> entry : modelMap.entrySet()){
            long diff = pm.compare(entry.getValue());
            if(diff < min){
                min = diff;
                result = entry.getKey();
            }
        }
        return result;
    }

    protected Optional<ImagePixelModel> processSingleImage(String fileName){
        try {
            Optional<ImagePixelModel> o = Optional.of(new ImagePixelModel(resolution, fileName));
            logger.debug("Processing file " + fileName + " successfully finished.");
            return o;
        }catch (Exception e){
            logger.error("Error during processing file " + fileName + ". File will be skipped. " + e.getMessage());
            logger.debug(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}

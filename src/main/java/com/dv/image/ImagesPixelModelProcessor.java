package com.dv.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ImagesPixelModelProcessor {

    private static final Logger logger = LogManager.getLogger();

    private int resolution;

    public ImagesPixelModelProcessor(int resolution) {
        this.resolution = resolution;
    }

    abstract public Collection<String> getImages();

    public final Map<String, ImagePixelModel> process(){
        HashMap<String, ImagePixelModel> result = new HashMap<>();

        getImages()
                .stream()
                    .forEach((fileName) -> processSingleImage(fileName).ifPresent((val) -> result.put(fileName, val)));

        return result;
    }

    protected Optional<ImagePixelModel> processSingleImage(String fileName){
        try {
            Optional<ImagePixelModel> o = Optional.of(new ImagePixelModel(resolution, fileName));
            logger.debug("Processing file " + fileName + " succesfully finished.");
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

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
    private Map<String, ImagePixelModel> modelMap;
    private Map<String, Integer> usagesCountMap;

    public Map<String, ImagePixelModel> getModelMap() {
        return modelMap;
    }

    public ImagesPixelModelProcessor(int resolution) {
        this.resolution = resolution;
    }

    abstract public Collection<String> getImages();

    public final Map<String, ImagePixelModel> process() {
        modelMap = new HashMap<>();
        usagesCountMap = new HashMap<>();

        getImages().parallelStream()
                .forEach((fileName) -> processSingleImage(
                        fileName).ifPresent((val) -> {
                            modelMap.put(fileName, val);
                            usagesCountMap.put(fileName, 0);
                }));

        return modelMap;
    }

    public String getMostAppropriate(ImagePixelModel pm) {
        long min = Long.MAX_VALUE;
        String result = null;
        for (Map.Entry<String, ImagePixelModel> entry : modelMap.entrySet()) {
            int usagesCount = usagesCountMap.get(entry.getKey());
            long usageCoefficient = (usagesCount+1)*(usagesCount+1);
            long diff = pm.compare(entry.getValue()) * usageCoefficient;
            if (diff < min) {
                min = diff;
                result = entry.getKey();
            }
        }
        usagesCountMap.put(result, usagesCountMap.get(result) + 1);
        return result;
    }

    protected Optional<ImagePixelModel> processSingleImage(String fileName) {
        try {
            Optional<ImagePixelModel> o = Optional.of(new ImagePixelModel(resolution, fileName));
            logger.debug("Processing file " + fileName + " successfully finished.");
            return o;
        } catch (Exception e) {
            logger.error("Error during processing file " + fileName + ". File will be skipped. " + e.getMessage());
            logger.debug(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}

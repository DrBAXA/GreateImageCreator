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
    private Map<String, LastUsageCounter> lastUsagesMap;


    public Map<String, ImagePixelModel> getModelMap() {
        return modelMap;
    }

    public ImagesPixelModelProcessor(int resolution) {
        this.resolution = resolution;
    }

    abstract public Collection<String> getImages();

    public final Map<String, ImagePixelModel> process() {
        modelMap = new HashMap<>();
        lastUsagesMap = new HashMap<>();

        getImages().parallelStream()
                .forEach((fileName) -> processSingleImage(fileName).ifPresent((val) -> {
                    modelMap.put(fileName, val);
                    lastUsagesMap.put(fileName, new LastUsageCounter());
                }));

        return modelMap;
    }

    public synchronized String getMostAppropriate(final ImagePixelModel pm,final int counter) {
        long min = Long.MAX_VALUE;
        String result = null;
        for (Map.Entry<String, ImagePixelModel> entry : modelMap.entrySet()) {
            long usageCoefficient = 1;
            long diff = pm.compare(entry.getValue()) * usageCoefficient;
            if (diff < min && !lastUsagesMap.get(entry.getKey()).isNearest(counter)) {
                min = diff;
                result = entry.getKey();
            }
        }
        lastUsagesMap.get(result).setLastUsage(counter);
        return result;
    }




    public static class LastUsageCounter{

        int[] usages = new int[100];
        int index = 0;

        public LastUsageCounter() {
            for (int i = 0; i < 100; i++) {
                usages[i] = Integer.MAX_VALUE;
            }
        }

        public void setLastUsage(int lastUsage){
            if(index == 100){
                index = 0;
            }
            usages[index++] = lastUsage;
        }

        public boolean isNearest(int counter){
            for(int i : usages){
                if(isNearestOne(counter, i)) return true;
            }
            return false;
        }

        private boolean isNearestOne(int counter, int lastUsage){
            if(counter == 0) return false;
            if(lastUsage == Integer.MAX_VALUE) return false;
            if(Math.abs(counter - lastUsage) < 4) return true;
            if((Math.abs((counter - lastUsage)%240) < 4) || (Math.abs((counter - lastUsage)%240) > 236)) return true;
            return false;
        }
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

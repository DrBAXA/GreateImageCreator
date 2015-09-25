package com.dv.image;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ImagesPixelModelProcessor {

    private int resolution;

    public ImagesPixelModelProcessor(int resolution) {
        this.resolution = resolution;
    }

    abstract public Collection<String> getImages();

    public final Map<String, ImagePixelModel> process() throws IOException{
        HashMap<String, ImagePixelModel> result = new HashMap<>();

        getImages()
                .stream()
                    .forEach((fileName) -> processSingleImage(fileName)
                                                .ifPresent((val) -> result.put(fileName, val)));

        return result;
    }

    private Optional<ImagePixelModel> processSingleImage(String fileName){
        try {
            return Optional.of(new ImagePixelModel(resolution, fileName));
        }catch (IOException ioe){
            return Optional.empty();
        }
    }
}

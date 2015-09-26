package com.dv.image;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ImagesPixelModelDirectoryProcessor extends ImagesPixelModelProcessor{

    private Collection<String> files = new ArrayList<>();

    public ImagesPixelModelDirectoryProcessor(int resolution, String dirPath) {
        super(resolution);
        addDirFiles(new File(dirPath));
    }

    private void addDirFiles(File dir){
        Arrays.stream(dir.listFiles()).forEach(this::processFile);
    }

    private void processFile(File file){
        if(file.isFile()) {
            String name = file.getName();
            if(name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) {
                files.add(file.getPath());
            }
        }
        else if(file.isDirectory()){
            addDirFiles(file);
        }
    }

    @Override
    public Collection<String> getImages() {
        return files;
    }
}

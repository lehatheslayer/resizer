package mts.teta.resizer.imageprocessor.commads.impl;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;
import mts.teta.resizer.imageprocessor.commads.Command;

import java.io.File;
import java.io.IOException;

public class CropCommand implements Command {

    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final File inputFile;
    private final File outputFile;

    public CropCommand(String[] args, File inputFile, File outputFile) {

        this.width = Integer.parseInt(args[0]);
        this.height = Integer.parseInt(args[1]);
        this.x = Integer.parseInt(args[2]);
        this.y = Integer.parseInt(args[3]);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void execute() {

        MarvinImage image = MarvinImageIO.loadImage(inputFile.getPath());
        MarvinPluginCollection.crop(image.clone(), image, x, y, width, height);
        MarvinImageIO.saveImage(image, outputFile.getPath());
    }
}

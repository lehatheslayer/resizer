package mts.teta.resizer.imageprocessor.commads.impl;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvinplugins.MarvinPluginCollection;
import mts.teta.resizer.imageprocessor.commads.Command;

import java.io.File;

public class BlurCommand implements Command {

    private final int radius;
    private final File inputFile;
    private final File outputFile;

    public BlurCommand(String[] args, File inputFile, File outputFile) {

        this.radius = Integer.parseInt(args[0]);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void execute() {

        MarvinImage image = MarvinImageIO.loadImage(inputFile.getPath());
        MarvinPluginCollection.gaussianBlur(image.clone(), image, radius);
        MarvinImageIO.saveImage(image, outputFile.getPath());
    }
}

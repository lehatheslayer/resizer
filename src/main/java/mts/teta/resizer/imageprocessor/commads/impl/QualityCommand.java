package mts.teta.resizer.imageprocessor.commads.impl;

import mts.teta.resizer.imageprocessor.commads.Command;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QualityCommand implements Command {

    private final double quality;
    private final File inputFile;
    private final File outputFile;

    public QualityCommand(String[] args, File inputFile, File outputFile) {

        this.quality = Double.parseDouble(args[0]);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void execute() throws IOException {

        BufferedImage img = ImageIO.read(inputFile);
        int width = img.getWidth();
        int height = img.getHeight();
        Thumbnails.of(inputFile)
                .size(width, height)
                .outputQuality(quality)
                .toFile(outputFile);
    }
}

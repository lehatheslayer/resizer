package mts.teta.resizer.imageprocessor.commads.impl;

import mts.teta.resizer.imageprocessor.commads.Command;
import net.coobird.thumbnailator.Thumbnails;

import javax.swing.*;
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

        ImageIcon img = new ImageIcon(inputFile.getPath());
        Thumbnails.of(inputFile)
                .forceSize(img.getImage().getWidth(null), img.getImage().getHeight(null))
                .outputQuality(quality)
                .toFile(outputFile);
    }
}

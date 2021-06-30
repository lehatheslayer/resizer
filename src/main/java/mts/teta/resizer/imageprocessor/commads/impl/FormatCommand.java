package mts.teta.resizer.imageprocessor.commads.impl;

import mts.teta.resizer.imageprocessor.commads.Command;
import net.coobird.thumbnailator.Thumbnails;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FormatCommand implements Command {

    private final String format;
    private final File inputFile;
    private final File outputFile;

    public FormatCommand(String[] args, File inputFile, File outputFile) {

        this.format = args[0];
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void execute() throws IOException {

        ImageIcon img = new ImageIcon(inputFile.getPath());
        Thumbnails.of(inputFile.getPath())
                .size(img.getImage().getWidth(null), img.getImage().getHeight(null))
                .outputFormat(format)
                .toFile(outputFile.getPath());
    }
}

package mts.teta.resizer.imageprocessor.commads.impl;

import mts.teta.resizer.imageprocessor.commads.Command;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ResizeCommand implements Command {

    private final int width;
    private final int height;
    private final File inputFile;
    private final File outputFile;

    public ResizeCommand(String[] args, File inputFile, File outputFile) {

        this.width = Integer.parseInt(args[0]);
        this.height = Integer.parseInt(args[1]);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void execute() throws IOException {

        Thumbnails.of(inputFile)
                .forceSize(width, height)
                .toFile(outputFile);
    }
}

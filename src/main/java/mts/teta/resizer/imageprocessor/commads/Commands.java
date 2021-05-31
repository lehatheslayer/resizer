package mts.teta.resizer.imageprocessor.commads;

import mts.teta.resizer.imageprocessor.commads.impl.*;

import java.io.File;

public enum Commands {

    RESIZE {
        @Override
        public Command getCommand(String[] args, File inputFile, File outputFile) {

            return new ResizeCommand(args, inputFile, outputFile);
        }
    },

    QUALITY {
        @Override
        public Command getCommand(String[] args, File inputFile, File outputFile) {

            return new QualityCommand(args, inputFile, outputFile);
        }
    },

    CROP {
        @Override
        public Command getCommand(String[] args, File inputFile, File outputFile) {

            return new CropCommand(args, inputFile, outputFile);
        }
    },

    BLUR {
        @Override
        public Command getCommand(String[] args, File inputFile, File outputFile) {

            return new BlurCommand(args, inputFile, outputFile);
        }
    },

    FORMAT {
        @Override
        public Command getCommand(String[] args, File inputFile, File outputFile) {

            return new FormatCommand(args, inputFile, outputFile);
        }
    };

    public abstract Command getCommand(String[] args, File inputFile, File outputFile);
}

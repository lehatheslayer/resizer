package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import mts.teta.resizer.imageprocessor.commads.Commands;
import picocli.CommandLine;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "convert", mixinStandardHelpOptions = true, version = "resizer 1.0.1",
        description = "This utility will allow you to resize the image for other MTS services: " +
                "changing the images of advertising banners, previews for albums " +
                "and movie covers in small resolution, and others.")
public class ResizerApp implements Callable<Integer> {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(description = "The input file")
    private String inputFile;

    @CommandLine.Option(names = {"-r", "--resize"}, arity = "2", description = "Resize the image")
    private String[] resizingArgs;

    @CommandLine.Option(names = {"-q", "--quality"}, description = "JPEG/PNG compression level", arity = "1")
    private String[] quality;

    @CommandLine.Option(names = {"-c", "--crop"}, arity = "4", description = "сut out one or more rectangular regions of the image")
    private String[] croppingArgs;

    @CommandLine.Option(names = {"-b", "--blur"}, description = "reduce image noise and reduce detail levels", arity = "1")
    private String[] radius;

    @CommandLine.Option(names = {"-f", "--format"}, description = "the image format type", arity = "1")
    private String[] format;

    @CommandLine.Parameters(description = "The output file")
    private String outputFile; // File

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws BadAttributesException, IOException {

        try {
            validate();
        } catch (BadAttributesException e) {
            throw new BadAttributesException(e.getMessage());
        } catch (IIOException e) {
            throw new IIOException(e.getMessage());
        }

        File file1 = new File(inputFile);
        File file2 = new File(outputFile);
        if (spec != null) {
            try {
                // For console run
                List<CommandLine.Model.OptionSpec> options = spec.options();

                for (CommandLine.Model.OptionSpec opt : options) {
                    if (opt.getValue() != null && !opt.getValue().toString().equals("false")) {
                        Commands.valueOf(opt.longestName().substring(2).toUpperCase(Locale.ROOT))
                                .getCommand(opt.getValue(), file1, file2)
                                .execute();

                        file1 = file2;
                    }
                }

                return 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // For tests
            try {
                if (resizingArgs != null) {
                    Commands.RESIZE.getCommand(resizingArgs, file1, file2).execute();
                    file1 = file2;
                }

                if (quality != null) {
                    Commands.QUALITY.getCommand(quality, file1, file2).execute();
                    file1 = file2;
                }

                if (croppingArgs != null) {
                    Commands.CROP.getCommand(croppingArgs, file1, file2).execute();
                    file1 = file2;
                }

                if (radius != null) {
                    Commands.BLUR.getCommand(radius, file1, file2).execute();
                    file1 = file2;
                }

                if (format != null) {
                    Commands.FORMAT.getCommand(format, file1, file2).execute();
                }

                return 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void validate() throws IOException, BadAttributesException {

        File file1 = new File(inputFile);

        if (!inputFile.substring(inputFile.length() - 3).toLowerCase(Locale.ROOT).equals("png")
                && !inputFile.substring(inputFile.length() - 3).toLowerCase(Locale.ROOT).equals("jpg")
                && !inputFile.substring(inputFile.length() - 4).toLowerCase(Locale.ROOT).equals("jpeg")) {
            throw new IIOException("Can't read input file!");
//            throw new IIOException("IIOException: extension of file " + inputFile[0] + " is wrong");
        }

        if (!file1.exists()) {
            throw new IIOException("Can't read input file!");
//            throw new IIOException("IIOException: file " + inputFile[0] + " doesn't exist");
        }

        if (file1.isDirectory()) {
            throw new IIOException("Can't read input file!");
//            throw new IIOException("IIOException: file " + inputFile[0] + " is not directory");
        }

        if (ImageIO.read(file1) == null) {
            throw new IIOException("Can't read input file!");
//            throw new IIOException("IIOException: file " + inputFile[0] + " is not image");
        }

        if (!file1.canRead()) {
            throw new IIOException("Can't read input file!");
//            throw new IIOException("IIOException: file " + inputFile[0] + " can't be read");
        }

        ImageIcon img = new ImageIcon(inputFile);
        int width = img.getImage().getWidth(null);
        int height = img.getImage().getHeight(null);

        if (resizingArgs != null) {
            try {
                int a = Integer.parseInt(resizingArgs[0]);
                int b = Integer.parseInt(resizingArgs[1]);

                if (a <= 0 || b <= 0) {
                    throw new BadAttributesException("Please check params!");
//                    throw new BadAttributesException("BadAttributesException: arguments of option RESIZE are wrong");
                }
            } catch (NumberFormatException e) {
                throw new BadAttributesException("Please check params!");
//                throw new BadAttributesException("BadAttributesException: arguments of option RESIZE are wrong");
            }
        }

        if (quality != null) {
            try {
                double a = Double.parseDouble(quality[0]);

                if (a <= 0) {
                    throw new BadAttributesException("Please check params!");
//                    throw new BadAttributesException("BadAttributesException: argument of option QUALITY is wrong");
                }
            } catch (NumberFormatException e) {
                throw new BadAttributesException("Please check params!");
//                throw new BadAttributesException("BadAttributesException: argument of option QUALITY is wrong");
            }
        }

        if (croppingArgs != null) {
            try {
                int a = Integer.parseInt(croppingArgs[0]);
                int b = Integer.parseInt(croppingArgs[1]);
                int c = Integer.parseInt(croppingArgs[2]);
                int d = Integer.parseInt(croppingArgs[3]);

                if (a <= 0 || b <= 0 || c <= 0 || d <= 0 || c > width || d > height) {
                    throw new BadAttributesException("Please check params!");
//                    throw new BadAttributesException("BadAttributesException: arguments of option CROP are wrong");
                }
            } catch (NumberFormatException e) {
                throw new BadAttributesException("Please check params!");
//                throw new BadAttributesException("BadAttributesException: arguments of option CROP are wrong");
            }
        }

        if (radius != null) {
            try {
                int a = Integer.parseInt(radius[0]);

                if (a <= 0) {
                    throw new BadAttributesException("Please check params!");
//                    throw new BadAttributesException("BadAttributesException: argument of option BLUR is wrong");
                }
            } catch (NumberFormatException e) {
                throw new BadAttributesException("Please check params!");
//                throw new BadAttributesException("BadAttributesException: argument of option BLUR is wrong");
            }
        }

        if (format != null) {
            if (!format[0].toLowerCase(Locale.ROOT).equals("png")
                    && !format[0].toLowerCase(Locale.ROOT).equals("jpg")
                    && !format[0].toLowerCase(Locale.ROOT).equals("jpeg")) {
                throw new BadAttributesException("Please check params!");
//                throw new BadAttributesException("BadAttributesException: wrong format " + format[0]);
            }
        }
    }

    // Setters for tests
    public void setInputFile(File file) {
        inputFile = file.getPath();
    }

    public void setOutputFile(File file) {
        outputFile = file.getPath();
    }

    public void setResizeWidth(Integer width) {
        resizingArgs = new String[2];
        resizingArgs[0] = String.valueOf(width);
    }

    public void setResizeHeight(Integer height) {
        resizingArgs[1] = String.valueOf(height);
    }

    public void setQuality(Integer quality) {
        this.quality = new String[1];
        this.quality[0] = String.valueOf((double) (quality / 100));
    }

    public void setBlurRadius(Integer radius) {
        this.radius = new String[1];
        this.radius[0] = String.valueOf(radius);
    }

    public void setFormat(String format) {
        this.format = new String[1];
        this.format[0] = format;
    }
}

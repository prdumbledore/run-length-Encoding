package main.java;

import org.kohsuke.args4j.*;
import java.io.*;

public class Main {
    @Option(name = "-z", metaVar = "Zip", usage = "File packaging")
    private boolean zip;

    @Option(name = "-u", metaVar = "Unzip", usage = "File unpacking")
    private boolean unzip;

    @Option(name = "-out", metaVar = "outputFile", usage = "Output document", required = true)
    private File outputFile;

    @Argument(required = true, metaVar = "inputFileName", usage = "Input document name")
    private File inputFile;


    public static void main(String[] args) {
        new Main().launch(args);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Command Line: java -jar packrle.jar [-z|-u] [-out outputName.txt] inputName.txt");
            parser.printUsage(System.err);
            return;
        }
        PackRLE packRLE = new PackRLE(inputFile, zip, unzip);
        try {
            String result = packRLE.newFile();
            System.out.println("Файл называется:" + result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
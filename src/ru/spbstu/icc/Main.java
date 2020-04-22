package ru.spbstu.icc;

import org.kohsuke.args4j.*;

import java.io.*;

public class Main {
    @Option(name = "-z", metaVar = "packingFlag", usage = "File packaging")
    private boolean packingFlag;

    @Option(name = "-u", metaVar = "unpackingFlag", usage = "File unpacking", forbids = {"-z"})
    private boolean unpackingFlag;

    @Option(name = "-out", metaVar = "outputFlag", usage = "Output document")
    private boolean outputFlag;

    @Argument(required = true, metaVar = "inputFile", usage = "Input document name")
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
        PackRLE packRLE = new PackRLE(inputFile, packingFlag, unpackingFlag, outputFlag);
        try {
            if (packingFlag || unpackingFlag) {
                String result = packRLE.newFile();
                System.out.println("File name:" + result);
            } else throw new IllegalArgumentException ("Option \"-z\" or \"-u\" is required");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
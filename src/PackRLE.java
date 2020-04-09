import java.io.*;

public class PackRLE {

    private File inputFile;
    private boolean zip;
    private boolean unzip;

    public PackRLE(File inputFile, boolean zip, boolean unzip) {
        this.inputFile = inputFile;
        this.zip = zip;
        this.unzip = unzip;
    }

    public String newFile() throws IOException {
        StringBuilder outputName = new StringBuilder(inputFile.getName());
        outputName.replace(inputFile.getName().length() - 3, inputFile.getName().length(), "");
        StringBuilder newFile = new StringBuilder();
        if (zip) {
            outputName.append("Zipped.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                int counterRepetitions = 0;
                int prevChar = -1;
                int newChar;
                while ((newChar = reader.read()) != -1) {
                    if (prevChar > 32 || prevChar == -1) {
                        if (newChar == prevChar && counterRepetitions < 9) {
                            counterRepetitions++;
                        } else if (prevChar != -1) {
                            newFile.append(counterRepetitions);
                            newFile.append((char) prevChar);
                            counterRepetitions = 0;
                        }
                    } else {
                        newFile.append((char) prevChar);
                    }
                    prevChar = newChar;
                }
                newFile.append(counterRepetitions);
                newFile.append((char) prevChar);
            }
        } else if (unzip) {
            outputName.append("Unzipped.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                boolean counter = false;
                int counterRepetitions = 0;
                int newChar;
                while ((newChar = reader.read()) != -1) {
                    if (newChar > 32) {
                        if (counter) {
                            while (counterRepetitions != 0) {
                                newFile.append((char) newChar);
                                counterRepetitions--;
                            }
                            counter = false;
                        } else {
                            counterRepetitions = newChar - '0' + 1;
                            counter = true;
                        }
                    } else {
                        newFile.append((char) newChar);
                        counter = false;
                    }

                }
            }
        }
        FileWriter writer = new FileWriter(outputName.toString());
        writer.write(newFile.toString());
        writer.close();
        return outputName.toString();
    }

}

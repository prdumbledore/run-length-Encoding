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

    /**
     * Программа посимвольно читает входяший файл, подсчитывает кол-во повторов подряд символа и записывает в новый
     * в формате двух символов: [кол-во повторов от 0-9 (где 0 - это 1 повтор)][сам символ]
     *
     * Программа не считает кол-во повторов управляющих символов, таких как: пробел, переход на новую строку и т.д.
     * Управляющие символы находятся в диапазоне от 0 до 31, 32 - пробел.
     * Так же это сделано для улучшения коэффициента сжатия.
     *
     * @return output file name
     * @throws IOException
     */
    public String newFile() throws IOException {
        StringBuilder outputName = new StringBuilder(inputFile.getName());
        outputName.replace(inputFile.getName().length() - 4, inputFile.getName().length(), "");
        StringBuilder newFile = new StringBuilder();
        if (zip) {
            outputName.append("_Zip.txt");
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
                if (prevChar > 32) newFile.append(counterRepetitions);
                newFile.append((char) prevChar);
            }
        } else if (unzip) {
            outputName.append("_Unzip.txt");
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

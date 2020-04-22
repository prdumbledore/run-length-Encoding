package ru.spbstu.icc;

import java.io.*;
import java.util.Map;

public class PackRLE {

    private final File inputFile;
    private final boolean packingFlag;
    private final boolean unpackingFlag;
    private final boolean outputFlag;

    public PackRLE(File inputFile, boolean packingFlag, boolean unpackingFlag, boolean outputFlag) {
        this.inputFile = inputFile;
        this.packingFlag = packingFlag;
        this.unpackingFlag = unpackingFlag;
        this.outputFlag = outputFlag;
    }

    /**
     * @return output file name
     * @throws IOException
     */
    public String newFile() throws IOException {
        Map<Integer, Character> digitsTable = DigitsTable.digitsTable;
        StringBuilder outputName = new StringBuilder(inputFile.getName());
        outputName.replace(inputFile.getName().length() - 4, inputFile.getName().length(), "");
        StringBuilder newFile = new StringBuilder();
        if (packingFlag) {
            if (outputFlag)  outputName.replace(0, inputFile.getName().length(), "output.txt");
            else outputName.append("_packed.txt");
            packing(newFile, digitsTable);
        }
        else if (unpackingFlag) {
            if (outputFlag)  outputName.replace(0, inputFile.getName().length(), "output.txt");
            else outputName.append("_unpacked.txt");
            unpacking(newFile, digitsTable);
        }
        FileWriter writer = new FileWriter(outputName.toString());
        writer.write(newFile.toString());
        writer.close();
        return outputName.toString();
    }

    /**
     * Программа посимвольно читает входяший файл, подсчитывает кол-во повторов подряд символа и записывает в новый
     * в формате двух символов: [кол-во повторов от 0-9-A-Z-a-z (где 0 - это 1 повтор, а z - 62 повтора)][сам символ].
     *
     * Программа не считает кол-во повторов управляющих символов, таких как: пробел, переход на новую строку и т.д.
     * Управляющие символы находятся в диапазоне от 0 до 31, 32 - пробел.
     * Так же это сделано для улучшения коэффициента сжатия.
     *
     * @param newFile текст нового файла
     * @param digitsTable таблица для перевода чисел в буквенную форму
     * @throws IOException
     */
    private void packing(StringBuilder newFile, Map<Integer, Character> digitsTable) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int counterRepetitions = 0;
            int prevChar = -1;
            int newChar;
            while ((newChar = reader.read()) != -1) {
                if (prevChar > 32 || prevChar == -1) {
                    if (newChar == prevChar && counterRepetitions < 62) {
                        counterRepetitions++;
                    } else if (prevChar != -1) {
                        newFile.append(digitsTable.get(counterRepetitions));
                        newFile.append((char) prevChar);
                        counterRepetitions = 0;
                    }
                } else {
                    newFile.append((char) prevChar);
                }
                prevChar = newChar;
            }
            if (prevChar > 32) newFile.append(digitsTable.get(counterRepetitions));
            if (prevChar != -1) newFile.append((char) prevChar);
        }
    }

    private void unpacking(StringBuilder newFile, Map<Integer, Character> digitsTable) throws IOException {
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
                        counterRepetitions = DigitsTable.getValue(digitsTable, (char) newChar) + 1;
                        counter = true;
                    }
                } else {
                    newFile.append((char) newChar);
                    counter = false;
                }

            }
        }
    }

}

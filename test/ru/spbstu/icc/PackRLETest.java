package ru.spbstu.icc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PackRLETest {

    void assertEqualsFile(String inputFile) throws IOException {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb1.append(line).append(System.lineSeparator());
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb2.append(line).append(System.lineSeparator());
            }
        }
        assertEquals(sb1.toString(), sb2.toString());
    }

    @Test
    public void packingTest1() throws IOException {
        String[] args = {"-z", "-out", "test/resources/input1.txt"};
        new Main();
        Main.main(args);
        assertEqualsFile("test/resources/output1.txt"
        );
    }

    @Test
    public void packingTest2() throws IOException {
        String[] args = {"-z", "-out", "test/resources/input2.txt"};
        Main.main(args);
        assertEqualsFile("test/resources/output2.txt"
        );
    }

    @Test
    public void packingTest3() throws IOException {
        String[] args = {"-z", "-out", "test/resources/input3.txt"};
        Main.main(args);
        assertEqualsFile("test/resources/output3.txt"
        );
    }

    @Test
    public void unpackingTest1() throws IOException {
        String[] args = {"-u", "-out", "test/resources/output1.txt"};
        new Main();
        Main.main(args);
        assertEqualsFile("test/resources/input1.txt"
        );
    }

    @Test
    public void unpackingTest2() throws IOException {
        String[] args = {"-u", "-out", "test/resources/output2.txt"};
        new Main();
        Main.main(args);
        assertEqualsFile("test/resources/input2.txt"
        );
    }

    @Test
    public void unpackingTest3() throws IOException {
        String[] args = {"-u", "-out", "test/resources/output3.txt"};
        new Main();
        Main.main(args);
        assertEqualsFile("test/resources/input3.txt"
        );
    }
}

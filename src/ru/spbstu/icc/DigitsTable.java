package ru.spbstu.icc;

import java.util.HashMap;
import java.util.Map;

public class DigitsTable {

    /**
     * Таблица перевода кол-ва повторов в буквенную форму, чтобы в 1 символ поместить как можно больше повторов,
     * до этого максимальное число повторов было 9 (10), теперь 61(62) (буква - 'z').
     * Таким образом улучшится коэффициент сжатия.
     */
    public static final Map<Integer, Character> digitsTable = new HashMap<>();

    static {
        int a = 0;
        while (a < 10) {
            digitsTable.put(a, (char) ('0' + a));
            a++;
        }
        while (a < 36) {
            digitsTable.put(a, (char) ('A' + a - 10));
            a++;
        }
        while (a < 62) {
            digitsTable.put(a, (char) ('a' + a - 36));
            a++;
        }
    }

    public static int getValue(Map<Integer, Character> digitsTable, char symbol) {
        for (Map.Entry<Integer, Character> entry : digitsTable.entrySet()) {
            if (entry.getValue().equals(symbol)) return entry.getKey();
        }
        return 0;
    }
}
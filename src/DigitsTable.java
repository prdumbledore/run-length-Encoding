import java.util.HashMap;
import java.util.Map;

public class DigitsTable {

    /**
     * @return таблица перевода кол-ва повторов в буквенную форму, чтобы в 1 символ поместить как можно больше повторов,
     * до этого максимальное число повторов было 9 (10), теперь 61(62) (буква - 'z').
     * Таким образом улучшится коэффициент сжатия.
     */
    public Map<Integer, Character> setDigitsTable() {
        Map<Integer, Character> digitsTable = new HashMap<>();
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
        return digitsTable;
    }

    public static int getValue(Map<Integer, Character> digitsTable, char symbol) {
        for (Map.Entry<Integer, Character> entry: digitsTable.entrySet()) {
            if (entry.getValue().equals(symbol)) return entry.getKey();
        }
        return 0;
    }

    public static void main(String[] args) {
        Map<Integer, Character> a = new DigitsTable().setDigitsTable();
        System.out.println(a.get(28));
        System.out.println(a.get(1));
    }
}

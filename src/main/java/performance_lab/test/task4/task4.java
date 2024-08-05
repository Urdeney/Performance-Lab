package performance_lab.test.task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Дан массив целых чисел nums.
 * Напишите программу, выводящую минимальное количество ходов, требуемых для
 * приведения всех элементов к одному числу.
 * За один ход можно уменьшить или увеличить число массива на 1.
 * Элементы массива читаются из файла, переданного в качестве аргумента командной строки!
 */
public class task4 {
    public static void main(String[] args) {
        System.out.println(EqualizedPath_Mean(ArgsFileReading(args)));
    }

    /**
     * Функция для выгрузки чисел из файла в массив значений
     * из аргумента командной строки
     * 
     * @param args - имя файла из командной строки
     * @return массив прочитанных из файла целых чисел
     */
    static List<Integer> ArgsFileReading(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Программа должна принимать только имя одного файла");
        }

        File filename = new File(args[0]);
        List<Integer> lst = new ArrayList<>();

        try (Scanner sc = new Scanner(filename)) {
            while (sc.hasNextInt()) {
                lst.add(sc.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }

    /**
     * Функция для нахождения количества шагов, необходимых для приведения всех
     * элементов к одному числу.
     * ДАННАЯ РЕАЛИЗАЦИЯ использует среднее арифметическое значение.
     * 
     * @param numbers - список целых чисел
     * @return минимальное кол-во шагов, за которое можно привести все числа в списке к одному значению
     */
    static int EqualizedPath_Mean(List<Integer> numbers) {

        if (numbers.isEmpty()) {
            return 0;
        }

        if (numbers.size() == 1) {
            return numbers.get(0);
        }

        if (Collections.min(numbers).equals(Collections.max(numbers))) {
            return numbers.get(0);
        }

        double mean = numbers.stream().reduce(0, (a, b) -> a + b) / (double) numbers.size();
        List<Integer> paths = new ArrayList<>();
        List<Integer> paths_values = new ArrayList<>();

        if (Math.floor(mean) != mean) {
            paths.add((int) mean);
            paths.add((int) mean + 1);
        } else {
            paths.add((int) mean);
        }

        for (var path : paths) {
            int path_value = 0;
            for (var num : numbers) {
                path_value += Math.abs(num - path);
            }
            paths_values.add(path_value);
        }

        return Collections.min(paths_values);
    }
}

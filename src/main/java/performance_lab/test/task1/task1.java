package performance_lab.test.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Круговой массив - массив из элементов, в котором по достижению конца массива
 * следующим элементом будет снова первый. Массив задается числом n, то есть
 * представляет собой числа от 1 до n.
 * Напишите программу, которая выводит путь, по которому, двигаясь интервалом
 * длины m
 * по заданному массиву, концом будет являться первый элемент.
 */
public class task1 {

    public static void main(String[] args) {

        List<Integer> path = CircleArrayPath(ParseTernimalArgs(args));
        System.out.println(Concat(path));
    }

    /**
     * Функция для парсинга аргументов из командной строки
     * 
     * @param args - параметры n и m командной строки
     * @return список целочисленных параметров n и m
     */
    static List<Integer> ParseTernimalArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Программа должна принимать параметры n и m");
        }
        int n = 0;
        int m = 0;
        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        if (n <= 0 && m <= 0) {
            throw new IllegalArgumentException("Параметры n и m должны быть целыми числами");
        }

        List<Integer> arg_lst = new ArrayList<>(Arrays.asList(n, m));
        return arg_lst;
    }

    /**
     * Функция для построения пути в виде целочисленного списка
     * 
     * @param arg_lst - список целочисленных параметров n и m
     * @return список, содержащий путь по круговому массиву
     */
    static List<Integer> CircleArrayPath(List<Integer> arg_lst) {

        if (arg_lst.get(0) == 1) {
            return new ArrayList<Integer>(Arrays.asList(1));
        }

        if (arg_lst.get(1) % arg_lst.get(0) == 1) {
            return new ArrayList<>(Arrays.asList(1));
        }

        int[] circle_arr = IntStream.range(1, arg_lst.get(0) + 1).toArray();
        int n = arg_lst.get(0);
        int m = arg_lst.get(1);
        boolean flag = false;
        int counter = 0;
        List<Integer> path = new ArrayList<>();
        path.add(circle_arr[0]);

        while (flag != true) {
            counter = (counter + m - 1) % n;
            path.add(circle_arr[counter]);
            if (counter == 0) {
                flag = true;
            }
        }
        path.remove(path.size()-1);

        return path;
    }

    /**
     * Строковое представление списка целых чисел
     * 
     * @return строка заданного вида
     */
    static String Concat(List<Integer> path) {
        StringBuilder res_str = new StringBuilder();
        for (Integer i : path) {
            res_str.append(i);
        }
        return res_str.toString();
    }
}

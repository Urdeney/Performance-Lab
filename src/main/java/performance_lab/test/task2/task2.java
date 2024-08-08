package performance_lab.test.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Напишите программу, которая рассчитывает положение точки относительно окружности.
 * Координаты центра окружности и его радиус считываются из файла 1.
 * Координаты точек считываются из файла 2.
 */
public class task2 {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Программа должна принимать только имя двух файлов");
        }

        Cirle cirle = СircleFileParsing(args[0]);
        List<Point> points_lst = PointsFileParsing(args[1]);
        for (Point point : points_lst) {
            System.out.println(cirle.PointPosion(point));
        }
    }

    /*
     * Первый параметр - координаты точки по оси OX
     * Второй параметр - координаты точки по оси OY
     */
    public static class Point {
        double x; double y;
        public Point(double x, double y) {
            this.x=x;
            this.y=y;
        }
    }

    /*
     * Первый параметр - точка с координатами x и y
     * Второй параметр - радиус окружности
     */
    public static class Cirle {
        Point p; 
        int radius;

        public Cirle (Point p,int radius) {
            this.p=p;
            this.radius = radius;
        }
        /*
         * 0 - точка лежит на окружности
         * 1 - точка внутри
         * 2 - точка снаружи
         */
        public int PointPosion(Point point) {

            double distance = Math.pow(this.p.x - point.x, 2) + Math.pow(this.p.y - point.y, 2);
            int point_pos = Double.compare(Math.pow(this.radius, 2), distance);
            if (point_pos < 0) {
                return 2;
            } else if (point_pos > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Функция для парсинга параметров окружности из файла
     * 
     * @param cirle_filename - имя файла окружности
     */
    static Cirle СircleFileParsing(String cirle_filename) {

        Point point;
        Cirle circle;
        File circle_file = new File(cirle_filename);

        try (Scanner sc = new Scanner(circle_file)) {
            point = new Point(sc.nextDouble(), sc.nextDouble());
            circle = new Cirle(point, sc.nextInt());
            return circle;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Функция для парсинга параметров точек из файла
     * 
     * @param points_filename - имя файла точек
     */
    static List<Point> PointsFileParsing(String points_filename) {
        List<Point> points_list = new ArrayList<>();
        File points_file = new File(points_filename);

        try (Scanner sc = new Scanner(points_file)) {
            while (sc.hasNextLine()) {
                String[] points_coords = sc.nextLine().split(" ", 2);
                points_list.add(new Point(Double.parseDouble(points_coords[0]),
                        Double.parseDouble(points_coords[1])));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return points_list;
    }
}

/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Функции имапликаций с t-норм
 * Вариант 3
 * 4.11.24
 */

package by.kovalski.fuzzyinference.util;

public class Util {

    public static double lukasiewiczTNorm(double x, double y) {
        return roundToTenth(Math.max(x + y - 1, 0));
    }

    public static double minTNorm(double x, double y) {
        return roundToTenth(Math.min(x, y));
    }

    public static double lukasiewiczImplication(double x, double y) {
        return roundToTenth(Math.min(1 - x + y, 1));
    }

    public static double godelImplication(double x, double y) {
        return roundToTenth(x <= y ? 1 : y);
    }

    private static double roundToTenth(double value) {
        return Math.round(value * 10) / 10.0;
    }
}

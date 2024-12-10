/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает интерфейс сервиса нечеткого логического вывода
 * Вариант 3
 * 4.11.24
 */

package by.kovalski.fuzzyinference.service;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;

import java.util.List;
import java.util.function.BiFunction;

public interface DirectFuzzyInferenceService {
    FuzzyPredicate makeInference(FuzzyPredicate set, BinaryFuzzyPredicate implication, BiFunction<Double, Double, Double> tNorm, String resName);

    List<FuzzyPredicate> makeInferenceFromKB(List<FuzzyPredicate> sets, List<BinaryFuzzyPredicate> implications, BiFunction<Double, Double, Double> tNorm);
}

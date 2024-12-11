/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает класс нечеткого бинарного предиката
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference.entity;

import java.util.*;
import java.util.function.BiFunction;

public class BinaryFuzzyPredicate {

    final FuzzyPredicate set1;
    final FuzzyPredicate set2;
    Map<String, List<Pair<String, Double>>> relationMatrix;

    public BinaryFuzzyPredicate(FuzzyPredicate set1, FuzzyPredicate set2, Map<String, List<Pair<String, Double>>> relationMatrix) {
        this.set1 = set1;
        this.set2 = set2;
        this.relationMatrix = relationMatrix;
    }

    public FuzzyPredicate getSet1() {
        return set1;
    }

    public FuzzyPredicate getSet2() {
        return set2;
    }

    public void setRelationMatrix(Map<String, List<Pair<String, Double>>> relationMatrix) {
        this.relationMatrix = relationMatrix;
    }

    public Map<String, List<Pair<String, Double>>> getRelationMatrix() {
        return relationMatrix;
    }

    public static BinaryFuzzyPredicate compute(BiFunction<Double, Double, Double> implFunction,
                                               FuzzyPredicate set1, FuzzyPredicate set2) {

        BinaryFuzzyPredicate result = new BinaryFuzzyPredicate(set1, set2, new HashMap<>());
        for (Map.Entry<String, Double> set1Element : set1.elements.entrySet()) {
            List<Pair<String, Double>> matrixRow = new ArrayList<>();

            for (Map.Entry<String, Double> set2Element : set2.elements.entrySet()) {
                matrixRow.add(new Pair<>(set2Element.getKey(), implFunction.apply(set1Element.getValue(),
                        set2Element.getValue())));
            }
            result.relationMatrix.put(set1Element.getKey(), matrixRow);
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        relationMatrix.forEach((rowKey, rowValues) -> {
            stringBuilder.append(rowKey).append(": ");
            rowValues.forEach(implValue -> stringBuilder.append(implValue.getValue()).append(" "));
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }
}

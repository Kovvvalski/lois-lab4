/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает класс нечеткой посылки
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference.entity;

import java.util.*;
import java.util.stream.Collectors;

public class FuzzyPremise {
    private final BinaryFuzzyPredicate binaryFuzzyPredicate;
    private final FuzzyPredicate conclusion;
    private Map<List<Pair<String, Double>>, Double> equationsSystem;
    private Set<Map<String, Pair<Double, Double>>> solutions;

    public FuzzyPremise(BinaryFuzzyPredicate binaryFuzzyPredicate, FuzzyPredicate conclusion) {
        this.binaryFuzzyPredicate = binaryFuzzyPredicate;
        this.conclusion = conclusion;
        solutions = Set.of();

        // если область определения вывода не соответствует области определения правила
        if (!binaryFuzzyPredicate.getRelationMatrix().values().stream().
                allMatch(row -> row.stream().map(Pair::getKey).collect(Collectors.toSet()).
                        equals(conclusion.getElements().keySet()))) {
            throw new IllegalArgumentException("Elements in implication matrix don't correspond to a input fuzzy set");
        }

    }

    public BinaryFuzzyPredicate getFuzzyImplication() {
        return binaryFuzzyPredicate;
    }

    public FuzzyPredicate getConclusion() {
        return conclusion;
    }

    public Set<Map<String, Pair<Double, Double>>> getSolutions() {
        return solutions;
    }

    public Map<List<Pair<String, Double>>, Double> getEquationsSystem() {
        return equationsSystem;
    }

    // метод расчета посылки
    public void calculate() {
        var matrix = binaryFuzzyPredicate.getRelationMatrix();
        var transposedMatrix = transposeMatrix(matrix);
        Map<List<Pair<String, Double>>, Double> equations = new HashMap<>();

        // составляем уравнения
        for (var entry : transposedMatrix.entrySet()) {
            equations.put(entry.getValue(), conclusion.getElements().get(entry.getKey()));
        }
        this.equationsSystem = equations;

        // инициализируем единственное решение как [0, 1]
        Set<Map<String, Pair<Double, Double>>> equationsSolutions = new HashSet<>(Set.of(
                matrix.keySet().stream().collect(Collectors.toMap(var -> var, var -> new Pair<>(0D, 1D)))));

        for (var equation : equations.entrySet()) {

            // находим решения для каждого уравнения из системы
            var equationSolution = calculateEquationSolutions(equation.getKey(), equation.getValue());

            // если уравнение не имеет решений - вся система не имеет решений
            if (equationSolution.isEmpty()) {
                return;
            }

            // пересекаем решения
            equationsSolutions = intersectSolutions(equationsSolutions, equationSolution);

            // если пересечений не найдено
            if (equationsSolutions.isEmpty()) {
                return;
            }
        }
        solutions = equationsSolutions;
    }

    // метод расчета решения уравнения
    private Set<Map<String, Pair<Double, Double>>> calculateEquationSolutions(List<Pair<String, Double>> leftPart, Double rightPart) {
        Set<Map<String, Pair<Double, Double>>> ranges = new HashSet<>();
        for (Pair<String, Double> kNormOperand : leftPart) {
            Map<String, Pair<Double, Double>> currentRange = new HashMap<>();

            // невозможно приравнять данный операнд k-нормы к правой части уравнения
            if (kNormOperand.getValue() < rightPart) {
                continue;
            }

            // вычисляем значение переменной
            double varValue = rightPart / kNormOperand.getValue();
            currentRange.put(kNormOperand.getKey(), new Pair<>(varValue, varValue));

            // вычисляем значения промежутков для всех оставшихся переменных
            for (var remainingEl : leftPart.stream()
                    .filter(o -> !o.getKey().equals(kNormOperand.getKey()))
                    .toList()) {

                // вычисляем значение правой границы переменной
                double rightBound = rightPart / remainingEl.getValue();
                currentRange.put(remainingEl.getKey(), new Pair<>(0D, rightBound <= 1 ? rightBound : 1));
            }
            ranges.add(currentRange);
        }
        return ranges;
    }

    // метод для нахождения пересечений решений
    private Set<Map<String, Pair<Double, Double>>> intersectSolutions(Set<Map<String, Pair<Double, Double>>> existingSols,
                                                                      Set<Map<String, Pair<Double, Double>>> newSols) {

        Set<Map<String, Pair<Double, Double>>> intersected = new HashSet<>();

        // каждое новое решение пересекаем со всеми существующими
        for (var newSol : newSols) {
            for (var existingSol : existingSols) {
                boolean isIntersected = true;
                Map<String, Pair<Double, Double>> intersection = new HashMap<>();
                for (String var : newSol.keySet()) {
                    Pair<Double, Double> existingRange = existingSol.get(var);
                    Pair<Double, Double> newRange = newSol.get(var);

                    // проверяем на наличие пересечений
                    if (!(existingRange.getKey() <= newRange.getValue() && newRange.getKey() <= existingRange.getValue())) {
                        isIntersected = false;
                        break;
                    }

                    // составляем новый промежуток из наиболее "узких" границ
                    intersection.put(var, new Pair<>(Math.max(existingRange.getKey(), newRange.getKey()),
                            Math.min(existingRange.getValue(), newRange.getValue())));
                }

                // если пересечение существует
                if (isIntersected) {
                    intersected.add(intersection);
                }
            }
        }

        return intersected;
    }

    // метод транспонирования матрица нечеткого бинарного предиката
    private Map<String, List<Pair<String, Double>>> transposeMatrix(Map<String, List<Pair<String, Double>>> matrix) {
        Map<String, List<Pair<String, Double>>> transposedMatrix = new HashMap<>();
        List<String> conclusionElements = matrix.values().stream().findAny().get().
                stream().map(Pair::getKey).toList();

        for (int i = 0; i < conclusionElements.size(); i++) {
            String conclusionElement = conclusionElements.get(i);
            transposedMatrix.put(conclusionElement, new ArrayList<>());
            for (var entry : matrix.entrySet()) {
                transposedMatrix.get(conclusionElement).add(new Pair<>(entry.getKey(), entry.getValue().get(i).getValue()));
            }
        }
        return transposedMatrix;
    }
}

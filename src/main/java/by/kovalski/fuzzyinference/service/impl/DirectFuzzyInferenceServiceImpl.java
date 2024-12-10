/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает класс сервиса нечеткого логического вывода
 * Вариант 3
 * 4.11.24
 */

package by.kovalski.fuzzyinference.service.impl;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;
import by.kovalski.fuzzyinference.entity.Pair;
import by.kovalski.fuzzyinference.service.DirectFuzzyInferenceService;

import java.util.*;
import java.util.function.BiFunction;

public class DirectFuzzyInferenceServiceImpl implements DirectFuzzyInferenceService {
    @Override
    public FuzzyPredicate makeInference(FuzzyPredicate set, BinaryFuzzyPredicate implication, BiFunction<Double, Double, Double> tNorm, String resName) {
        Map<String, Double> maxValues = new HashMap<>();
        for (Map.Entry<String, Double> setElement : set.getElements().entrySet()) {
            for (Pair<String, Double> implElement : implication.getRelationMatrix().get(setElement.getKey())) {
                Double newVarValue = tNorm.apply(setElement.getValue(), implElement.getValue());
                String varName = implElement.getKey();
                maxValues.put(varName,
                        maxValues.containsKey(varName) ?
                                Math.max(maxValues.get(varName), newVarValue) :
                                newVarValue
                );
            }
        }
        return new FuzzyPredicate(resName, maxValues);
    }

    @Override
    public List<FuzzyPredicate> makeInferenceFromKB(List<FuzzyPredicate> sets, List<BinaryFuzzyPredicate> implications,
                                                    BiFunction<Double, Double, Double> tNorm) {
        Queue<FuzzyPredicate> setsQueue = new LinkedList<>(sets);
        List<FuzzyPredicate> outputSets = new ArrayList<>(sets);
        int setCounter = 1;

        while (!setsQueue.isEmpty()) {
            FuzzyPredicate currentSet = setsQueue.poll();

            for (BinaryFuzzyPredicate implication : implications) {
                if (implication.getSet1().getElements().keySet().equals(currentSet.getElements().keySet())) {
                    FuzzyPredicate inferredSet = makeInference(currentSet, implication, tNorm, String.valueOf(setCounter));

                    Optional<FuzzyPredicate> duplicateSet = outputSets.stream()
                            .filter(s -> s.getElements().equals(inferredSet.getElements()))
                            .findFirst();

                    if (duplicateSet.isPresent()) {
                        logInference(currentSet, implication, inferredSet, duplicateSet.get());
                    } else {
                        logInference(currentSet, implication, inferredSet, null);
                        setsQueue.add(inferredSet);
                        outputSets.add(inferredSet);
                    }
                    setCounter++;
                }
            }
        }

        return outputSets;
    }

    private void logInference(FuzzyPredicate currentSet, BinaryFuzzyPredicate implication, FuzzyPredicate inferredSet, FuzzyPredicate duplicateSet) {
        String implicationStr = String.format("%s -> %s", implication.getSet1().getName(), implication.getSet2().getName());
        String resultStr = String.format("%s /~\\ (%s) = %s", currentSet.getName(), implicationStr, inferredSet);

        if (duplicateSet != null) {
            System.out.println(resultStr + " = " + duplicateSet.getName());
        } else {
            System.out.println(resultStr);
        }
    }
}

/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл класс приложения
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPremise;
import by.kovalski.fuzzyinference.entity.Pair;
import by.kovalski.fuzzyinference.parser.KnowledgeBaseParser;
import by.kovalski.fuzzyinference.parser.impl.KBParserImpl;
import by.kovalski.fuzzyinference.validator.impl.KnowledgeBaseValidatorImpl;

import java.util.*;
import java.util.stream.Collectors;


public class Main {

    static String FILE_PATH = "src/main/resources/test3.kb";

    static KnowledgeBaseParser knowledgeBaseParser = new KBParserImpl(FILE_PATH, new KnowledgeBaseValidatorImpl());

    public static void main(String[] args) {
        try {
            FuzzyPredicate conclusion = knowledgeBaseParser.parseFuzzyPredicate();
            BinaryFuzzyPredicate rule = knowledgeBaseParser.parseBinaryFuzzyPredicate(conclusion);

            FuzzyPremise fuzzyPremise = new FuzzyPremise(rule, conclusion);
            fuzzyPremise.calculate();

            printEquationsSystem(fuzzyPremise);
            printSolutions(fuzzyPremise);
        }
        catch (NoSuchElementException e) {
            System.out.println("Error while parsing");
        }
    }

    static void printEquationsSystem(FuzzyPremise fuzzyPremise) {
        Map<List<Pair<String, Double>>, Double> equationsSystem = fuzzyPremise.getEquationsSystem();

        System.out.println("Система уравнений:");
        for (Map.Entry<List<Pair<String, Double>>, Double> equation : equationsSystem.entrySet()) {
            List<Pair<String, Double>> pairs = equation.getKey();
            Double result = equation.getValue();

            String equationStr = "max(" + pairs.stream()
                    .map(pair -> pair.getKey() + " * " + pair.getValue())
                    .collect(Collectors.joining(", "))
                    + ") = " + result;

            System.out.println(equationStr);
        }
    }

    static void printSolutions(FuzzyPremise fuzzyPremise) {
        Set<Map<String, Pair<Double, Double>>> solutions = fuzzyPremise.getSolutions();

        System.out.println("Решение системы:");

        if(solutions.isEmpty()) {
            System.out.println('∅');
        }

        String solutionsStr = solutions.stream()
                .map(solution -> "(" +
                        solution.entrySet().stream()
                                .map(entry -> {
                                    String variable = entry.getKey();
                                    double left = entry.getValue().getKey();
                                    double right = entry.getValue().getValue();

                                    if (left == right) {
                                        return variable + " = " + left;
                                    } else {
                                        return variable + " ∈ [" + left + ", " + right + "]";
                                    }
                                })
                                .collect(Collectors.joining(" ∧ ")) +
                        ")")
                .collect(Collectors.joining(" ∪ "));

        System.out.println(solutionsStr);
    }
}
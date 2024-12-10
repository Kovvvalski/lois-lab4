/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает класс вадидатора входных данных
 * Вариант 3
 * 4.11.24
 */


package by.kovalski.fuzzyinference.validator.impl;

import by.kovalski.fuzzyinference.validator.KnowledgeBaseValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeBaseValidatorImpl implements KnowledgeBaseValidator {

    private final String FUZZY_PREDICATE_NAMES_LINE_REGEX = "^[a-zA-Z]([a-zA-Z]*|[0-9]*)( [a-zA-Z]([a-zA-Z]*|[0-9]*))*$";
    private final String FUZZY_PREDICATE_VALUES_LINE_REGEX = "^(((1\\.0)|(0\\.[0-9]*))(( 1\\.0)|( 0\\.[0-9]*))*)$";

    @Override
    public boolean validateKb() {
        return true;
    }

    @Override
    public boolean validateFuzzyPredicate(String fuzzyPredicateToken) {
        var lines = fuzzyPredicateToken.split("\n");
        return lines[0].matches(FUZZY_PREDICATE_NAMES_LINE_REGEX)
                && Arrays.stream(lines[0].split(" "))
                                         .collect(Collectors.toSet()).size() == lines[0].split(" ").length
                && lines[1].matches(FUZZY_PREDICATE_VALUES_LINE_REGEX)
                && lines[1].split(" ").length == lines[0].split(" ").length;
    }

    @Override
    public boolean validateBinaryFuzzyPredicate(String binaryFuzzyPredicateToken, Integer predicateElementsCount) {
        var lines = binaryFuzzyPredicateToken.split("\n");
        int counter = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (i == 0) {
                counter += line.matches(FUZZY_PREDICATE_NAMES_LINE_REGEX) ? 1 : 0;
            } else {
                counter += line.matches(FUZZY_PREDICATE_VALUES_LINE_REGEX) ? 1 : 0;
            }
        }
        return counter == predicateElementsCount + 1;
    }
}

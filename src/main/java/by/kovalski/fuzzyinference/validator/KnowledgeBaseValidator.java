/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает интерфейс вадидатора входных данных
 * Вариант 3
 * 4.11.24
 */


package by.kovalski.fuzzyinference.validator;

import java.util.List;

public interface KnowledgeBaseValidator {

    boolean validateKb();

    boolean validateFuzzyPredicate(String fuzzyPredicate);

    boolean validateBinaryFuzzyPredicate(String binaryFuzzyPredicateToken, Integer predicateElementsCount);
}

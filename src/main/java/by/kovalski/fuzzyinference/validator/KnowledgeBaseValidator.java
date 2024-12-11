/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл интерфейс валидатора базы знаний
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference.validator;

public interface KnowledgeBaseValidator {

    boolean validateKb();

    boolean validateFuzzyPredicate(String fuzzyPredicate);

    boolean validateBinaryFuzzyPredicate(String binaryFuzzyPredicateToken, Integer predicateElementsCount);
}

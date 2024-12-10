/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает интерфейс вадидатора входных данных
 * Вариант 3
 * 4.11.24
 */


package by.kovalski.fuzzyinference.validator;

public interface KnowledgeBaseValidator {

    boolean validateKb();

    boolean validateFuzzySet(String fuzzySet);

    boolean validateFuzzyImplication(String fuzzyImplication);
}

/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает интерфейс парсера БЗ
 * Вариант 3
 * 4.11.24
 */

package by.kovalski.fuzzyinference.parser;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;

import java.util.List;

public interface KnowledgeBaseParser {

    List<FuzzyPredicate> parseFuzzySet();

    List<BinaryFuzzyPredicate> parseFuzzyImplication(List<FuzzyPredicate> parsedSets);
}

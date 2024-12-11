/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл интерфейс парсера базы знаний
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference.parser;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;

import java.util.List;

public interface KnowledgeBaseParser {

    FuzzyPredicate parseFuzzyPredicate();

    BinaryFuzzyPredicate parseBinaryFuzzyPredicate(FuzzyPredicate fuzzyPredicate);
}

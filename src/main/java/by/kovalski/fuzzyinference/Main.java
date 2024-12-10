/**
 * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает класс запуска программы
 * Вариант 3
 * 4.11.24
 */

package by.kovalski.fuzzyinference;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;
import by.kovalski.fuzzyinference.parser.KnowledgeBaseParser;
import by.kovalski.fuzzyinference.parser.impl.KnowledgeBaseParserImpl;
import by.kovalski.fuzzyinference.service.DirectFuzzyInferenceService;
import by.kovalski.fuzzyinference.service.impl.DirectFuzzyInferenceServiceImpl;
import by.kovalski.fuzzyinference.util.Util;
import by.kovalski.fuzzyinference.validator.impl.KnowledgeBaseValidatorImpl;

import java.util.*;

public class Main {

    static DirectFuzzyInferenceService inferenceService = new DirectFuzzyInferenceServiceImpl();

    static String FILE_PATH = "src/main/resources/test3.kb";

    static KnowledgeBaseParser knowledgeBaseParser = new KnowledgeBaseParserImpl(FILE_PATH, new KnowledgeBaseValidatorImpl());

    public static void main(String[] args) {
        List<FuzzyPredicate> sets = knowledgeBaseParser.parseFuzzySet();
        List<BinaryFuzzyPredicate> binaryFuzzyPredicates = knowledgeBaseParser.parseFuzzyImplication(sets);
        for (FuzzyPredicate fuzzyPredicate : sets) {
            System.out.println(fuzzyPredicate);
        }
        System.out.println();
        for (BinaryFuzzyPredicate binaryFuzzyPredicate : binaryFuzzyPredicates) {
            System.out.println(binaryFuzzyPredicate);
        }
        System.out.println("-------------------------------------------");
        inferenceService.makeInferenceFromKB(sets, binaryFuzzyPredicates, Util::lukasiewiczTNorm);
    }
}
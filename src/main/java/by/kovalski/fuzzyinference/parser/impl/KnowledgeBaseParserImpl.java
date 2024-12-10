///**
// * Лабораторная работа №3 по дисциплине Логические основы интеллектуальных систем
// * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
// * Файл описывает класс парсера БЗ
// * Вариант 3
// * 4.11.24
// */
//
//package by.kovalski.fuzzyinference.parser.impl;
//
//import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
//import by.kovalski.fuzzyinference.entity.FuzzyPredicate;
//import by.kovalski.fuzzyinference.parser.KnowledgeBaseParser;
//import by.kovalski.fuzzyinference.util.Util;
//import by.kovalski.fuzzyinference.validator.KnowledgeBaseValidator;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class KnowledgeBaseParserImpl implements KnowledgeBaseParser {
//
//    private final String filePath;
//    private final KnowledgeBaseValidator kbValidator;
//
//    public KnowledgeBaseParserImpl(String filePath, KnowledgeBaseValidator kbValidator) {
//        this.filePath = filePath;
//        this.kbValidator = kbValidator;
//    }
//
//    @Override
//    public List<FuzzyPredicate> parseFuzzySet() {
//        List<FuzzyPredicate> resultList = new ArrayList<>();
//        try (var in = this.fileOpen()) {
//            String fuzzySetStr;
//            StringBuilder sb = new StringBuilder();
//            while ((fuzzySetStr = in.readLine()) != null) {
//                Map<String, Double> fuzzySetElements = new HashMap<>();
//                if (kbValidator.validateFuzzyPredicate(fuzzySetStr)) {
//                    boolean readActive = false;
//                    for (char letter : fuzzySetStr.toCharArray()) {
//                        switch (letter) {
//                            case '<':
//                                readActive = true;
//                                break;
//                            case '>':
//                                String[] strElements = sb.toString()
//                                        .replaceAll(" ", "")
//                                        .split(",");
//                                fuzzySetElements.put(strElements[0], Double.valueOf(strElements[1]));
//                                readActive = false;
//                                sb.setLength(0);
//                                break;
//                            default:
//                                if (readActive) {
//                                    sb.append(letter);
//                                }
//                                break;
//                        }
//                    }
//                    resultList.add(new FuzzyPredicate(fuzzySetStr.substring(0, fuzzySetStr.indexOf(" ")), fuzzySetElements));
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return resultList;
//    }
//
//    @Override
//    public List<BinaryFuzzyPredicate> parseFuzzyImplication(List<FuzzyPredicate> parsedSets) {
//        List<BinaryFuzzyPredicate> resultList = new ArrayList<>();
//        try (var in = this.fileOpen()) {
//            String fuzzyImplicationStr;
//            while ((fuzzyImplicationStr = in.readLine()) != null) {
//                if (this.kbValidator.validateBinaryFuzzyPredicate(fuzzyImplicationStr)) {
//                    String[] implicants = fuzzyImplicationStr.split("~>");
//                    FuzzyPredicate set1 = null;
//                    FuzzyPredicate set2 = null;
//                    for (FuzzyPredicate set : parsedSets) {
//                        if (set.getName().equals(implicants[0].trim()
//                                .substring(0, implicants[0].indexOf('(')))) {
//                            set1 = set;
//                        }
//                        if (set.getName().equals(implicants[1].trim()
//                                .substring(0, implicants[1].indexOf('(')-1))) {
//                            set2 = set;
//                        }
//                    }
//                    if (set1 != null && set2 != null) {
//                        resultList.add(new BinaryFuzzyPredicate(set1, set2, null));
//                    }
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        for (var impl : resultList) {
//            impl.setRelationMatrix(
//                    BinaryFuzzyPredicate.compute(Util::lukasiewiczImplication,
//                            impl.getSet1(),
//                            impl.getSet2()).getRelationMatrix()
//            );
//        }
//        return resultList;
//    }
//
//    private BufferedReader fileOpen() throws FileNotFoundException {
//        return new BufferedReader(new FileReader(this.filePath));
//    }
//}

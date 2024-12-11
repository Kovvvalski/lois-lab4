/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл класс парсера базы знаний
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference.parser.impl;

import by.kovalski.fuzzyinference.entity.BinaryFuzzyPredicate;
import by.kovalski.fuzzyinference.entity.FuzzyPredicate;
import by.kovalski.fuzzyinference.entity.Pair;
import by.kovalski.fuzzyinference.parser.KnowledgeBaseParser;
import by.kovalski.fuzzyinference.validator.KnowledgeBaseValidator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class KBParserImpl implements KnowledgeBaseParser {

    private final String filePath;
    private final KnowledgeBaseValidator kbValidator;

    public KBParserImpl(String filePath, KnowledgeBaseValidator kbValidator) {
        this.filePath = filePath;
        this.kbValidator = kbValidator;
    }

    @Override
    public FuzzyPredicate parseFuzzyPredicate() {
        Map<String, Double> elementSet = new LinkedHashMap<>();
        String fuzzyPredicateToken = getFuzzyPredicateToken(getTokens());
        if (kbValidator.validateFuzzyPredicate(fuzzyPredicateToken)) {
            var lines = fuzzyPredicateToken.split("\n");
            var args = lines[0].split(" ");
            var values = lines[1].split(" ");
            for (int i = 0; i < args.length; i++) {
                elementSet.put(args[i], Double.valueOf(values[i]));
            }
        }
        return new FuzzyPredicate("TEST", elementSet);
    }

    @Override
    public BinaryFuzzyPredicate parseBinaryFuzzyPredicate(FuzzyPredicate fuzzyPredicate) {
        Map<String, List<Pair<String, Double>>> relMatrix = new LinkedHashMap<>();
        Set<String> predicateElementKeys = fuzzyPredicate.getElements().keySet();
        String binaryFuzzyPredicateToken = getBinaryFuzzyPredicateToken(getTokens(), predicateElementKeys.size());
        if (Arrays.stream(binaryFuzzyPredicateToken
                .split("\n")[0].split(" "))
                .anyMatch(str -> predicateElementKeys.stream().anyMatch(str::equals))) {
            var tokens = getTokens();
            tokens.remove(binaryFuzzyPredicateToken);
            binaryFuzzyPredicateToken = getBinaryFuzzyPredicateToken(tokens, predicateElementKeys.size());
        }
        if (kbValidator.validateBinaryFuzzyPredicate(binaryFuzzyPredicateToken, predicateElementKeys.size())) {
            var lines = binaryFuzzyPredicateToken.split("\n");
            var args = lines[0].split(" ");
            for (int i = 0; i < args.length; i++) {
                relMatrix.put(args[i], new ArrayList<>());
                for (int j = 1; j < lines.length; j++) {
                    relMatrix.get(args[i]).add(new Pair<>((String)predicateElementKeys.toArray()[j-1],
                            Double.valueOf(lines[j].split(" ")[i])));
                }
            }
        }
        return new BinaryFuzzyPredicate(null, null, relMatrix);
    }

    private List<String> getTokens() {
        List<String> tokens = new ArrayList<>();
        try (var in = this.fileOpen()) {
            String inputLine;
            StringBuilder tokenBuilder = new StringBuilder();
            while (true) {
                inputLine = in.readLine();
                if (inputLine == null) {
                    tokens.add(tokenBuilder.toString());
                    break;
                }
                if (inputLine.isEmpty()) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                }
                tokenBuilder.append(inputLine).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tokens.stream()
                .map(str -> str.startsWith("\n") ? str.replaceFirst("\n", "") : str).collect(Collectors.toCollection(ArrayList::new));
    }

    private String getFuzzyPredicateToken(List<String> tokens) {
        for (String token : tokens) {
            if (token.split("\n").length == 2) {
                return token;
            }
        }
        return "";
    }

    private String getBinaryFuzzyPredicateToken(List<String> tokens, Integer fuzzyPredicateElementsCount) {
        for (String token : tokens) {
            if (token.split("\n").length == fuzzyPredicateElementsCount + 1) {
                return token;
            }
        }
        return "";
    }

    private BufferedReader fileOpen() throws FileNotFoundException {
        return new BufferedReader(new FileReader(this.filePath));
    }
}

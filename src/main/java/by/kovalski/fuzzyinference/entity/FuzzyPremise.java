package by.kovalski.fuzzyinference.entity;

import java.util.*;
import java.util.stream.Collectors;

public class FuzzyPremise {
    private final BinaryFuzzyPredicate binaryFuzzyPredicate;
    private final FuzzyPredicate conclusion;
    private List<Map<String, Pair<Double, Double>>> ranges;

    public FuzzyPremise(BinaryFuzzyPredicate binaryFuzzyPredicate, FuzzyPredicate conclusion) {
        this.binaryFuzzyPredicate = binaryFuzzyPredicate;
        this.conclusion = conclusion;
        if (!binaryFuzzyPredicate.getRelationMatrix().values().stream().
                allMatch(row -> row.stream().map(Pair::getKey).collect(Collectors.toSet()).
                        equals(conclusion.getElements().keySet()))) {
            throw new IllegalArgumentException("Elements in implication matrix don't correspond to a input fuzzy set");
        }

    }

    public BinaryFuzzyPredicate getFuzzyImplication() {
        return binaryFuzzyPredicate;
    }

    public FuzzyPredicate getConclusion() {
        return conclusion;
    }

    public List<Map<String, Pair<Double, Double>>> getRanges() {
        return ranges;
    }

    public void setRanges(List<Map<String, Pair<Double, Double>>> ranges) {
        this.ranges = ranges;
    }

    void calculate() {
        var transposedImplicationMatrix = transposedMatrix(binaryFuzzyPredicate.getRelationMatrix());
        Map<List<Pair<String, Double>>, Double> equations = new HashMap<>();

        // calculating equations
        for (var entry : transposedImplicationMatrix.entrySet()) {
            equations.put(entry.getValue(), conclusion.getElements().get(entry.getKey()));
        }

        for(var equation : equations.entrySet()) {
            System.out.println(equation.getKey() + " = " + equation.getValue());
        }

        List<Map<String, Pair<Double, Double>>> ranges = new ArrayList<>();


    }

    private Map<String, List<Pair<String, Double>>> transposedMatrix(Map<String, List<Pair<String, Double>>> matrix) {
        Map<String, List<Pair<String, Double>>> transposedMatrix = new HashMap<>();
        List<String> conclusionElements = matrix.values().stream().findAny().get().
                stream().map(Pair::getKey).toList();

        for (int i = 0; i < conclusionElements.size(); i++) {
            String conclusionElement = conclusionElements.get(i);
            transposedMatrix.put(conclusionElement, new ArrayList<>());
            for (var entry : matrix.entrySet()) {
                transposedMatrix.get(conclusionElement).add(new Pair<>(entry.getKey(), entry.getValue().get(i).getValue()));
            }
        }
        return transposedMatrix;
    }

    private void logEquations(Map<List<Pair<String, Double>>, Double> equations) {
        System.out.println("Formed equations:");
        for (var entry : equations.entrySet()) {

        }
    }

    public static void main(String[] args) {
        Map<String, Double> elementsSet1 = new HashMap<>();
        elementsSet1.put("y1", 0.9);
        elementsSet1.put("y2", 0.1);
        elementsSet1.put("y3", 0.2);
        FuzzyPredicate conclusion = new FuzzyPredicate("Set1", elementsSet1);

        Map<String, List<Pair<String, Double>>> implicationMatrix = new HashMap<>();
        implicationMatrix.put("x1", Arrays.asList(new Pair<>("y1", 0.9), new Pair<>("y2", 0.1), new Pair<>("y3", 0.2)));
        implicationMatrix.put("x2", Arrays.asList(new Pair<>("y1", 0.6), new Pair<>("y2", 0.5), new Pair<>("y3", 0.5)));
        implicationMatrix.put("x3", Arrays.asList(new Pair<>("y1", 0.2), new Pair<>("y2", 0.1), new Pair<>("y3", 0.5)));
        BinaryFuzzyPredicate binaryFuzzyPredicate = new BinaryFuzzyPredicate(conclusion, null, implicationMatrix);

        FuzzyPremise fuzzyPremise = new FuzzyPremise(binaryFuzzyPredicate, conclusion);
        fuzzyPremise.calculate();
    }
}

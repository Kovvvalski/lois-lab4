//package by.kovalski.fuzzyinference.service.impl;
//
//import by.kovalski.fuzzyinference.entity.FuzzyImplication;
//import by.kovalski.fuzzyinference.entity.FuzzySet;
//import by.kovalski.fuzzyinference.entity.Pair;
//import by.kovalski.fuzzyinference.entity.ReverseFuzzyInference;
//import by.kovalski.fuzzyinference.service.ReverseFuzzyInferenceService;
//
//import java.util.*;
//
//public class ReverseFuzzyInferenceServiceServiceImpl implements ReverseFuzzyInferenceService {
//
////    @Override
////    public ReverseFuzzyInference makeInference(FuzzyImplication implication, FuzzySet ySet) {
////        ReverseFuzzyInference out = new ReverseFuzzyInference(List.of(), ySet, implication);
////        List<Map<String, Pair<Double, Double>>> solutions = new ArrayList<>();
////        var implMatrix = implication.getImplicationMatrix();
////        for (String yVar : ySet.getElements().keySet()) {
////            Map<String, Pair<Double, Double>> newRanges = new HashMap<>();
////            double ySetValue = ySet.getElements().get(yVar);
////            for (var entry : implMatrix.entrySet()) {
////                double xSetValue = ySetValue - entry.getValue().stream().
////                        filter(pair -> pair.getKey().
////                                equals(yVar)).findFirst().get().getValue();
////
////                if (xSetValue <= 1.0) {
////                    if (!addRange(solutions, entry.getKey(), new Pair<>(0D, xSetValue))) {
////                        return out;
////                    }
////
////                    newRanges.putIfAbsent(entry.getKey(), new Pair<>(xSetValue, xSetValue));
////                }
////            }
////
////            if (ySetValue == 0) {
////                continue;
////            }
////
////            if (!addSolutions(solutions, newRanges)) {
////                return out;
////            }
////
////        }
////        out.setSolutions(solutions);
////        return out;
////    }
////
////    private boolean addRange(List<Map<String, Pair<Double, Double>>> solutions, String xSetVar, Pair<Double, Double> newRange) {
////        for (var solution : solutions) {
////            Pair<Double, Double> range = solution.get(xSetVar);
////
////            if (newRange.getKey() > range.getKey()) {
////                range.setKey(newRange.getKey());
////            }
////            if (newRange.getValue() < range.getValue()) {
////                range.setValue(newRange.getValue());
////            }
////
////            if (range.getKey() > range.getValue()) {
////                return false;
////            }
////        }
////
////        return true;
////    }
////
////    private boolean addSolutions(List<Map<String, Pair<Double, Double>>> solutions, Map<String, Pair<Double, Double>> newRanges) {
////        if (newRanges.isEmpty()) {
////            return false;
////        }
////
////        List<Map<String, Pair<Double, Double>>> newSolutions = new ArrayList<>();
////        for (var e : newRanges.entrySet()) {
////            String varName = e.getKey();
////            Pair<Double, Double> newRange = e.getValue();
////
////            int oldSize = newSolutions.size();
////
////            for (var solution : solutions) {
////                Pair<Double, Double> range = solution.get(varName);
////                if (newRange.getKey() < range.getKey() || newRange.getValue() > range.getValue()) {
////                    continue;
////                }
////
////                newSolutions.add(solution);
////                newSolutions.get(newSolutions.size() - 1).put(varName, newRange);
////            }
////
////            if (newSolutions.size() == oldSize) {
////                return false;
////            }
////
////        }
////
////        List<Map<String, Pair<Double, Double>>> unitedSolutions = uniteSolutions(newSolutions);
////        solutions.clear();
////        solutions.addAll(unitedSolutions);
////        return true;
////    }
////
////    private List<Map<String, Pair<Double, Double>>> uniteSolutions(List<Map<String, Pair<Double, Double>>> sols) {
////        List<Map<String, Pair<Double, Double>>> uniteSols = new ArrayList<>();
////        uniteSols.add(sols.get(0));
////
////
////        for (int i = 1; i < sols.size(); i++) {
////            var solToAdd = sols.get(i);
////            boolean shouldAdd = true;
////
////            for (var sol : uniteSols) {
////                boolean isSubRange = true;
////
////                for (var var : solToAdd.keySet()) {
////                    if (solToAdd.get(var).getKey() < sol.get(var).getKey() || solToAdd.get(var).getValue() > sol.get(var).getValue()) {
////                        isSubRange = false;
////                        break;
////                    }
////                }
////
////                if (isSubRange) {
////                    shouldAdd = false;
////                    break;
////                }
////            }
////
////            if (shouldAdd) {
////                uniteSols.add(solToAdd);
////            }
////        }
////
////        return uniteSols;
////    }
//}
//

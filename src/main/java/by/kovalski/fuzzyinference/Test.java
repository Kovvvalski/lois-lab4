//package by.kovalski.fuzzyinference;
//
//import by.kovalski.fuzzyinference.entity.FuzzyImplication;
//import by.kovalski.fuzzyinference.entity.FuzzySet;
//import by.kovalski.fuzzyinference.entity.Pair;
//import by.kovalski.fuzzyinference.service.ReverseFuzzyInferenceService;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Test {
//    public static void main(String[] args) {
//        Map<String, Double> elementsA = new HashMap<>();
//        elementsA.put("y1", 0.1);
//        elementsA.put("y2", 0.3);
//        elementsA.put("y3", 0.2);
//
//        FuzzySet fuzzySetA = new FuzzySet("A", elementsA);
//
//        Map<String, List<Pair<String, Double>>> implicationMatrix = new HashMap<>();
//
//        // Пример заполнения матрицы для ключей x1, x2, x3
//        implicationMatrix.put("x1", new ArrayList<>());
//        implicationMatrix.get("x1").add(new Pair<>("y1", 0.1));
//        implicationMatrix.get("x1").add(new Pair<>("y2", 0.3));
//        implicationMatrix.get("x1").add(new Pair<>("y3", 0.2));
//
//        implicationMatrix.put("x2", new ArrayList<>());
//        implicationMatrix.get("x2").add(new Pair<>("y1", 0.1));
//        implicationMatrix.get("x2").add(new Pair<>("y2", 0.3));
//        implicationMatrix.get("x2").add(new Pair<>("y3", 0.2));
//
//        implicationMatrix.put("x3", new ArrayList<>());
//        implicationMatrix.get("x3").add(new Pair<>("y1", 0.1));
//        implicationMatrix.get("x3").add(new Pair<>("y2", 0.3));
//        implicationMatrix.get("x3").add(new Pair<>("y3", 0.2));
//
//        // Создание объекта FuzzyImplication
//        FuzzyImplication fuzzyImplication = new FuzzyImplication(null, null, implicationMatrix);
//        ReverseFuzzyInferenceService service = new ReverseFuzzyInferenceServiceServiceImpl();
//        var reverseFuzzyInference = service.makeInference(fuzzyImplication, fuzzySetA);
//        System.out.println(reverseFuzzyInference.getSolutions());
//    }
//}

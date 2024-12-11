/**
 * Лабораторная работа №2 по дисциплине Логические основы интеллектуальных систем
 * Выполнена студентами гр. 221703 БГУИР Быльковым Даниилом Владимировичем, Аврукевичем Константином Сергеевичем
 * Файл описывает класс нечеткого предиката
 * Вариант 2
 * 9.12.24
 */

package by.kovalski.fuzzyinference.entity;

import java.util.Map;
import java.util.Objects;

public class FuzzyPredicate {
    final String name;
    final Map<String, Double> elements;

    public FuzzyPredicate(String name, Map<String, Double> elements) {
        this.name = name;
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuzzyPredicate fuzzyPredicate = (FuzzyPredicate) o;
        return Objects.equals(name, fuzzyPredicate.name) && Objects.equals(elements, fuzzyPredicate.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, elements);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" = {");
        elements.forEach((key, value) -> sb.append("<").append(key).append(", ").append(value).append(">, "));
        if (!elements.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("}");
        return sb.toString();
    }
}

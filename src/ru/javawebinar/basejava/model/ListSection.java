package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items,"items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder();
        for (String value : items) {
            listToString.append(value).append("\n");
        }
        return listToString.toString();
    }
}
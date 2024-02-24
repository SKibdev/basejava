package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> list;

    public ListSection() {
        list = new ArrayList<>();
    }

    public List<String> getAll() {
        return new ArrayList<>(list);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setElement(String s) {
        list.add(s);
    }

    public void removeElement(String s) {
        list.remove(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder();
        for (String value : list) {
            listToString.append(value);
        }
        return listToString.toString();
    }
}

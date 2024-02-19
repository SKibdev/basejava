package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends Section {

    private final String text;
    public TextSection(SectionType sectionType) {
        text = sectionType.getTitle();
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return text;
    }
}

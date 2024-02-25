package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Period {
    LocalDate startDate;
    LocalDate endDate;
    String title;
    String description;

    public Period(String startDate, String endDate, String title, String description) {
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  startDate + " - " + endDate + "\n" +
                title + "\n" + description + "\n";
    }
}

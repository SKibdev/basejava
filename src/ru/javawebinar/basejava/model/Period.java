package ru.javawebinar.basejava.model;

public class Period {
    String startDate = "Начало, ММ/ГГГГ";
    String endDate = "Окончание, ММ/ГГГГ";
    String title = "Заголовок";
    String description = "Описание";

    public Period(String startDate, String endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }
}

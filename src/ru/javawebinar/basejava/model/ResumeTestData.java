package ru.javawebinar.basejava.model;

import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resumeTest = new Resume("uuid1", "Name1");
        showResume(resumeTest);

    }

    public static void showResume(Resume resumeTest) {
        System.out.println(resumeTest.getUuid() + " " + resumeTest.getFullName() + "\n");

        Map<ContactType, String> contacts = resumeTest.getContacts();
        for (ContactType type : ContactType.values()) {
            System.out.println(type + " " + contacts.get(type));
        }
        System.out.println();

        Map<SectionType, Section> sections = resumeTest.getSections();
        for (SectionType type : SectionType.values()) {
            System.out.println(type + " " + sections.get(type));
        }
    }
}

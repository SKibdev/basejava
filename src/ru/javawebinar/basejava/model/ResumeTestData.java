package ru.javawebinar.basejava.model;

import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    private final static Resume RESUME_TEST = new Resume("uuid1", "Name1");
    private final static Map<ContactType, String> CONTACTS = RESUME_TEST.getContacts();
    private final static Map<SectionType, Section> SECTIONS = RESUME_TEST.getSections();

    public static void main(String[] args) {
        CONTACTS.put(TELEPHONE, "+7(921) 855-0482");
        CONTACTS.put(SKYPE, "skype:grigory.kislin");
        CONTACTS.put(EMAIL, "gkislin@yandex.ru");
        CONTACTS.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        CONTACTS.put(GITHUB, "https://github.com/gkislin");
        CONTACTS.put(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        CONTACTS.put(HOMEPAGE, "http://gkislin.ru/");


        SECTIONS.get(OBJECTIVE).setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise " +
                "технологиям");
        SECTIONS.get(PERSONAL).setText("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.");

        SECTIONS.get(ACHIEVEMENT).getList().add("Организация команды и успешная реализация Java проектов для " +
                "сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
                "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный " +
                "Spring Boot + Vaadin проект для комплексных DIY смет");
        SECTIONS.get(ACHIEVEMENT).getList().add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение " +
                "проектов. Более 3500 выпускников.");

        showResume(RESUME_TEST);
    }

    public static void showResume(Resume resumeTest) {
        System.out.println(resumeTest.getUuid() + " " + resumeTest.getFullName() + "\n");

        for (ContactType type : ContactType.values()) {
            System.out.println(type + " " + CONTACTS.get(type));
        }
        System.out.println();

        for (SectionType type : SectionType.values()) {
            System.out.println(type + "\n" + SECTIONS.get(type) + "\n");
        }
    }
}

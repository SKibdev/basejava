package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    private final static Resume RESUME_TEST = new Resume("uuid1", "Name1");
    private final static Map<ContactType, String> CONTACTS = RESUME_TEST.getContacts();
    private final static Map<SectionType, Section> SECTIONS = RESUME_TEST.getSections();
    private final static String OBJECTIVE_1 = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise " +
            "технологиям";
    private final static String PERSONAL_1 = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
            "Пурист кода и архитектуры.";
    private final static String ACHIEVEMENT_1 = "Организация команды и успешная реализация Java проектов для " +
            "сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
            "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный " +
            "Spring Boot + Vaadin проект для комплексных DIY смет.";
    private final static String ACHIEVEMENT_2 = "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
            "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
            "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение " +
            "проектов. Более 3500 выпускников.";
    private final static String ACHIEVEMENT_3 = "Реализация двухфакторной аутентификации для онлайн платформы " +
            "управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.";
    private final static String ACHIEVEMENT_4 = "Налаживание процесса разработки и непрерывной интеграции ERP " +
            "системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением " +
            "на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
            "интеграция CIFS/SMB java сервера.";
    private final static String ACHIEVEMENT_5 = "Реализация c нуля Rich Internet Application приложения на стеке " +
            "технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического " +
            "трейдинга.";
    private final static String ACHIEVEMENT_6 = "Создание JavaEE фреймворка для отказоустойчивого взаимодействия " +
            "слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и " +
            "информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для " +
            "администрирования и мониторинга системы по JMX (Jython/ Django).";
    private final static String ACHIEVEMENT_7 = "Реализация протоколов по приему платежей всех основных " +
            "платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.";
    private final static String QUALIFICATIONS_1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
            "WebLogic, WSO2";
    private final static String QUALIFICATIONS_2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
    private final static String QUALIFICATIONS_3 = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), " +
            "H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB";
    private final static String QUALIFICATIONS_4 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, " +
            "Groovy";
    private final static Organization ORGANIZATION_1 = new Organization("Java Online Projects",
            "http://javaops.ru/");
    private final static Period PERIOD_1_ORGANIZATION_1 = new Period("2013-10-01", "2024-02-25",
            "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
    private final static Organization ORGANIZATION_2 = new Organization("Wrike", "https://www.wrike.com/");
    private final static Period PERIOD_1_ORGANIZATION_2 = new Period("2014-10-01", "2016-01-01",
            "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления" +
            " проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
            "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

    private final static Organization EDUCATIONAL_INSTITUTION_1 = new Organization("Coursera",
            "https://www.coursera.org/course/progfun");
    private final static Period PERIOD_1_EDUCATIONAL_INSTITUTION_1 = new Period("2013-03-01",
            "2013-05-01", "'Functional Programming Principles in Scala' by Martin Odersky", "");
    private final static Organization EDUCATIONAL_INSTITUTION_2 = new Organization("Санкт-Петербургский " +
            "национальный исследовательский университет информационных технологий, механики и оптики",
            "http://www.ifmo.ru/");
    private final static Period PERIOD_1_EDUCATIONAL_INSTITUTION_2 = new Period("1993-09-01",
            "1996-07-01", "Аспирантура (программист С, С++)", "");
    private final static Period PERIOD_2_EDUCATIONAL_INSTITUTION_2 = new Period("1987-09-01",
            "1993-07-01", "Инженер (программист Fortran, C)", "");


    public static void main(String[] args) {
        CONTACTS.put(TELEPHONE, "+7(921) 855-0482");
        CONTACTS.put(SKYPE, "skype:grigory.kislin");
        CONTACTS.put(EMAIL, "gkislin@yandex.ru");
        CONTACTS.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        CONTACTS.put(GITHUB, "https://github.com/gkislin");
        CONTACTS.put(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        CONTACTS.put(HOMEPAGE, "http://gkislin.ru/");

        SECTIONS.get(OBJECTIVE).setText(OBJECTIVE_1);
        SECTIONS.get(PERSONAL).setText(PERSONAL_1);

        List<String> achievementList = List.of(ACHIEVEMENT_1, ACHIEVEMENT_2, ACHIEVEMENT_3, ACHIEVEMENT_4,
                ACHIEVEMENT_5, ACHIEVEMENT_6, ACHIEVEMENT_7);
        SECTIONS.get(ACHIEVEMENT).getList().addAll(achievementList);

        List<String> qualificationsList = List.of(QUALIFICATIONS_1, QUALIFICATIONS_2, QUALIFICATIONS_3,
                QUALIFICATIONS_4);
        SECTIONS.get(QUALIFICATIONS).getList().addAll(qualificationsList);

        List<Organization> organizations = SECTIONS.get(EXPERIENCE).getOrganizations();
        organizations.add(ORGANIZATION_1);
        ORGANIZATION_1.getPeriods().add(PERIOD_1_ORGANIZATION_1);
        organizations.add(ORGANIZATION_2);
        ORGANIZATION_2.getPeriods().add(PERIOD_1_ORGANIZATION_2);

        List<Organization> institutions = SECTIONS.get(EDUCATION).getOrganizations();
        institutions.add(EDUCATIONAL_INSTITUTION_1);
        EDUCATIONAL_INSTITUTION_1.getPeriods().add(PERIOD_1_EDUCATIONAL_INSTITUTION_1);
        institutions.add(EDUCATIONAL_INSTITUTION_2);
        EDUCATIONAL_INSTITUTION_2.getPeriods().add(PERIOD_1_EDUCATIONAL_INSTITUTION_2);
        EDUCATIONAL_INSTITUTION_2.getPeriods().add(PERIOD_2_EDUCATIONAL_INSTITUTION_2);
        showResume(RESUME_TEST);
    }

    public static void showResume(Resume resumeTest) {
        System.out.println(resumeTest.getUuid() + " " + resumeTest.getFullName() + "\n");

        for (ContactType type : ContactType.values()) {
            System.out.println(type + " " + CONTACTS.get(type));
        }
        System.out.println();

        for (SectionType type : SectionType.values()) {
            System.out.println(type + "\n" + SECTIONS.get(type));
        }
    }
}

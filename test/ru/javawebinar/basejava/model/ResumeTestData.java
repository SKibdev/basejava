package ru.javawebinar.basejava.model;

import java.time.Month;
import java.util.List;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;
import static ru.javawebinar.basejava.util.DateUtil.of;

public class ResumeTestData {

    private static Resume resumeTest;
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
    private final static List<String> ACHIEVEMENT_LIST = List.of(ACHIEVEMENT_1, ACHIEVEMENT_2, ACHIEVEMENT_3, ACHIEVEMENT_4,
            ACHIEVEMENT_5, ACHIEVEMENT_6, ACHIEVEMENT_7);

    private final static String QUALIFICATIONS_1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
            "WebLogic, WSO2";
    private final static String QUALIFICATIONS_2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
    private final static String QUALIFICATIONS_3 = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), " +
            "H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB";
    private final static String QUALIFICATIONS_4 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, " +
            "Groovy";
    private final static List<String> QUALIFICATIONS_LIST = List.of(QUALIFICATIONS_1, QUALIFICATIONS_2, QUALIFICATIONS_3,
            QUALIFICATIONS_4);

    private final static Period PERIOD_1_ORGANIZATION_1 = new Period(of(2013, Month.of(10)),
            of(2024, Month.of(2)), "Автор проекта.",
            "Создание, организация и проведение Java онлайн проектов и стажировок.");
    private static final List<Period> PERIODS_ORGANIZATION_1 = List.of(PERIOD_1_ORGANIZATION_1);
    private final static Organization ORGANIZATION_1 = new Organization("Java Online Projects",
            "http://javaops.ru/", PERIODS_ORGANIZATION_1);

    private final static Period PERIOD_1_ORGANIZATION_2 = new Period(of(2014, Month.of(10)),
            of(2016, Month.of(1)), "Старший разработчик (backend)",
            "Проектирование и разработка онлайн платформы управления" +
                    " проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                    "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
    private final static List<Period> PERIODS_ORGANIZATION_2 = List.of(PERIOD_1_ORGANIZATION_2);
    private final static Organization ORGANIZATION_2 = new Organization("Wrike", "https://www.wrike.com/",
            PERIODS_ORGANIZATION_2);
    private final static List<Organization> ORGANIZATIONS = List.of(ORGANIZATION_1, ORGANIZATION_2);

    private final static Period PERIOD_1_EDUCATIONAL_INSTITUTION_1 = new Period(of(2013, Month.of(3)),
            of(2013, Month.of(5)), "'Functional Programming Principles in Scala' by Martin Odersky",
            "");
    private static final List<Period> PERIODS_EDUCATIONAL_INSTITUTION_1 = List.of(PERIOD_1_EDUCATIONAL_INSTITUTION_1);
    private final static Organization EDUCATIONAL_INSTITUTION_1 = new Organization("Coursera",
            "https://www.coursera.org/course/progfun", PERIODS_EDUCATIONAL_INSTITUTION_1);
    private static final Period PERIOD_1_EDUCATIONAL_INSTITUTION_2 = new Period(of(1993, Month.of(9)),
            of(1996, Month.of(7)), "Аспирантура (программист С, С++)", "");
    private static final Period PERIOD_2_EDUCATIONAL_INSTITUTION_2 = new Period(of(1987, Month.of(9)),
            of(1993, Month.of(7)), "Инженер (программист Fortran, C)", "");
    private final static List<Period> PERIODS_EDUCATIONAL_INSTITUTION_2 = List.of(PERIOD_1_EDUCATIONAL_INSTITUTION_2,
            PERIOD_2_EDUCATIONAL_INSTITUTION_2);
    private final static Organization EDUCATIONAL_INSTITUTION_2 = new Organization("Санкт-Петербургский " +
            "национальный исследовательский университет информационных технологий, механики и оптики",
            "http://www.ifmo.ru/", PERIODS_EDUCATIONAL_INSTITUTION_2);

    private final static List<Organization> EDUCATIONAL_INSTITUTIONS = List.of(EDUCATIONAL_INSTITUTION_1,
            EDUCATIONAL_INSTITUTION_2);

    private static void createResume(String uuid1, String name1) {
        resumeTest = new Resume("uuid1", "Name1");
        resumeTest.setContact(PHONE, "+7(921) 855-0482");
        resumeTest.setContact(SKYPE, "skype:grigory.kislin");
        resumeTest.setContact(EMAIL, "gkislin@yandex.ru");
        resumeTest.setContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resumeTest.setContact(GITHUB, "https://github.com/gkislin");
        resumeTest.setContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resumeTest.setContact(HOME_PAGE, "http://gkislin.ru/");

        createSection(OBJECTIVE, OBJECTIVE_1);
        createSection(PERSONAL, PERSONAL_1);
        createSection(ACHIEVEMENT, ACHIEVEMENT_LIST);
        createSection(QUALIFICATIONS, QUALIFICATIONS_LIST);
        createSection(EXPERIENCE, ORGANIZATIONS);
        createSection(EDUCATION, EDUCATIONAL_INSTITUTIONS);
    }

    public static void showResume(Resume resumeTest) {
        System.out.println(resumeTest.getUuid() + " " + resumeTest.getFullName() + "\n");

        for (ContactType type : ContactType.values()) {
            System.out.println(type + " " + resumeTest.getContact(type));
        }
        System.out.println();

        for (SectionType type : SectionType.values()) {
            System.out.println(type + "\n" + resumeTest.getSection(type));
        }
    }

    private static void createSection(SectionType sectionType, Object data) {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> resumeTest.setSection(sectionType, new TextSection((String) data));
            case ACHIEVEMENT, QUALIFICATIONS ->
                    resumeTest.setSection(sectionType, new ListSection((List<String>) data));
            case EXPERIENCE, EDUCATION ->
                    resumeTest.setSection(sectionType, new OrganizationSection((List<Organization>) data));
        }
    }
}
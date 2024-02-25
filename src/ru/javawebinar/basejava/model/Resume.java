package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
        createContacts();
        createSections();
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid most not be null");
        Objects.requireNonNull(uuid, "fullName most not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        createContacts();
        createSections();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    private void createContacts() {
        for (ContactType contactType : ContactType.values()) {
            contacts.put(contactType, "");
        }
    }

    private void createSections() {
        for (SectionType sectionType : SectionType.values()) {
            sections.put(sectionType, createSection(sectionType));
        }
    }

    private static Section createSection(SectionType sectionType) {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection();
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection();
            case EXPERIENCE, EDUCATION -> new OrganizationSection();
        };
    }
}

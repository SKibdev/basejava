package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {
    public abstract static class SectionData implements Serializable {
        public abstract void write(DataOutputStream dos, Section data) throws IOException;
        public abstract void read(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException;
    }

    SectionData textSectionData = new SectionData() {
        @Override
        public void write(DataOutputStream dos, Section data) throws IOException {
            dos.writeUTF(((TextSection) data).getContent());
        }

        @Override
        public void read(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
            resume.addSection(sectionType, new TextSection(dis.readUTF()));
        }
    };

    SectionData listSectionData = new SectionData() {
        @Override
        public void write(DataOutputStream dos, Section data) throws IOException {
            List<String> listSections = ((ListSection) data).getItems();
            dos.writeInt(listSections.size());
            for (String section : listSections) {
                dos.writeUTF(section);
            }
        }

        @Override
        public void read(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
            int itemsSize = dis.readInt();
            List<String> items = new ArrayList<>();
            for (int j = 0; j < itemsSize; j++) {
                String item = dis.readUTF();
                items.add(item);
            }
            resume.addSection(sectionType, new ListSection(items));
        }
    };

    SectionData organizationSectionData = new SectionData() {
        @Override
        public void write(DataOutputStream dos, Section data) throws IOException {
            List<Organization> organizations = ((OrganizationSection) data).getOrganizations();
            dos.writeInt(organizations.size());
            for (Organization organization : organizations) {
                dos.writeUTF(organization.getHomePage().getName());
                dos.writeUTF(organization.getHomePage().getUrl());
                List<Organization.Position> positions = organization.getPositions();
                dos.writeInt(positions.size());
                for (Organization.Position position : positions) {
                    dos.writeUTF(position.getStartDate().toString());
                    dos.writeUTF(position.getEndDate().toString());
                    dos.writeUTF(position.getTitle());
                    dos.writeUTF(position.getDescription());
                }
            }
        }

        @Override
        public void read(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
            int organizationsSize = dis.readInt();
            List<Organization> organizations = new ArrayList<>();
            for (int j = 0; j < organizationsSize; j++) {
                String organizationName = dis.readUTF();
                String organizationUrl = dis.readUTF();
                int positionsSize = dis.readInt();
                List<Organization.Position> positions = new ArrayList<>();
                for (int k = 0; k < positionsSize; k++) {
                    LocalDate startDate = LocalDate.parse(dis.readUTF());
                    LocalDate endDate = LocalDate.parse(dis.readUTF());
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    positions.add(new Organization.Position(startDate, endDate, title, description));
                }
                organizations.add(new Organization(organizationName, organizationUrl, positions));
            }
            resume.addSection(sectionType, new OrganizationSection(organizations));
        }
    };

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL, OBJECTIVE -> textSectionData.write(dos, entry.getValue());
                    case ACHIEVEMENT, QUALIFICATIONS -> listSectionData.write(dos, entry.getValue());
                    case EXPERIENCE, EDUCATION -> organizationSectionData.write(dos, entry.getValue());
                    default -> throw new IllegalStateException("Unexpected value: " + entry.getKey());
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> textSectionData.read(resume, sectionType, dis);
                    case ACHIEVEMENT, QUALIFICATIONS -> listSectionData.read(resume, sectionType, dis);
                    case EXPERIENCE, EDUCATION -> organizationSectionData.read(resume, sectionType, dis);
                }
            }
            return resume;
        }
    }
}
package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();

            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeWithException(sections.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> listSections = ((ListSection) entry.getValue()).getItems();
                        writeWithException(listSections, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                        writeWithException(organizations, dos, (Organization organization) -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(Objects.requireNonNullElse(organization.getHomePage().getUrl(),
                                    "null"));
                            List<Organization.Position> positions = organization.getPositions();
                            writeWithException(positions, dos, (Organization.Position position) -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(Objects.requireNonNullElse(position.getDescription(), "null"));
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () ->
                    resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF())
            );

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = new ArrayList<>();
                        readWithException(dis, () -> {
                            String item = dis.readUTF();
                            items.add(item);
                        });
                        resume.addSection(sectionType, new ListSection(items));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(dis, () -> {
                            String organizationName = dis.readUTF();
                            String organizationUrl = dis.readUTF();
                            if ("null".equals(organizationUrl)) {
                                organizationUrl = null;
                            }
                            List<Organization.Position> positions = new ArrayList<>();
                            readWithException(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if ("null".equals(description)) {
                                    description = null;
                                }
                                positions.add(new Organization.Position(startDate, endDate, title, description));
                            });
                            organizations.add(new Organization(organizationName, organizationUrl, positions));
                        });
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                    }
                }
            });
            return resume;
        }
    }

    @FunctionalInterface
    interface ConsumerReadWithIOException<T> {
        void read() throws IOException;
    }

    private <T> void readWithException(DataInputStream dis, ConsumerReadWithIOException<? super T> action)
            throws IOException {
        Objects.requireNonNull(action);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.read();
        }
    }

    @FunctionalInterface
    interface ConsumerWriteWithIOException<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeWithException(Collection<? extends T> collections, DataOutputStream dos,
                                        ConsumerWriteWithIOException<? super T> action) throws IOException {
        Objects.requireNonNull(action);
        dos.writeInt(collections.size());
        for (T t : collections) {
            action.write(t);
        }
    }
}
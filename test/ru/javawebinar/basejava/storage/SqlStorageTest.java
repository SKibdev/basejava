package ru.javawebinar.basejava.storage;

public class SqlStorageTest extends AbstractStorageTest {
    protected SqlStorageTest() {
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes","postgres","rootroot"));
    }
}

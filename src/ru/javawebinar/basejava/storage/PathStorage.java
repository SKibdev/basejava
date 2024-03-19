package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.ObjectStreamStorage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    public PathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> pathsStream = getPathsStreamFromDirectory()) {
            pathsStream.forEach(this::doDelete);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> pathsStream = getPathsStreamFromDirectory()) {
            return pathsStream.toList().size();
        }
    }

    private Stream<Path> getPathsStreamFromDirectory() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("The directory does not exist or an I/O error occurred", null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try (Stream<Path> pathsStream = getPathsStreamFromDirectory()) {
            return pathsStream.map(this::doGet).collect(Collectors.toList());
        }
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        setSerializationStrategy(new ObjectStreamStorage());
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        setSerializationStrategy(new ObjectStreamStorage());
        try {
            return serializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File deletion error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    protected void doWrite(Resume r, OutputStream os) throws IOException {

    }

    protected Resume doRead(InputStream is) throws IOException {
        return null;
    }
}

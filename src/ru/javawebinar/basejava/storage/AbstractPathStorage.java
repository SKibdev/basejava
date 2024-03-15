package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> pathsStream = Files.list(directory)) {
            pathsStream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> pathsStream = Files.list(directory)) {
            List<Path> pathsList = pathsStream.toList();
            return pathsList.size();
        } catch (IOException e) {
            throw new StorageException("The directory does not exist or an I/O error occurred", null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try (Stream<Path> pathsStream = Files.list(directory)) {
            List<Path> paths = pathsStream.toList();
            List<Resume> pathsList = new ArrayList<>(size());
            for (Path path : paths) {
                pathsList.add(doGet(path));
            }
            return pathsList;
        } catch (IOException e) {
            throw new StorageException("The directory does not exist or an I/O error occurred", null, e);
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
        try {
            doWrite(r, path);
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(path);
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
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    protected abstract void doWrite(Resume r, Path path) throws IOException;

    protected abstract Resume doRead(Path path) throws IOException;
}

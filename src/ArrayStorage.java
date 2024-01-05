import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public static final int CAPACITY = 10000;
    Resume[] storage = new Resume[CAPACITY];
    int size;
    int indexResumePresent;

    void update(Resume r) {
        if (hasResume(r.uuid)) {
            storage[indexResumePresent] = r;
        }
    }

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (size == CAPACITY) {
            System.out.println("Error: Storage is full!");
            return;
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Error: Resume \"uuid\" (" + r + ") already exists!");
                    return;
                }
            }
        }
        storage[size++] = r;
    }

    Resume get(String uuid) {
        if (hasResume(uuid)) {
            return storage[indexResumePresent];
        }
        return null;
    }

    void delete(String uuid) {
        if (hasResume(uuid)) {
            if (size > 1 && indexResumePresent < size - 1) {
                System.arraycopy(storage, (indexResumePresent + 1), storage, indexResumePresent,
                        size - indexResumePresent - 1);
            }
            size--;
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    private boolean hasResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                indexResumePresent = i;
                return true;
            }
        }
        System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
        return false;
    }
}

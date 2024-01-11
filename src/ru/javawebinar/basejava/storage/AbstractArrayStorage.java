 package ru.javawebinar.basejava.storage;

 import ru.javawebinar.basejava.model.Resume;

 import java.util.Arrays;

 /**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

     public void clear() {
         Arrays.fill(storage, 0, size, null);
         size = 0;
     }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
            return null;
        }
        return storage[index];
    }

     /**
      * @return array, contains only Resumes in storage (without null)
      */
     public Resume[] getAll() {
         return Arrays.copyOf(storage, size);
     }

    protected abstract int getIndex(String uuid);
}

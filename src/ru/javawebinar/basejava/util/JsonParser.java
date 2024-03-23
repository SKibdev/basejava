package ru.javawebinar.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javawebinar.basejava.model.Section;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
//            .registerTypeAdapter(ContactType.class, new JsonDeserializer<Map<String, Object>>() {
//                @Override
//                public Map<String, Object> deserialize(JsonElement json1, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                    return new Gson().fromJson(json1, typeOfT);
//                }
//            })
//            .registerTypeAdapter(Resume.SectionType.class, new JsonDeserializer<Map<String, Object>>() {
//                @Override
//                public Map<String, Object> deserialize(JsonElement json1, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                    return new Gson().fromJson(json1, typeOfT);
//                }
//            })
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}

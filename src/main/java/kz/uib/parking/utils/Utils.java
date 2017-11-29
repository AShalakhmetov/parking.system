package kz.uib.parking.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import kz.uib.parking.model.User;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class Utils {

//    private final Type listType = new TypeToken<List<T>>(){}.getType();
//
//    public ArrayList<Object> readFromFile(String filename) throws IOException {
//        final File file = new File(filename);
//        ArrayList<Object> list = new ArrayList();
//        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
//            final JsonReader reader = new JsonReader(fileReader);
//            final ArrayList<Object> t = gson.fromJson(reader, listType);
//            list = t;
//        }
//        return list;
//    }
//
//    public static void writeToFile(final ArrayList<Class> t, final String filepath) throws IOException {
//        final Writer writer = new FileWriterWithEncoding(filepath, StandardCharsets.UTF_8);
//        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        gson.toJson(t, writer);
//        writer.flush();
//        writer.close();
//    }


}

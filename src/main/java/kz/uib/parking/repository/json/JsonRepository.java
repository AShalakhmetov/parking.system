package kz.uib.parking.repository.json;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import kz.uib.parking.exception.UserNotFoundException;
import kz.uib.parking.model.AbstractModel;
import kz.uib.parking.model.User;
import kz.uib.parking.repository.interfaces.RepositoryInterface;
import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public abstract class JsonRepository<T extends AbstractModel> implements RepositoryInterface<T> {

    public Gson gson = new Gson();
    private final String filename;
    protected ArrayList<T> lastUpdate;

    public JsonRepository(final String filename) {
        this.filename = filename;
        setListUpToDate();
    }

    @Override
    public void addOne(final T t) {
        try {
            lastUpdate.add(t);
            writeToFileWithJson(lastUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("#addOne()");
    }

    @Override
    public boolean deleteOne(final T t) {
        try {
            lastUpdate = readFromJsonFile();
            if(lastUpdate == null) return false;
            else {
                if(lastUpdate.contains(t)) {
                    lastUpdate.remove(t);
                    writeToFileWithJson(lastUpdate);
                    return true;
                }else{
                    throw new UnsupportedOperationException("No such security code!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("#deleteOne()");
    }

    @Override
    public boolean deleteOneById(final String id, Class<T[]> clazz) {
        try {
            String s = new Gson().toJson(readFromJsonFile());
            lastUpdate = crutch(s, clazz);
            if(lastUpdate == null) throw new UnsupportedOperationException("No such security code with such ID!");
            else {
                for(T x : lastUpdate){
                    if(x.getId().equals(id)){
                        lastUpdate.remove(x);
                        writeToFileWithJson(lastUpdate);
                        return true;
                    }else return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("#deleteOneById()");
    }

    @Override
    public T getById(final String id) {
        if(lastUpdate == null) return null;
        else {
            for(T x : lastUpdate){
                if(x.getId().equals(id)){
                    return x;
                }else return null;
            }
        }
        throw new UnsupportedOperationException("#getById()");
    }

    @Override
    public ArrayList<T> findAll() {
        if(lastUpdate != null) return this.lastUpdate;
        else throw new UnsupportedOperationException("#findAll()");
    }

    @Override
    public void addAll(final ArrayList<T> list) {
        lastUpdate = list;
        try {
            writeToFileWithJson(lastUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public boolean checkIfExists(final String id) throws UserNotFoundException {
        return false;
    }

    public String getFilename() {
        return this.filename;
    }

    private void setListUpToDate(){
        try {
            lastUpdate = readFromJsonFile();
            if(lastUpdate == null) lastUpdate = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFileWithJson(final ArrayList<T> t) throws IOException {
        final Writer writer = new FileWriterWithEncoding(this.filename, StandardCharsets.UTF_8);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(t, writer);
        writer.flush();
        writer.close();
    }


    public ArrayList<T> readFromJsonFile() throws IOException {
        final File file = new File(this.filename);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            final JsonReader reader = new JsonReader(fileReader);
            return gson.fromJson(reader, Object.class);
        }
    }

    private <T> ArrayList<T> crutch(String s, Class<T[]> clazz){
        T[] arr = new Gson().fromJson(s, clazz);
        return new ArrayList<>(Arrays.asList(arr));
    }
}

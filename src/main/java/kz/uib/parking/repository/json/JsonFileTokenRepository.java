package kz.uib.parking.repository.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import kz.uib.parking.model.Token;
import kz.uib.parking.repository.interfaces.TokenRepositoryInterface;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class JsonFileTokenRepository extends JsonRepository<Token> implements TokenRepositoryInterface {
    private final Type listType = new TypeToken<List<Token>>(){}.getType();
    public JsonFileTokenRepository(final String filename) {
        super(filename);
    }

    @Override
    public Token readTokenByUserId(final String userId) {
        final File file = new File(getFilename());
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            final JsonReader reader = new JsonReader(fileReader);
            final ArrayList<Token> tList;

            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (ArrayList<Token>) o;
            }
            for (Token t : tList) {
                if (t.getUser().getId().equals(userId)) return t;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("#readTokenByUserId()");
    }

    @Override
    public void removeTokenByUserId(final String userId) {
        final File file = new File(getFilename());
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            final JsonReader reader = new JsonReader(fileReader);
            final ArrayList<Token> tList;

            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (ArrayList<Token>) o;
            }
            for (Token t : tList) {
                if (t.getUser().getId().equals(userId)) {
                    tList.remove(t);
                    super.addAll(tList);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("#removeTokenByUserId()");
    }
}

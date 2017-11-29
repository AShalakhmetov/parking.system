package kz.uib.parking.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import kz.uib.parking.model.SecurityCode;
import kz.uib.parking.repository.interfaces.SecurityCodeRepositoryInterface;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class JsonFileSecurityCodeRepository extends JsonRepository<SecurityCode> implements SecurityCodeRepositoryInterface {

    private final Type listType = new TypeToken<List<SecurityCode>>(){}.getType();

    public JsonFileSecurityCodeRepository(final String filename) {
        super(filename);
    }

    @Override
    public SecurityCode getBySecurityCode(final String securityCode) {
        throw new UnsupportedOperationException("#getBySecurityCode()");
    }

    @Override
    public SecurityCode getByUserId(final String userId) {
        final File file = new File(getFilename());
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {

            final JsonReader reader = new JsonReader(fileReader);
            final ArrayList<SecurityCode> tList;

            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (ArrayList<SecurityCode>) o;
            }

            System.out.println(tList.get(0).getSecurityCode());
            for (final SecurityCode code : tList) {
                if (code.getUserId().equals(userId)) {
                    return code;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        throw new UnsupportedOperationException("#getByUserId()");
    }

    @Override
    public Boolean checkIfUserSecurityCode(final String securityCode,
        final String userId) {

        final File file = new File(getFilename());
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {

            final JsonReader reader = new JsonReader(fileReader);
            final List<SecurityCode> tList;

            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (List<SecurityCode>) o;
            }

            System.out.println(tList.get(0).getSecurityCode());
            for (final SecurityCode code : tList) {
                if (code.getUserId().equals(userId)) {
                    if (code.getSecurityCode().equals(securityCode)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

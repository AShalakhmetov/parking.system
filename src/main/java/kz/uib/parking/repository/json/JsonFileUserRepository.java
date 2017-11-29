package kz.uib.parking.repository.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kz.uib.parking.exception.UserNotFoundException;
import kz.uib.parking.model.User;
import kz.uib.parking.repository.interfaces.UserRepositoryInterface;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class JsonFileUserRepository extends JsonRepository<User> implements UserRepositoryInterface {
    private final Type listType = new TypeToken<List<User>>(){}.getType();

    public JsonFileUserRepository(final String filename) {
        super(filename);
    }

    @Override
    public User findUserByPhoneNumber(final String phoneNumber) {
        final File file = new File(getFilename());
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            final JsonReader reader = new JsonReader(fileReader);
            final List<User> tList;

            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (List<User>) o;
            }
            for (User u : tList) {
                if (u.getLogin().equals(phoneNumber)) return u;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("#findUserByPhoneNumber()");
    }

    @Override
    public boolean updateUserPassword(final String userId,
                                      final String sha512Password) throws UserNotFoundException {
        final File file = new File(getFilename());
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {

            final JsonReader reader = new JsonReader(fileReader);
            final ArrayList<User> tList;

            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (ArrayList<User>) o;
            }

            tList
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Не найден пользователь с таким userId: " + userId))
                .setPassword(sha512Password);
            super.addAll(tList);
            return true;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        //throw new UnsupportedOperationException("#updateUserPassword()");
    }

    @Override
    public boolean checkIfExists(final String login) throws UserNotFoundException {
        try (final Reader fileReader = new InputStreamReader(new FileInputStream(super.getFilename()), "UTF-8")) {
            final JsonReader reader = new JsonReader(fileReader);
            final List<User> tList;
            final Object o = gson.fromJson(reader, listType);

            if (o == null) {
                tList = new ArrayList<>();
            } else {
                tList = (List<User>) o;
            }
            if(!tList.isEmpty()){
                for(User item : tList){
                    if(item.getLogin().equals(login))return true;
                }
                return false;
            }else{
                return false;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

package kz.uib.parking.repository.interfaces;

import kz.uib.parking.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public interface RepositoryInterface<T> {

    public void addOne(final T t);

    public boolean deleteOne(final T t);

    public boolean deleteOneById(final String id, Class<T[]> clazz);

    public T getById(final String id);

    public List<T> findAll();

    public void addAll(final ArrayList<T> list);

    public boolean checkIfExists(final String id) throws UserNotFoundException;
}

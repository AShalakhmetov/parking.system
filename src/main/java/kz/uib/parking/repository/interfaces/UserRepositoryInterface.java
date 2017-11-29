package kz.uib.parking.repository.interfaces;

import kz.uib.parking.exception.UserNotFoundException;
import kz.uib.parking.model.User;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public interface UserRepositoryInterface extends RepositoryInterface<User> {

    public User findUserByPhoneNumber(String phoneNumber);

    boolean updateUserPassword(final String userId,
                               final String sha512Password) throws UserNotFoundException;
}

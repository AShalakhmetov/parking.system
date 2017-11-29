package kz.uib.parking.service;

import kz.uib.parking.exception.TokenNotFoundException;
import kz.uib.parking.exception.UserNotFoundException;
import kz.uib.parking.model.User;
import kz.uib.parking.repository.interfaces.TokenRepositoryInterface;
import kz.uib.parking.repository.interfaces.UserRepositoryInterface;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class UserService {

    private final UserRepositoryInterface userRepository;
    private final TokenRepositoryInterface tokenRepository;

    public UserService(
        final UserRepositoryInterface userRepository,
        final TokenRepositoryInterface tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User getUserInfo(final String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public void logout(final String phoneNumber) throws UserNotFoundException, TokenNotFoundException {
        final User user = userRepository.findUserByPhoneNumber(phoneNumber);
        tokenRepository.removeTokenByUserId(user.getId());
    }

    /**
     * Бонусный метод продумайте сами
     */
    public void changePassword() {
        //TODO Тут требуются доработки
    }

    /**
     * Бонусный метод продумайте сами
     */
    public void changeNickname() {
        //TODO Тут требуются доработки
    }
}

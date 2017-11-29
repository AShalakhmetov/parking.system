package kz.uib.parking.service;

import com.google.gson.Gson;
import kz.uib.parking.exception.UserNotFoundException;
import kz.uib.parking.model.AbstractModel;
import kz.uib.parking.model.SecurityCode;
import kz.uib.parking.model.User;
import kz.uib.parking.repository.interfaces.SecurityCodeRepositoryInterface;
import kz.uib.parking.repository.interfaces.UserRepositoryInterface;
import kz.uib.parking.repository.json.JsonFileSecurityCodeRepository;
import kz.uib.parking.repository.json.JsonFileUserRepository;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class RegistrationService {

    private final UserRepositoryInterface userRepository =
        new JsonFileUserRepository("D:\\Projects\\Study\\E-Commerce (Source)\\users.txt");
    private final SecurityCodeRepositoryInterface securityCodeRepository =
        new JsonFileSecurityCodeRepository("D:\\Projects\\Study\\E-Commerce (Source)\\secCodes.txt");

    public RegistrationService(){}

    /** Метод должен создать нового юзера и создать новый security code. Юзер пока без пароля
     *
     * @param phoneNumber номер юзера, он же логин
     * @param nickName никнейм юзера
     * @param email емейл юзера
     */
    public String registerNewUser(final String phoneNumber, final String nickName, final String email) throws UserNotFoundException {
        final User user = new User();
        user.setLogin(phoneNumber);
        user.setNickname(nickName);
        user.setEmail(email);

        if(!userRepository.checkIfExists(user.getLogin())) {
            final SecurityCode code = new SecurityCode();
            userRepository.addOne(user);
            securityCodeRepository.addOne(code);
            System.out.println("Security code: "
                    + code.getSecurityCode()
                    + " for user " + user.getLogin()
                    + " (" + user.getId() + ");");
            return "{\"status\":\"success\", \"code\":\"100\", \"message\":\"Registration successful.\"}";
        }
        else return "{\"status\":\"failed\", \"code\":\"109\", \"message\":\"Registration failed. User exists!\"}";
    }

    public String registerNewUser(final User user) throws UserNotFoundException {
        final SecurityCode code = new SecurityCode();
        code.setUserId(user.getId());

        if(!userRepository.checkIfExists(user.getLogin())) {
            userRepository.addOne(user);
            securityCodeRepository.addOne(code);
            System.out.println("Security code: "
                    + code.getSecurityCode()
                    + " for user " + user.getLogin()
                    + " (" + user.getId() + ");");
            return "{\"status\":\"success\", \"code\":\"100\", \"message\":\"Registration successful.\"}";
        }
        else return "{\"status\":\"failed\", \"code\":\"109\", \"message\":\"Registration failed. User already exists!\"}";
    }

    /**
     * Проверяет есть ли у этого юзера такой security code в базе данных
     * @param securityCode сам код
     * @param phoneNumber номер юзера, он же логин
     * @param sha512password
     */
    public boolean checkSecurityCodeValid(final String securityCode,
        final String phoneNumber, final String sha512password) throws UserNotFoundException {

        final User userByPhoneNumber = userRepository.findUserByPhoneNumber(phoneNumber);

        final Boolean isValid = securityCodeRepository.checkIfUserSecurityCode(securityCode, userByPhoneNumber.getId());

        if (isValid) {
            if(userRepository.updateUserPassword(userByPhoneNumber.getId(), sha512password)) {
                SecurityCode code = securityCodeRepository.getByUserId(userByPhoneNumber.getId());
                System.out.println(code.getSecurityCode() + " : " + code.getId() + " : " + code.getUserId());
                securityCodeRepository.deleteOneById(code.getId(), SecurityCode[].class);
                return true;
            }
        } else {
            throw new IllegalStateException();
        }
        return false;
    }

    /**
     * Создаем новый security code для этого юзера
     * TODO здесь важно удалить предыдущий код если он есть. Этого не сделано
     * @param phoneNumber номер юзера, он же логин
     */
    public void generateNewSecurityCode(final String phoneNumber) {
        //TODO Тут требуются доработки
        securityCodeRepository.addOne(new SecurityCode());
    }

    public String isNumberAvailable() {
        //TODO Тут требуются доработки
        return "true/false";
    }

    public String login() {
        //TODO Тут требуются доработки
        return "TOKEN";
    }

}

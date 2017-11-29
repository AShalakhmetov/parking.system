package kz.uib.parking.controller;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kz.uib.parking.exception.UserNotFoundException;
import kz.uib.parking.model.Token;
import kz.uib.parking.model.User;
import kz.uib.parking.repository.json.JsonFileSecurityCodeRepository;
import kz.uib.parking.service.RegistrationService;
import kz.uib.parking.service.TokenService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
@RestController
public class RegistrationContoller {

    /**
     *
     * Регистрация юзера
     *
     * POST метод
     *
     *         Принимает на вход body в виде:
     {
            "phoneNumber":"String", # Логин/Login
            "nickName":"String",
            "deviceId":"String" # уникальный id устройства. возможно это будет FCM токен
            "email":"String"
     }
     * @return Возвращает статус регистрации
     * Коды ответов:
     *              100 - Успешная регистрация.
     *              101 - Усрешная регистрация. Выслан токен.
     *              107 - Неудачная регистрация. Несовпадение секьюрити кода.
     *              108 - Нуедачная регистрация. Некорректные данные от клиента.
     *              109 - Неудачная регистрация. Пользователь существует.
     *
     */
    @RequestMapping(value = "/register", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    public String registerNewUser(@RequestBody String body) {

        // Validation
        Gson gson = new Gson();
        User user = gson.fromJson(body, User.class);
        if (user.checkPrerequisites()) {
            final RegistrationService registrationService = new RegistrationService();
            try {
                return registrationService.registerNewUser(user);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
                return "{\"status\":\"failed\", \"code\":\"109\", \"message\":\"Registration failed. User already exists!\"}";
            }
        }else{
            return "{\"status\":\"failed\", \"code\":\"108\", \"message\":\"Registration failed. Validation failed!\"}";
        }
    }

    /**
     *
     * POST метод
     *
     * Принимает на вход body в виде:
     {
        "phoneNumber":"",
        "securityCode":"",
        "password" : "SHA512 String"
     }

     Метод должен сверить security code, а потом выставить новый пароль пользователю

     * @return возвращает статус проверки и выдает токен для других API
     */
    @RequestMapping(value = "/checkRegistrationCode", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String checkSecurityCode(@RequestBody String body) throws UserNotFoundException {
        // Validation
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson( body, JsonObject.class);

        final String phoneNumber = jsonObject.get("phoneNumber").getAsString();
        final String securityCode = jsonObject.get("securityCode").getAsString();
        final String sha512Password = jsonObject.get("pass").getAsString();

        System.out.println(phoneNumber + " : " + securityCode + " : " + sha512Password);

        final RegistrationService registrationService = new RegistrationService();
        if(registrationService.checkSecurityCodeValid(securityCode, phoneNumber, sha512Password)) {

            return login(phoneNumber);
        }
        else return "{\"status\":\"success\", \"code\":\"107\", \"message\":\"Security code does not match!\"}";
    }

    /**
     *
     * Отправляет новый security code для регистрации
     *
     * POST метод
     *
     * Принимает на вход body в виде:
     *
     {
        "phoneNumber":""
     }
     * @return возвращет статус отправки.
     */
    public String generateNewRegistrationSecurityCode() {
        return "Отправляет статус генерации кода";
    }

    /**
     *
     * GET метод
     *
     * Принимает параметр в виде query string
        ?phoneNumber=+77772200051
     * @return возвращает true/false
     */
    public String isNumberAvailable() {
        return "true/false";
    }

    /**
     *
     * Отправляет новый security code для регистрации
     *
     * POST метод
     *
     * Принимает на вход body в виде:
     *
     {
        "phoneNumber":"String", # Login 77772200051 (11 цифр)
        "password":"SHA512 String"
     }
     * @return возвращает токен для использования других API
     */


    public String login(String login) {

        final TokenService tokenService = new TokenService();
        String token = tokenService.firstLogin(login);
        return "{\"status\":\"success\", \"code\":\"101\", \"token\":\"" + token + "\", \"message\":\"Login successful. Welcome!\"}";
    }

//    @RequestMapping
//    public String login(@RequestBody String body){
//        return "{\"status\":\"success\", \"code\":\"200\", \"token\":\"" + token + "\", \"message\":\"Login successful.Welcome!\"}";
//    }
}

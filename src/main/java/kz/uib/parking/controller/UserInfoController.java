package kz.uib.parking.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kz.uib.parking.service.TokenService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
@RestController
public class UserInfoController {

    /**
     * GET метод
     *
     {
        "token":"String",
        "phoneNumber":"String"
     }
     * @return возвращает модель #kz.uib.parking.model.User в виде json
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String getUserInfo(@RequestBody String body) {
        final TokenService tokenService = new TokenService();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson( body, JsonObject.class);

        final String phoneNumber = jsonObject.get("phoneNumber").getAsString();
        final String token = jsonObject.get("token").getAsString();
        boolean isChecked = tokenService.loginByToken(phoneNumber, token);
        if(isChecked) return gson.toJson(tokenService.getUserData(phoneNumber));
        else return "{\"status\":\"failed\", \"code\":\"206\", \"message\":\"Could not get user data!\"}";
    }

    /**
     *
     * Метод должен уничтожать токен пользователя
     *
     * POST метод

     Принимает данные в body в виде:

     {
        "phoneNumber":"String",
        "token":"String"
     }
     * @return возвращает статус вызова метода
     * Коды ответов:
     *              200 - Успешная аутентификация.
     *              201 - Успешная аутентификация. Токен приянт.
     *              202 - Успешная выход из системы.
     *              206 - Неудачная попытка извлечь данные пользователя.
     *              207 - Неудачная попытка выхода из системы.
     *              208 - Неудачная аутентификация. Неправильный токен.
     *              209 - Неудачная аутентификация. Неправильный логин или пароль.
     *
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String login(@RequestBody String body){
        final TokenService tokenService = new TokenService();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson( body, JsonObject.class);

        final String phoneNumber = jsonObject.get("phoneNumber").getAsString();
        final String sha512Password = jsonObject.get("pass").getAsString();
        String token = tokenService.login(phoneNumber, sha512Password);
        if(token != null){
            return "{\"status\":\"success\", \"code\":\"200\", \"token\":\"" + token + "\", \"message\":\"Login by password successful.\"}";
        }
        return "{\"status\":\"failed\", \"code\":\"209\", \"message\":\"Registration failed. Incorrect login or password!\"}";
    }

    @RequestMapping(value = "/loginByToken", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String loginByToken(@RequestBody String body){
        final TokenService tokenService = new TokenService();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson( body, JsonObject.class);

        final String phoneNumber = jsonObject.get("phoneNumber").getAsString();
        final String token = jsonObject.get("token").getAsString();

        boolean isChecked = tokenService.loginByToken(phoneNumber, token);
        if(isChecked) return "{\"status\":\"success\", \"code\":\"201\", \"message\":\"Login by token successful.\"}";
        else return "{\"status\":\"failed\", \"code\":\"208\", \"message\":\"Login by token failed!\"}";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String logout(@RequestBody String body) {
        final TokenService tokenService = new TokenService();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson( body, JsonObject.class);

        final String phoneNumber = jsonObject.get("phoneNumber").getAsString();
        final String token = jsonObject.get("token").getAsString();

        boolean isChecked = tokenService.logout(phoneNumber, token);
        if(isChecked) return "{\"status\":\"success\", \"code\":\"202\", \"message\":\"User has successfully loged out.\"}";
        else return "{\"status\":\"failed\", \"code\":\"207\", \"message\":\"Logout failed!\"}";
    }

    /**
     * Бонусный метод продумайте сами
     * @return
     */
    public String changePassword() {
        return "";
    }

    /**
     * Бонусный метод продумайте сами
     * @return
     */
    public String changeNickname() {
        return "";
    }
}

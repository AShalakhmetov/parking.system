package kz.uib.parking.service;

import kz.uib.parking.model.Token;
import kz.uib.parking.model.User;
import kz.uib.parking.repository.interfaces.SecurityCodeRepositoryInterface;
import kz.uib.parking.repository.interfaces.TokenRepositoryInterface;
import kz.uib.parking.repository.interfaces.UserRepositoryInterface;
import kz.uib.parking.repository.json.JsonFileSecurityCodeRepository;
import kz.uib.parking.repository.json.JsonFileTokenRepository;
import kz.uib.parking.repository.json.JsonFileUserRepository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class TokenService {
    private final TokenRepositoryInterface tokenRepository =
            new JsonFileTokenRepository("D:\\Projects\\Study\\E-Commerce (Source)\\tokens.txt");
    private final UserRepositoryInterface userRepository =
            new JsonFileUserRepository("D:\\Projects\\Study\\E-Commerce (Source)\\users.txt");
    private final SecurityCodeRepositoryInterface codeRepository =
            new JsonFileSecurityCodeRepository("D:\\Projects\\Study\\E-Commerce (Source)\\secCodes.txt");

    public TokenService(){}

    //TODO Тут требуются доработки

    public String login(String login, String sha512password){
        User u = userRepository.findUserByPhoneNumber(login);
        Token token = new Token();
        if(u.getPassword().equals(sha512password)){

            token.setUser(userRepository.findUserByPhoneNumber(login));
            Date date = new Date();
            token.setTimestamp(new Timestamp(date.getTime()));

            tokenRepository.addOne(token);
            return token.getId();
        }
        return null;
    }

    public boolean loginByToken(String login, String passedToken){
        User u = userRepository.findUserByPhoneNumber(login);
        Token token = tokenRepository.readTokenByUserId(u.getId());
        if(token.getId().equals(passedToken)){
            tokenRepository.removeTokenByUserId(u.getId());
            Date date = new Date();
            token.setTimestamp(new Timestamp(date.getTime()));
            tokenRepository.addOne(token);
            return true;
        }
        return false;
    }

    public String firstLogin(String login){
        Token token = new Token();
        token.setUser(userRepository.findUserByPhoneNumber(login));
        Date date = new Date();
        token.setTimestamp(new Timestamp(date.getTime()));

        tokenRepository.addOne(token);

        return token.getId();
    }

    public boolean logout(String login, String passedToken){
        User u = userRepository.findUserByPhoneNumber(login);
        Token token = tokenRepository.readTokenByUserId(u.getId());

        if(token.getId().equals(passedToken)){
            tokenRepository.removeTokenByUserId(u.getId());
            return true;
        }
        return false;
    }

    public User getUserData(String phoneNumber){

        return userRepository.findUserByPhoneNumber(phoneNumber);
    }
}

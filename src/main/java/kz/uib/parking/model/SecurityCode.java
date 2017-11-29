package kz.uib.parking.model;

import java.util.Random;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class SecurityCode extends AbstractModel{

    // секретный код для активации пользователей. Должен генерироваться рандомом
    String securityCode;
    String userId;

    public SecurityCode(){
        String random = String.valueOf(new Random().nextInt(99999));
        if(random.length() < 5){
            for(int i = 0; i < 5 - random.length(); i++){
                random = "0" + random;
            }
        }
        this.securityCode = random;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public void setSecurityCode(final String securityCode) {
        this.securityCode = securityCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}

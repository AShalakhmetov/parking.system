package kz.uib.parking.model;

import java.util.Date;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class Token extends AbstractModel {

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // Кому был выдан токен
    User user;

    // Дата выдачи токена
    Date timestamp;
}

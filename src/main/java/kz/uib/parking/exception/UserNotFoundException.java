package kz.uib.parking.exception;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
    }

    public UserNotFoundException(final String message) {
        super(message);
    }
}

package kz.uib.parking.model;

import java.util.UUID;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class UUIDTest {

    @Test
    public void testUserUUID() {
//        final User user = new User();
//        assertEquals(user.getId().length(), UUID.randomUUID().toString().length());
    }

    @Test
    public void testTokenUUID() {
        final Token token = new Token();
        assertEquals(token.getId().length(), UUID.randomUUID().toString().length());
    }

}

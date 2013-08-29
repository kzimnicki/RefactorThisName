package cc.explain.server.api;

import cc.explain.server.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 17.06.13
 * Time: 20:54
 */
public class UserServiceTest {

    @Test
    public void shouldGenerateActivationKey(){
        String username = "user@gmail.com";

        String activationKey = new UserService().generateActivationKey(username);

        assertEquals("cba1f2d695a5ca39ee6f343297a761a4", activationKey);
    }

    @Test
    public void shouldGenerateActivationLink(){
        User user = new User();
        user.setId(1L);
        user.setUsername("user@gmail.com");

        String textLink = new UserService().generateLink(user);

        assertEquals("https://explain.cc/app/activate/13/cba1f2d695a5ca39ee6f343297a761a4", textLink);
    }

    @Test
    public void shouldValidateIdAndKey(){
        User user = new User();
        user.setId(1L);
        user.setUsername("user@gmail.com");
        String key = "cba1f2d695a5ca39ee6f343297a761a4";


        boolean result = new UserService().validateActivation(user, key);

        assertEquals(true, result);
    }

    @Test
    public void shouldValidateResetLink(){
        User user = new User();
        user.setId(1L);
        user.setUsername("user@gmail.com");
        String key = "cba1f2d695a5ca39ee6f343297a761a4";

        boolean result = new UserService().validateReset(user, key);

        assertEquals(true, result);
    }

    @Test
    public void shouldCreateActivateEmailMessage(){
        String link = "https://explain.cc/app/activate/13/cba1f2d695a5ca39ee6f343297a761a4";

        String message = new UserService().createActivationEmailMessage(link);

        assertEquals("Please confirm user account: https://explain.cc/app/activate/13/cba1f2d695a5ca39ee6f343297a761a4", message);
    }

    @Test
    public void shouldCreateResetEmailMessage(){
        String link = "https://explain.cc/?key=cba1f2d695a5ca39ee6f343297a761a4&username=testuser3@gmail.com";

        String message = new UserService().createResetEmailMessage(link);

        assertEquals("Please reset password: https://explain.cc/?key=cba1f2d695a5ca39ee6f343297a761a4&username=testuser3@gmail.com", message);
    }

    @Test
    public void shouldCreateResetPasswordLink(){
        User user = new User();
        user.setId(1L);
        user.setUsername("user@gmail.com");

        String link = new UserService().generateResetPasswordLink(user);

        assertEquals("https://explain.cc/?key=cba1f2d695a5ca39ee6f343297a761a4&username=user@gmail.com", link);
    }

}

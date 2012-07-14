package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import server.model.newModel.User;

public class LoginService {


    @Autowired
    CommonDao commonDao;

    @Autowired
    AuthenticationManager authenticationManager;

    public LoginServiceResult register(User user) {
        try {
            user.setRole(Role.USER);
            commonDao.create(user);
        } catch (DataIntegrityViolationException e) {
            return LoginServiceResult.USER_ALREADY_EXIST;
        }
        return LoginServiceResult.SUCCESS;
    }

    public LoginServiceResult login(User user) {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (BadCredentialsException e) {
            return LoginServiceResult.BAD_LOGIN_OR_PASSWORD;
        }
        return LoginServiceResult.SUCCESS;
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String username = authentication.getName();
        //Todo refactor
        User user = (User) commonDao.getByHQL("from User u where u.username = :username", "username", username).get(0);
        return user;
    }
}

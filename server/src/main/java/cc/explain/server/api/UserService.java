package cc.explain.server.api;

import cc.explain.server.core.CommonDao;
import cc.explain.server.model.Configuration;
import cc.explain.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {
    @Autowired
    CommonDao commonDao;

    @Autowired
    AuthenticationManager authenticationManager;

    public static final User DUMMY_USER = new User();

    public LoginServiceResult register(User user) {
        user.setRole(Role.USER);
        commonDao.create(user);
        return LoginServiceResult.SUCCESS;
    }

    public LoginServiceResult login(User user) {
        Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
        return LoginServiceResult.SUCCESS;
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return DUMMY_USER;
        }
        String username = authentication.getName();
        //Todo refactor
        User user = (User) commonDao.getByHQL("from User u where u.username = :username", "username", username).get(0);
        return user;
    }

    public void save(User user) {
        commonDao.saveOrUpdate(user);
    }

    public void clearAutentication(){
         SecurityContextHolder.clearContext();
    }

    public void save(User user, Configuration config) {
        user.getConfig().setMax(config.getMax());
        user.getConfig().setMin(config.getMin());
        user.getConfig().setSubtitleTemplate(config.getSubtitleTemplate());
        user.getConfig().setTextTemplate(config.getTextTemplate());
        user.getConfig().setPhrasalVerbAdded(config.isPhrasalVerbAdded());
        user.getConfig().setSubtitleProcessor(config.getSubtitleProcessor());
        save(user);
    }
}

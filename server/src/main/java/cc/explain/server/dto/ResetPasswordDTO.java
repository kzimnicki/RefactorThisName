package cc.explain.server.dto;

/**
 * User: kzimnick
 * Date: 11.08.13
 * Time: 20:19
 */
public class ResetPasswordDTO {

    private String username;
    private String key;
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

package server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import server.api.Role;

//
//@Entity
//@Table(name = "user")
public class User extends EntityObject {

	@Column(unique = true)
	@NotBlank
	@Email
	@Size(min = 5, max = 40)
	private String username;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;

	@NotNull
	private String role;

	private boolean enabled = true;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return toString(getId(), username, password, role, enabled);
	}

    public void setRole(String role) {
        this.role = role;
    }

     public String getRole() {
        return role;
    }
}

package server.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * User: kzimnick
 * Date: 23.04.12
 * Time: 12:52
 */
@Entity
public class Word extends EntityObject {
    @NotBlank
    @Column(unique = true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

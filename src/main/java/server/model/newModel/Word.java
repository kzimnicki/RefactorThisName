package server.model.newModel;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import server.core.WordType;
import server.model.EntityObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * User: kzimnick
 * Date: 23.04.12
 * Time: 12:52
 */
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"value", "wordType"})}
)
@Data
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class Word extends EntityObject {

    public static Word EMPTY = new Word();

    @NotBlank
    private String value;

    @NotNull
    private WordType wordType;

    private int frequency=0;

}

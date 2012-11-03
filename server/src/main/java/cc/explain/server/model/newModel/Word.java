package cc.explain.server.model.newModel;

import cc.explain.server.core.WordType;
import cc.explain.server.model.EntityObject;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"value", "wordType"})},
        name="word"
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

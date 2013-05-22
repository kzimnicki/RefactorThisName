package cc.explain.server.model;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * User: kzimnick
 * Date: 26.08.12
 * Time: 12:20
 */
@Entity
@Data
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"word_id", "rootWord_id"})},
        name="wordrelation"
)
public class WordRelation extends EntityObject {

    @NotNull
    @OneToOne
    @Cascade( { CascadeType.SAVE_UPDATE})
    private RootWord rootWord;

    @NotNull
    @OneToOne
    @Cascade( { CascadeType.SAVE_UPDATE})
    private Word word;

}

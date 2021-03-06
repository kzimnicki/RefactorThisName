package cc.explain.server.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * User: kzimnick
 * Date: 26.08.12
 * Time: 13:04
 */
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"rootWord_id"})},
        name = "rootword"
)
public class RootWord extends EntityObject {

    @NotNull
    @OneToOne
    @Cascade( { CascadeType.SAVE_UPDATE})
    private Word rootWord;

    public Word getRootWord() {
        return rootWord;
    }

    public void setRootWord(Word rootWord) {
        this.rootWord = rootWord;
    }
}

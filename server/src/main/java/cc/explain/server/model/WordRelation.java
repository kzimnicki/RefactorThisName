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
 * Time: 12:20
 */
@Entity
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

    public RootWord getRootWord() {
        return rootWord;
    }

    public void setRootWord(RootWord rootWord) {
        this.rootWord = rootWord;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}

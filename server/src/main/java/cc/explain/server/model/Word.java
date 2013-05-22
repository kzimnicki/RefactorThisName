package cc.explain.server.model;

import cc.explain.server.core.WordType;
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
public class Word extends EntityObject {

    public static Word EMPTY = new Word();

    @NotBlank
    private String value;

    @NotNull
    private WordType wordType;

    private int frequency=0;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}

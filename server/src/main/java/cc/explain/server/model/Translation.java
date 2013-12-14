package cc.explain.server.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * User: kzimnick
 * Date: 26.10.12
 * Time: 21:36
 */
@Entity
@Table(name = "translation",uniqueConstraints = {@UniqueConstraint(columnNames = {"sourceLang", "transLang", "sourceWord"})})
public class Translation extends EntityObject {

    @Enumerated(EnumType.STRING)
    private Language sourceLang;

    @Enumerated(EnumType.STRING)
    private Language transLang;

    @NotBlank
    private String sourceWord;

    private String transWord;

    public Language getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(Language sourceLang) {
        this.sourceLang = sourceLang;
    }

    public Language getTransLang() {
        return transLang;
    }

    public void setTransLang(Language transLang) {
        this.transLang = transLang;
    }

    public String getSourceWord() {
        return sourceWord;
    }

    public void setSourceWord(String sourceWord) {
        this.sourceWord = sourceWord;
    }

    public String getTransWord() {
        return transWord;
    }

    public void setTransWord(String transWord) {
        this.transWord = transWord;
    }
}

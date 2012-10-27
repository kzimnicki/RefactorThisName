package server.model.newModel;


import edu.stanford.nlp.international.Languages;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import server.model.EntityObject;

import javax.persistence.*;

/**
 * User: kzimnick
 * Date: 26.10.12
 * Time: 21:36
 */
@Entity
@Data
@Table(name = "translation",uniqueConstraints = {@UniqueConstraint(columnNames = {"sourceLang", "transLang", "sourceWord"})})
public class Translation extends EntityObject {

    @Enumerated(EnumType.STRING)
    private Language sourceLang;

    @Enumerated(EnumType.STRING)
    private Language transLang;

    @NotBlank
    private String sourceWord;

    private String transWord;

}

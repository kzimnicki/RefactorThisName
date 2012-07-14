package server.model.newModel;

import org.hibernate.validator.constraints.NotBlank;
import server.model.EntityObject;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:46
 */
//@Entity
public class Document extends EntityObject{
    @NotBlank
    private String uri;

    @NotBlank
    private String text;

    @OneToMany
    private List<PhrasalVerb> phrasalVerbs;

    @OneToMany
    private List<PhrasalVerb> vocabulary;

    @NotBlank
    private String currentConfig;
}

package cc.explain.server.model.newModel;

import cc.explain.server.model.EntityObject;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.OneToMany;
import java.util.List;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:46
 */
//@Entity
//@Table(name = "document")
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

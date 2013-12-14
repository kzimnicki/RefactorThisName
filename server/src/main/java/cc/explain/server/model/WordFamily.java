package cc.explain.server.model;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:38
 */
//@Entity
@Table(name = "wordfamily")
public class WordFamily extends EntityObject {

    public static WordFamily EMPTY = new WordFamily();

    @NotNull
    @OneToOne
    private Word root;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Word> family = new ArrayList<Word>();
}

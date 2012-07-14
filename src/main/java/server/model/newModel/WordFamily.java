package server.model.newModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import server.model.EntityObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:38
 */
@Entity
public class WordFamily extends EntityObject {

    public static WordFamily EMPTY = new WordFamily();

    @Getter
    @Setter
    @NotNull
    @OneToOne
    private Word root;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private List<Word> family = new ArrayList<Word>();

    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

package server.model.newModel;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import server.model.EntityObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: kzimnick
 * Date: 26.08.12
 * Time: 13:04
 */
@Entity
@Data
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"rootWord_id"})}
)
public class RootWord extends EntityObject {

    @NotNull
    @OneToOne
    @Cascade( { CascadeType.SAVE_UPDATE})
    private Word rootWord;

}

package server.model.newModel;

import lombok.Data;
import server.model.EntityObject;

import javax.persistence.*;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:37
 */
//@Entity
//@Table(
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"verb_id", "suffix1_id", "suffix2_id"})}
//)
//@Data
public class PhrasalVerb extends EntityObject{

    @OneToOne
    private RootWord verb;

    @OneToOne
    private Word suffix1;

    @OneToOne
    private Word suffix2;

}

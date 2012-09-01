package server.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 * User: kzimnick
 * Date: 25.08.12
 * Time: 16:54
 */
@MappedSuperclass
public abstract class AuditableEntityObject extends EntityObject {

    @Column(name = "created", insertable=true, updatable=true)
    private Date created = new Date();

//    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", insertable=true, updatable=true)
    private Date updated = new Date();
}

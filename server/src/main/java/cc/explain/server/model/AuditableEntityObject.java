package cc.explain.server.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", insertable=true, updatable=true)
    private Date updated = new Date();
}

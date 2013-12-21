package cc.explain.server.model;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * User: kzimnick
 * Date: 30.05.13
 * Time: 14:39
 */
@MappedSuperclass
public abstract class UserWord extends EntityObject{

    @OneToOne
    private User user;

    @OneToOne
    protected RootWord rootWord;

    @Column(name = "created", insertable=true, updatable=true)
    private Date created = new Date();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserWord){
            final UserWord other = (UserWord) obj;
            return Objects.equal(this.getRootWord(), other.getRootWord());
        } else{
            return false;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public RootWord getRootWord() {
        return rootWord;
    }

    public void setRootWord(RootWord rootWord) {
        this.rootWord = rootWord;
    }
}

package server.model;

import javax.persistence.*;


@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "word_id"})}
)
public class UserExcludeWord extends EntityObject {

    @OneToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Word word;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}

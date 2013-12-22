package cc.explain.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: kzimnick
 * Date: 30.05.13
 * Time: 13:15
 */
@Entity
@Table(name="user_includedwords")
public class UserIncludeWord extends UserWord {

    public UserIncludeWord(){}

    public UserIncludeWord(RootWord rootWord) {
        this.rootWord = rootWord;
    }

    @Override
    public int hashCode(){
        return rootWord != null? rootWord.hashCode() : 0 ;
    }
}

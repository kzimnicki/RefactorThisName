package cc.explain.server.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * User: kzimnick
 * Date: 30.05.13
 * Time: 13:15
 */
@Entity
@Data
@Table(name="user_excludedwords")
public class UserExcludeWord extends UserWord{

    public UserExcludeWord(){}

    public UserExcludeWord(RootWord rootWord) {
        this.rootWord = rootWord;
    }

    @Override
    public int hashCode(){
        return rootWord != null? rootWord.hashCode() : 0 ;
    }
}

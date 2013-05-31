package cc.explain.server.model;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.persistence.*;
import java.util.Date;

/**
 * User: kzimnick
 * Date: 30.05.13
 * Time: 13:15
 */
@Entity
@Data
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

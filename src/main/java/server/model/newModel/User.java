package server.model.newModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import server.model.EntityObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Data
public class User extends EntityObject {

    @Column(unique = true)
    @NotBlank
    @Email
    @Size(min = 5, max = 40)
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @Getter
    @Setter
    private String role;

    private boolean enabled = true;

    @OneToMany
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name="user_excludedWords")
    private List<WordFamily> excludedWords;

    @OneToMany
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name="user_includedWords")
    private List<WordFamily> includedWords;

    @OneToMany
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name="user_excludedPhrasalVerbs")
    private List<PhrasalVerb> excludedPhrasalVerbs;

    @OneToMany
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name="user_includedPhrasalVerbs")
    private List<PhrasalVerb> includedPhrasalVerbs;

//    @OneToMany
//    private List<Document> documents;
//
    @OneToOne
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    private Configuration config = new Configuration();
}

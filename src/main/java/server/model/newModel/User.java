package server.model.newModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import server.model.AuditableEntityObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Data
public class User extends AuditableEntityObject {

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
    private Set<RootWord> excludedWords;

    @OneToMany
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name="user_includedWords")
    private Set<RootWord> includedWords;

//    @OneToMany
//    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
//    @JoinTable(name="user_excludedPhrasalVerbs")
//    private Set<PhrasalVerb> excludedPhrasalVerbs;
//
//    @OneToMany
//    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
//    @JoinTable(name="user_includedPhrasalVerbs")
//    private Set<PhrasalVerb> includedPhrasalVerbs;

//    @OneToMany
//    private List<Document> documents;
//
    @OneToOne
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    private Configuration config = new Configuration();

    public void addIncludedWord(RootWord rootWord){
        if(rootWord.getId() != null){
            getIncludedWords().add(rootWord);
        }
    }

    public void removeIncludedWord(RootWord rootWord){
       getIncludedWords().remove(rootWord);
    }

    public void addExcludedWord(RootWord rootWord){
        if(rootWord.getId() != null){
            getExcludedWords().add(rootWord);
        }
    }

    public void removeExcludedWord(RootWord rootWord){
       getExcludedWords().remove(rootWord);
    }

//    public void addIncludedPhrasalVerb(PhrasalVerb phrasalVerb){
//        if(phrasalVerb.getId() != null){
//            getIncludedPhrasalVerbs().add(phrasalVerb);
//        }
//    }
//
//    public void removeIncludedPhrasalVerb(PhrasalVerb phrasalVerb){
//       getIncludedPhrasalVerbs().remove(phrasalVerb);
//    }
//
//    public void addExcludedPhrasalVerb(PhrasalVerb phrasalVerb){
//        if(phrasalVerb.getId() != null){
//            getExcludedPhrasalVerbs().add(phrasalVerb);
//        }
//    }
//
//    public void removeExcludedPhrasalVerb(PhrasalVerb phrasalVerb){
//       getExcludedPhrasalVerbs().remove(phrasalVerb);
//    }

}

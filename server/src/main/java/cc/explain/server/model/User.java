package cc.explain.server.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "user")
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
    private String role;

    @Setter
    @Getter
    private Boolean enabled = false;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    private Set<UserExcludeWord> excludedWords = new HashSet<UserExcludeWord>();



    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    private Set<UserIncludeWord> includedWords = new HashSet<UserIncludeWord>();

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
            getIncludedWords().add(new UserIncludeWord(rootWord));
        }
    }

    public void removeIncludedWord(RootWord rootWord){
       getIncludedWords().remove(new UserExcludeWord(rootWord));
    }

    public void addExcludedWord(RootWord rootWord){
        if(rootWord.getId() != null){
            getExcludedWords().add(new UserExcludeWord(rootWord));
        }
    }

    public void removeExcludedWord(RootWord rootWord){
       getExcludedWords().remove(new UserExcludeWord(rootWord));
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

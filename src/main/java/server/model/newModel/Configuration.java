package server.model.newModel;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import server.model.EntityObject;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:53
 */
@Entity
@Data
public class Configuration extends EntityObject{

    @Min(0)
    @Max(100)
    private int min = 5;

    @Min(0)
    @Max(100)
    private int max = 90;

    @NotBlank
    private String textTemplate="(@@TRANSLATED_TEXT@@)";

    @NotBlank
    private String subtitleTemplate="<font color='yellow'>(@@TRANSLATED_TEXT@@)</font>";

}

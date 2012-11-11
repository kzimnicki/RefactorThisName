package cc.explain.server.model;

import cc.explain.server.model.AuditableEntityObject;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:53
 */
@Entity
@Data
@Table(name = "configuration")
public class Configuration extends AuditableEntityObject{

    @Min(0)
    @Max(100)
    private int min = 5;

    @Min(0)
    @Max(100)
    private int max = 89;

    @NotBlank
    private String textTemplate="(@@TRANSLATED_TEXT@@)";

    @NotBlank
    private String subtitleTemplate="<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>";

}

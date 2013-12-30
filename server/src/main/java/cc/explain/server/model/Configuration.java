package cc.explain.server.model;

import cc.explain.server.subtitle.SubtitleProcessor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * User: kzimnick
 * Date: 30.05.12
 * Time: 20:53
 */
@Entity
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

    @NotBlank
    private String phrasalVerbTemplate="<font color=\"red\">@@TRANSLATED_TEXT@@</font>";

    private boolean isPhrasalVerbAdded = false;

    private Language language = Language.en;

    @Enumerated(EnumType.STRING)
    private SubtitleProcessor subtitleProcessor = SubtitleProcessor.IN_TEXT;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getTextTemplate() {
        return textTemplate;
    }

    public void setTextTemplate(String textTemplate) {
        this.textTemplate = textTemplate;
    }

    public String getSubtitleTemplate() {
        return subtitleTemplate;
    }

    public void setSubtitleTemplate(String subtitleTemplate) {
        this.subtitleTemplate = subtitleTemplate;
    }

    public String getPhrasalVerbTemplate() {
        return phrasalVerbTemplate;
    }

    public void setPhrasalVerbTemplate(String phrasalVerbTemplate) {
        this.phrasalVerbTemplate = phrasalVerbTemplate;
    }

    public boolean isPhrasalVerbAdded() {
        return isPhrasalVerbAdded;
    }

    public void setPhrasalVerbAdded(boolean isPhrasalVerbAdded) {
        this.isPhrasalVerbAdded = isPhrasalVerbAdded;
    }

    public SubtitleProcessor getSubtitleProcessor() {
        return subtitleProcessor;
    }

    public void setSubtitleProcessor(SubtitleProcessor subtitleProcessor) {
        this.subtitleProcessor = subtitleProcessor;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

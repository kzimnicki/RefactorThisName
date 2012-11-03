package cc.explain.server.api;

import lombok.Data;

@Data
public class WordDetails {

    private Integer frequency;

    public WordDetails(Integer frequency) {
        this.frequency = frequency;
    }
}

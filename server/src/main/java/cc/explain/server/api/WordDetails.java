package cc.explain.server.api;

import lombok.Data;

public class WordDetails {

    private Integer frequency;

    public WordDetails(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}

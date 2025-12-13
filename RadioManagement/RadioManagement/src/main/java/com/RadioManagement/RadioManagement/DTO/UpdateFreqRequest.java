package com.RadioManagement.RadioManagement.DTO;

public class UpdateFreqRequest {

    private Integer id;
    private Double newFrequency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNewFrequency() {
        return newFrequency;
    }

    public void setNewFrequency(Double newFrequency) {
        this.newFrequency = newFrequency;
    }
}

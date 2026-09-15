package com.RadioManagement.RadioManagement.DTO;

public class UpdateFreqRequest {

    private Long id;
    private Double newFrequency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNewFrequency() {
        return newFrequency;
    }

    public void setNewFrequency(Double newFrequency) {
        this.newFrequency = newFrequency;
    }
}

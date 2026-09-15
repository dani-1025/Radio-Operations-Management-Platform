package com.RadioManagement.RadioManagement.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateFreqRequest {

    @NotNull(message = "Channel ID is required")
    @Positive(message = "Channel ID must be greater than zero")
    private Long id;

    @NotNull(message = "New frequency is required")
    @Positive(message = "Frequency must be greater than zero")
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
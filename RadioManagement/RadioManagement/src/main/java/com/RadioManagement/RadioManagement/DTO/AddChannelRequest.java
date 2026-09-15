package com.RadioManagement.RadioManagement.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddChannelRequest {

    @NotBlank(message = "Channel name is required")
    private String name;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Frequency is required")
    @Positive(message = "Frequency must be greater than zero")
    private Double frequency;

    public AddChannelRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }
}
package com.RadioManagement.RadioManagement.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddProgramRequest {

    @NotBlank(message = "Program title is required")
    private String title;

    @NotBlank(message = "Program description is required")
    private String description;

    @NotNull(message = "Channel ID is required")
    private Long channelId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
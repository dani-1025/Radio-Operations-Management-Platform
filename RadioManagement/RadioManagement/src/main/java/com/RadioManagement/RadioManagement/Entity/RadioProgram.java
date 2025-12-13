package com.RadioManagement.RadioManagement.Entity;

import jakarta.persistence.*;

@Entity
public class RadioProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private RadioChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public RadioChannel getChannel() {
        return channel;
    }

    public void setChannel(RadioChannel channel) {
        this.channel = channel;
    }

    public RadioProgram(Long id, String title, String description, RadioChannel channel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.channel = channel;
    }

    public RadioProgram() {
    }
}

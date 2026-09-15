package com.RadioManagement.RadioManagement.Exception;

public class ChannelNotFoundException extends RuntimeException {

    public ChannelNotFoundException(String message) {
        super(message);
    }
}
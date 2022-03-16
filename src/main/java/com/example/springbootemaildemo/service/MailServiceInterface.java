package com.example.springbootemaildemo.service;

import com.example.springbootemaildemo.pyload.ApiResponse;

public interface MailServiceInterface {
    public ApiResponse sentText(String sendToEmail);
    public ApiResponse sentHtml(String sendToEmail);
    public ApiResponse sentFile(String sendToEmail);
}

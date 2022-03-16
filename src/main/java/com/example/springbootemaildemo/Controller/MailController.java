package com.example.springbootemaildemo.Controller;

import com.example.springbootemaildemo.pyload.ApiResponse;
import com.example.springbootemaildemo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/sendText")
    public HttpEntity<?> sendText(@RequestParam String email){
        ApiResponse apiResponse = mailService.sentText(email);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/sendHtml")
    public HttpEntity<?> sendHtml(@RequestParam String email){
        ApiResponse apiResponse = mailService.sentHtml(email);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/activate")
    public HttpEntity<?> activateFromMail(@RequestParam String code){
        return ResponseEntity.ok(code);
    }

    @GetMapping("/sendFile")
    public HttpEntity<?> sendFile(@RequestParam String email){
        ApiResponse apiResponse = mailService.sentFile(email);
        return ResponseEntity.ok().body(apiResponse);
    }
}

package com.example.springbootemaildemo.service;

import com.example.springbootemaildemo.pyload.ApiResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class MailService implements MailServiceInterface{
    @Autowired
    JavaMailSender sender;

    @Autowired
    private Configuration configuration;


    @Override
    public ApiResponse sentText(String sendToEmail) {
       try{
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
           simpleMailMessage.setText("Bu spring boot da test qilib jonatilgan");
           simpleMailMessage.setTo(sendToEmail);
           simpleMailMessage.setSubject("salom hayr!!!");
           sender.send(simpleMailMessage);
           return new ApiResponse("Okay...",true);
       }catch (Exception e){
           return new ApiResponse("Error",false);
       }
    }

    @Override
    public ApiResponse sentHtml(String sendToEmail) {
        try{

            HashMap<String, Object> model = new HashMap<>();
            model.put("email","ixtiyorisakjonov@gmail.com");
            model.put("fullName","Iskjonov Ixtiyor");
            model.put("code","123456");
            model.put("phoneNumber","+998995346398");
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            helper.setTo(sendToEmail);
            helper.setSubject("Sending Email From Myself");
            helper.setText(html,true);
            sender.send(mimeMessage);

            return new ApiResponse("Okay...",true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }
    }

    @Override
    public ApiResponse sentFile(String sendToEmail) {
        try{
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setTo(sendToEmail);
            helper.setSubject("Spring boot Applection");
            helper.setText("Asalomu alaykum. Bu spring boot file jonatildi");
            String name = "onajon.jpg";
            File file = ResourceUtils.getFile("src/main/resources/static/appFile"+name);
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            ByteArrayDataSource arrayDataSource = new ByteArrayDataSource(bytes,"application/octet-stream");
            helper.addAttachment(name,arrayDataSource);
            Thread thread = new Thread(){
                public void run(){
                    sender.send(mimeMessage);
                }
            };
            thread.start();
            return new ApiResponse("Okay...",true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }
    }
}

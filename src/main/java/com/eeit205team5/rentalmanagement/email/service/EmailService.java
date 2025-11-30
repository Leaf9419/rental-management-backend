package com.eeit205team5.rentalmanagement.email.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.eeit205team5.rentalmanagement.common.exception.BusinessException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    // Spring用來寄Email的功能入口
    private final JavaMailSender mailSender;

    // Thymeleaf引擎
    private final SpringTemplateEngine templateEngine;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.from-name}")
    private String fromName;

    /**
     * 發送HTML Email
     * 
     * @param to
     * @param subject
     * @param templateName
     * @param variables
     */
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            // createMimeMessage():建立MIME Email物件，可支援HTML、附件、多媒體的Email
            MimeMessage message = mailSender.createMimeMessage();
            // 建立支援HTML + 附件 + UTF-8的Email編輯器
            // message:告訴Helper要操作哪一封信件
            // true:代表multipart，支援HTML和附件
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = generateHtmlContent(templateName, variables);

            // fromName:可選
            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            // true:支援HTML渲染
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email發送成功: {}", to);
        } catch (MessagingException e) {
            // 這樣內部log可能會重複打印，但想保留to
            log.error("Email發送失敗: {}", to, e);
            throw new BusinessException("Email發送失敗", e);
        } catch (Exception e) {
            log.error("Email發送異常: {}", to, e);
            throw new RuntimeException("Email發送異常", e);
        }
    }

    // 生成HTML，將變數值填進HTML模板
    private String generateHtmlContent(String templateName, Map<String, Object> variables) {
        // Thymeleaf用來傳遞模板變數的物件，負責解析模板，將變數代入，生成最終文字
        Context context = new Context();
        context.setVariables(variables);

        // process():將模板檔案讀取出來，替換掉Context物件裡的變數，回傳String
        return templateEngine.process(templateName, context);
    }

    /**
     * 封裝發送驗證碼Email方法
     * 
     * @param to
     * @param code
     */
    public void sendVerificationCode(String to, String code) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("code", code);
        
        sendHtmlEmail(to, "【XXX租屋平台】Email 驗證碼", "email/verification", vars);
    }
}
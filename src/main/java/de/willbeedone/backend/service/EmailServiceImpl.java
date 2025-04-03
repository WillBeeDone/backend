package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.service.interfaces.ConfirmationService;
import de.willbeedone.backend.service.interfaces.EmailService;
import de.willbeedone.backend.service.interfaces.ResetService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;
    private final Configuration mailConfig;
    private final ConfirmationService confirmationService;
    private final ResetService resetService;

    @Value("${mail.from}")
    private String fromEmail;

    @Value("${base.url}")
    private String baseUrl;

    public EmailServiceImpl(JavaMailSender sender, Configuration mailConfig, ConfirmationService confirmationService, ResetService resetService) {
        this.sender = sender;
        this.mailConfig = mailConfig;
        this.confirmationService = confirmationService;
        this.resetService = resetService;

        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(new ClassTemplateLoader(EmailServiceImpl.class, "/mail/"));
    }

    @Override
    public void sendConfirmationEmail(User user) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateConfirmationEmail(user);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Registration");
            helper.setText(text, true);

            sender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendResetPasswordEmail(User user) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateResetPasswordEmail(user);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Reset password");
            helper.setText(text, true);

            sender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateConfirmationEmail(User user) {
        try {
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");
            String code = confirmationService.generateConfirmationCode(user);

            Map<String, Object> params = new HashMap<>();
            params.put("name", user.getEmail());
            params.put("link", baseUrl + "/confirm-email/" + code);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateResetPasswordEmail(User user) {
        try {
            Template template = mailConfig.getTemplate("reset_pass_mail.ftlh");
            String code = resetService.generateResetPasswordCode(user);

            Map<String, Object> params = new HashMap<>();
            params.put("name", user.getEmail());
            params.put("link", baseUrl + "/password-recovery-form/" + code); 

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

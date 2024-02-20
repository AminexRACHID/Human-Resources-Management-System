package miaad.sgrh.employeemanagement.serviceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;
import miaad.sgrh.employeemanagement.service.VerificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@AllArgsConstructor
@Service
public class EmailServiceImpl {
    private VerificationService verificationService;
    private TemplateEngine templateEngine;
    private JavaMailSender javaMailSender;

    public void sendHtmlMail(Account account) throws MessagingException {
        Verification verification = verificationService.findByAccount(account);

        if(verification != null){
            String token = verification.getToken();
            Context context = new Context();
            context.setVariable("title", "Verify your email address");
            context.setVariable("link", "http://localhost:8021/api/account/activation?token="+token);
            // create an HTML template and pass it
            String body = templateEngine.process("verification", context);

            // Send The verfication email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(account.getLogin());
            helper.setSubject("email address verification");
            helper.setText(body,true);
            javaMailSender.send(message);
        }
    }
}

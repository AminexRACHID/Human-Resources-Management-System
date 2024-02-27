package miaad.sgrh.employeemanagement.serviceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.service.VerificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@AllArgsConstructor
@Service
public class EmailServiceImpl {
    private VerificationService verificationService;
    private TemplateEngine templateEngine;
    private JavaMailSender javaMailSender;
    private AccountRepository accountRepository;

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

    public void sendHtmlMailForPasswordRecovering(String email) throws MessagingException {
        Account account1 = accountRepository.findAccountByLogin(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if(account1 != null){
            String pass = generateRandomPassword(8);
            account1.setPassword(passwordEncoder.encode(pass));
            accountRepository.save(account1);
            Context context = new Context();
            context.setVariable("title", "Password Recovery for Your Account");
            context.setVariable("link", pass);
            // create an HTML template and pass it
            String body = templateEngine.process("passwordForgot", context);

            // Send The verfication email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(email);
            helper.setSubject("Password Recovery for Your Account");
            helper.setText(body,true);
            javaMailSender.send(message);
        }
    }

    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }
}

package miaad.sgrh.employeemanagement.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;
import miaad.sgrh.employeemanagement.repository.VerificationRepository;
import miaad.sgrh.employeemanagement.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@AllArgsConstructor
@Service
public class VerificationServiceImpl implements VerificationService {
    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public Verification findByToken(String token) {
        return verificationRepository.findByToken(token);
    }

    @Override
    public Verification findByAccount(Account account) {
        return verificationRepository.findByAccount(account);
    }

    @Override
    public void save(Account account, String token) {
        Verification verification = new Verification();
        verification.setAccount(account);
        verification.setToken(token);
        verification.setExpiryDate(calculateExpiryDate(60));
        verificationRepository.save(verification);
    }

    private Timestamp calculateExpiryDate(int experyTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, experyTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}

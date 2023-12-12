package com.employee.service_employee.Service;

import com.employee.service_employee.Dto.PasswordDTO;
import com.employee.service_employee.Exception.EmployeeNotFoundException;
import com.employee.service_employee.Feign.AccountFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class EmployeeService {

    private AccountFeignClient accountFeignClient;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    public static boolean isPasswordValid(String password) {
        // Minimum password length
        int minLength = 8;

        // Check if the password is at least the minimum length
        if (password.length() < minLength) {
            return false;
        }

        // Check if the password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check if the password contains at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check if the password contains at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Check if the password contains at least one special character
        if (!password.matches(".*[@#$%^&+=!].*")) {
            return false;
        }

        return true;
    }


    public void changePassword(String email, PasswordDTO passwordDTO) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    try {
        String oldPassword = accountFeignClient.getPassword(email);
        // Find the employee by I
        String currentPassword = passwordEncoder.encode(passwordDTO.getOldPassword());
        //print the current password
        System.out.println("--------------------------------------Current not ENCODED : "+passwordDTO.getOldPassword());
        System.out.println("--------------------------------------Current ENCODED : "+currentPassword);
        System.out.println("--------------------------------------OLd  : "+oldPassword);
        //Compare the provided currentPassword with the stored password of the employee
        if (passwordEncoder.matches(passwordDTO.getOldPassword(),oldPassword)) {
            // Validate the new password and confirm new password
            if (isPasswordValid(passwordDTO.getNewPassword())) {
                // Encode the new password
                String newPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
                System.out.println(newPassword);
                // Update the password
                //check the problem why accountFeignClient.changePassword(email, newPassword); is not working
                try {
                    accountFeignClient.changePassword(email, newPassword);
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    throw new EmployeeNotFoundException("New password is valid ");}

            }else {
                System.out.println("New password is not valid");
            }

        } else {
            throw new EmployeeNotFoundException("The Current Password is not correct");
        }
    }catch (Exception ex) {
        ex.printStackTrace(); // Log the full stack trace
        throw new EmployeeNotFoundException("something went wrong");
    }

    }
}
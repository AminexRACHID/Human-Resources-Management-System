package miaad.sgrh.employeemanagement.service;

import miaad.sgrh.employeemanagement.dto.UserDto;
import miaad.sgrh.employeemanagement.entity.Account;

public interface AccountService {
    Account createAccount(UserDto userDto);
}

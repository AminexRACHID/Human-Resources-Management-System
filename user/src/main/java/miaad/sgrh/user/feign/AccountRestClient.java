package miaad.sgrh.user.feign;

import miaad.sgrh.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("EMPLOYEE-MANAGEMENT")
public interface AccountRestClient {
    @PostMapping("/api/account")
    ResponseEntity<?> createAccount(@RequestBody UserDto userDto);
}

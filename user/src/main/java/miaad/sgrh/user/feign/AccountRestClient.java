package miaad.sgrh.user.feign;

import miaad.sgrh.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("EMPLOYEE-MANAGEMENT")
public interface AccountRestClient {
    @PostMapping("/api/account/create")
    ResponseEntity<?> createAccount(@RequestBody UserDto userDto);

    @DeleteMapping("/api/account/{email}")
    ResponseEntity<?> deleteAccount(@PathVariable("email") String email);
}

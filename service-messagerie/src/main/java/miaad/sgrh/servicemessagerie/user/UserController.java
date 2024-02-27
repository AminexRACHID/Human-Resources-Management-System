package miaad.sgrh.servicemessagerie.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:4200")
    @MessageMapping("/user.addUser")
    @SendTo("/app/public")
    public User addUser(
            @Payload User user
    ) {
        userService.saveUser(user);
        return user;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("allUsers")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @MessageMapping("/user.disconnectUser")
    @SendTo("/app/public")
    public User disconnectUser(
            @Payload User user
    ) {
        userService.disconnect(user);
        return user;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/user/updateStatus")
    public ResponseEntity<String> updateUserStatus(
            @RequestParam String nickName,
            @RequestParam Status newStatus
    ) {
        userService.updateUserStatus(nickName, newStatus);
        return ResponseEntity.ok("User status updated successfully.");
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}

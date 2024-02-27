package miaad.sgrh.servicemessagerie.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void saveUser(User user) {
        var storedUser = repository.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.ONLINE);
            repository.save(storedUser);

        }else{
            user.setStatus(Status.ONLINE);
            repository.save(user);}
    }

    public void disconnect(User user) {
        var storedUser = repository.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }



    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

    public List<User> findAllUsers(){
        return  repository.findAll();
    }

    public void updateUserStatus(String nickName, Status newStatus) {
        var storedUser = repository.findById(nickName).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(newStatus);
            repository.save(storedUser);
        }
    }
}

package pw.react.backend.reactbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class UserService {
    private UserRepository UserRepository;

    @Autowired
    public UserService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    public User findByLogin(String login) {
        return UserRepository.findByLogin(login);
    }

    public User findById(int id) {
        return UserRepository.findById(id);
    }

    public boolean exists(User user) {
        User res = UserRepository.findByLogin(user.getLogin());
        boolean ret = res !=null;
        return ret;
    }

    public User save(User user) {
        return UserRepository.save(user);
    }

    public void delete(User user) {
        UserRepository.delete(user);
    }
}

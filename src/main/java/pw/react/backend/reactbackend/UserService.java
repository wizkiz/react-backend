package pw.react.backend.reactbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository UserRepository;

    @Autowired
    public UserService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    public boolean exists(String login) {
        List<User> res = UserRepository.findByLogin(login);
        boolean ret = (res !=null && !res.isEmpty());
        return ret;
    }

//    public User updateUser(User user) {
//        User existingUser = UserRepository.findById(user.getId());
//        if (existingUser!=null) {
//            return UserRepository.save(user);
//        }
//        throw new ResourceNotFoundException(String.format("User with id [%s] not found.", user.getId()));
//    }
}

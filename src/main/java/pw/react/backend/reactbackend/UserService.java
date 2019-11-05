package pw.react.backend.reactbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.*;

import javax.jws.soap.SOAPBinding;
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

    public User updateUser(User user) {
        Optional<User> existingUser = Optional.ofNullable(UserRepository.findById(user.getId()));
        if (existingUser.isPresent()) {
            return UserRepository.save(user);
        }
        throw new ResourceNotFoundException(String.format("User with id [%s] not found.", user.getId()));
    }

    List<User> findByLogin(String login) {
        List<User> users = UserRepository.findByLogin(login);
        if (users.isEmpty())
            throw new ResourceNotFoundException("Login: " + login + " not found");
        return users;
    }
    User findById(long id) {
        User user = UserRepository.findById(id);
        if (user == null)
            throw new ResourceNotFoundException("Id: " + id + " not found");
        return user;
    }
}

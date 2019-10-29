package pw.react.backend.reactbackend;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    UserRepository repository;

    @Autowired
    public UserController (UserService userService){
        this.userService=userService;
    }

    @GetMapping("/createme")
    public String createMe(){
        //User tmp = new User("wizkiz", "Szymon", "Majorek", true);
        repository.save(new User("wizkiz", "Szymon", "Majorek", LocalDate.of(1998,01,23), true));

        return "I am created";
    }

    @GetMapping("/creatfew")
    public String createFew(){
        //User tmp = new User("wizkiz", "Szymon", "Majorek", true);
        repository.saveAll(Arrays.asList(new User("leagueofgarfield","mateusz","pruk",LocalDate.of(1998,01,01), true),
                new User("bestlutek","piotr","tomala",LocalDate.of(1998,07,06),false)));
        return "Two users created";
    }


    //search for user with given login
    @GetMapping("/user/{login}")
    public String getUsers(@PathVariable String login) {
        User result = null;
        if (login != null && login.length() > 0)
            result = userService.findByLogin(login);
        if(result == null) {
            return "User not found";
        } else {
            return result.toString();
        }
    }



    //check if user with given login exists
    @RequestMapping("/existslogin/{login}")
    public String checkIfExists(@PathVariable String login){
        User result = null;
        if (login != null && login.length() > 0)
            result = userService.findByLogin(login);
        if(result == null) {
            return "Usr not yet created";
        }
        else {
            return "User already exists";
        }
    }

    //add new user
    @PostMapping("/user/")
    public String createUser(@RequestBody User user) {
        if (userService.exists(user)) {
            return "User already exists";
        }
        User result = userService.save(user);
        return "Created new user: " + result.toString();
    }



    //note:
    //I haven't really checked those thoroughly as it's 23:58 so I kinda have 1 minute to commit and push it
}

package pw.react.backend.reactbackend;

import org.apache.tomcat.jni.Local;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    @Autowired
    UserRepository repository;

    @Autowired
    public UserController (UserService userService){

        this.userService=userService;
    }

//    @GetMapping("/createme")
//    public String createMe(){
//
//        repository.save(new User("wizkiz", "Szymon", "Majorek", LocalDate.of(1998,01,23), true));
//        return "I am created";
//    }
//
//    @GetMapping("/creatfew")
//    public String createFew(){
//
//        repository.saveAll(Arrays.asList(new User("leagueofgarfield","mateusz","pruk",LocalDate.of(1998,01,01), true),
//                new User("bestlutek","piotr","tomala",LocalDate.of(1998,07,06),false)));
//        return "Two users created";
//    }

    //view all users as default page sort of
    @GetMapping(path = "")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok().body(repository.findAll());
    }

    //search for user with given login
    @GetMapping("/login/{login}")
    public ResponseEntity<List<User>> getUsers(@PathVariable String login) {

        return ResponseEntity.ok().body(repository.findByLogin(login));
    }

    //check if user with given login exists
    @RequestMapping("/existslogin/{login}")
    public boolean checkIfExists(@PathVariable String login){

        return userService.exists(login);
    }

    //add new user
    @PostMapping(path = "")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {

        return ResponseEntity.ok().body(repository.save(user));
    }

    //delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {

        User tmp = repository.findById(id);
        if(tmp!=null){
            repository.delete(repository.findById(id));
        }
        return ResponseEntity.ok(tmp);
    }

    //update user by id
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User newUser) {

        if(repository.findById(id)==null){
            return ResponseEntity.ok().body(repository.save(newUser));
        }
        return ResponseEntity.ok().body(userService.updateUser(newUser));
    }

    //find user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable long id){

        return ResponseEntity.ok().body(repository.findById(id));
    }

    //
    @GetMapping(
            path = "/img",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/xd.jpg");
        byte [] res = StreamUtils.copyToByteArray(in);
        return res;
    }

    @PatchMapping(path = "/{id}/upload",
        produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] uploadFileToUser(@RequestParam("file") MultipartFile file, @PathVariable long id) throws IOException {
        byte [] res = null;
        User user = repository.findById(id);
        if(user!=null) {

            res=file.getBytes();
            user.setProfilePicture(res);
            userService.updateUser(user);
        }
        return res;
    }
    @GetMapping(path = "/{id}/profilepic",
        produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] showPorfilePic(@PathVariable long id){
        return repository.findById(id).getProfilePicture();
    }
}

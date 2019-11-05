package pw.react.backend.reactbackend;

import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    @Column(name="login")
    private String login;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "profilepicture")
    private byte[] profilePicture;


    public User(String wizkiz, String szymon, String majorek, LocalDate dob, boolean b) {
        this.login=wizkiz;
        this.firstName=szymon;
        this.lastName=majorek;
        this.active=b;
        this.dateOfBirth = dob;
    }

    public User () {}

    public String getLogin() {
        return this.login;
    }
    public String getFirstName() {return  this.firstName;}
    public String getLastName() {return this.lastName; }
    public boolean getActive() {return this.active; }
    public LocalDate getDateOfBirth() {return this.dateOfBirth; }
    public long getId() {return this.id; }

    public void setId(long id) {
        this.id=id;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setLogin(String login) {
        this.login=login;
    }

}

//generated by the hibernate id, login, first name, last name, date of birth, an indicator saying if a user is active or not.
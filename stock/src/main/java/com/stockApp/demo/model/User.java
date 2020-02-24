package com.stockApp.demo.model;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    private String name;

    //@Column(columnDefinition="LONGVARCHAR")
    private String portrait;
    private String businessTitle;
    private String aboutMe;

    @Column(name = "PASSWORD", nullable = false)
    private String hashcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setHashcode(String hashcode) {this.hashcode = hashcode;}

    public String getHashcode() {return hashcode;}




    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                ", businessTitle='" + businessTitle + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", hashcode='" + hashcode + '\'' +
                '}';
    }


}

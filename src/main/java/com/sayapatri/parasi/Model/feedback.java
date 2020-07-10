package com.sayapatri.parasi.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="feedback")
public class feedback {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @org.hibernate.validator.constraints.Email(message = "Please provide valid email")
    @NotEmpty(message = "Please provide a email")
    private String Email;

    @Column(name="message")
    private String message;

    @NotNull
    @NotBlank(message="Please enter your phone number")
    @Pattern(regexp = "(\\+61|0)[0-9]{9}")
    @Column(name="phone")
    private String phone;




    public feedback() {
    }
    public feedback( String Email,String message,String phone) {
        this.Email=Email;
        this.message=message;
        this.phone=phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

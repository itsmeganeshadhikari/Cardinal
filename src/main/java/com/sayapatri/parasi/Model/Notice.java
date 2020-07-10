package com.sayapatri.parasi.Model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



    @NotBlank(message="Title is mandatory")
    private String title;



    @NotBlank(message="Message is mandatory")
    private String message;

   @Temporal(TemporalType.DATE)
   private Date createdDate=new Date(System.currentTimeMillis());

    public Notice()
    {
    }
    public Notice(String title,String message)
    {
        this.title=title;
        this.message=message;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

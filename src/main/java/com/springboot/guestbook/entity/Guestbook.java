package com.springboot.guestbook.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Guestbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String content;

}

package com.home.eschool.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Date-2/11/2024
 * By Sardor Tokhirov
 * Time-11:32 AM (GMT+5)
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edu_centers")
public class EduCenter extends BaseEntity{


    private String title;
    private String address;


    private String contacts;

    private String location;

    private String links;
}

package com.home.eschool.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classes extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Languages lang;

    @OneToOne
    private States state;

    @OneToOne
    private Teachers curator;
}

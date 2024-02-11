package com.home.eschool.models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsPayloadDetails extends StudentsPayload {

    private String address;
    private FilesPayload avatar;
    private Object mother;
    private Object father;
    private Object birthInfo;
    private BigDecimal monthlyPayment;
    private Object additionalInfo;
    private Object discount;
    private String sex;
    private FilesPayload file;

    public StudentsPayloadDetails(UUID id,
                                  String firstName,
                                  String lastName,
                                  String sureName,
                                  String dateOfBirth,
                                  String phoneNumber,
                                  ClassesPayload classes,
                                  String address,
                                  FilesPayload avatar,
                                  Object mother,
                                  Object father,
                                  Object birthInfo,
                                  BigDecimal monthlyPayment,
                                  Object additionalInfo,
                                  String sex,
                                  FilesPayload file,
                                  Object discount) {
        super(id, firstName, lastName, sureName, dateOfBirth, phoneNumber, classes);
        this.address = address;
        this.avatar = avatar;
        this.mother = mother;
        this.father = father;
        this.birthInfo = birthInfo;
        this.monthlyPayment = monthlyPayment;
        this.additionalInfo = additionalInfo;
        this.sex = sex;
        this.file = file;
        this.discount = discount;
    }


}

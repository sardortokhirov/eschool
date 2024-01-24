package com.home.eschool.models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeListPayload {

    private ReferencePayload student;
    private List<Grade> grades;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Grade {
        private Integer gradeValue;
        private String gradeDate;
        private String gradeReason;
    }
}

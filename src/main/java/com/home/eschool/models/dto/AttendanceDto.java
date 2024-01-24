package com.home.eschool.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {

    @NotNull
    private UUID classId;

    private UUID subjectId;

    @NotNull
    private UUID studentId;
    @NotNull
    private String attendanceDate;
    @NotNull
    private boolean attendanceIsReasonable;
    @NotNull
    private String attendanceReason;
}

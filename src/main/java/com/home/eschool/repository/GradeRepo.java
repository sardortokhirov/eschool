package com.home.eschool.repository;

import com.home.eschool.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface GradeRepo extends JpaRepository<Grade, UUID> {

    @Query("select t from Grade t " +
            "where t.students.id=?1 and t.subjects.id=?2 and t.gradeDate=?3")
    Grade getGrade(UUID studentId, UUID subjectId, Date date);

    @Query(nativeQuery = true,
            value = "select t.* from grade t \n" +
                    "where t.student_id=?1 and t.subject_id=?2 and to_char(t.grade_date, 'yyyy-mm') = ?3")
    List<Grade> getGrade(UUID studentId, UUID subjectId, String date);
}

package com.home.eschool.repository;

import com.home.eschool.entity.EduCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Date-2/11/2024
 * By Sardor Tokhirov
 * Time-11:37 AM (GMT+5)
 */
@Repository
public interface EduCenterRepo extends JpaRepository<EduCenter, UUID> {
    @Query("SELECT e FROM EduCenter e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<EduCenter> findByTitleContainingIgnoreCase(@Param("search") String search);

    List<EduCenter> findAll();

}

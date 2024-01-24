package com.home.eschool.repository;

import com.home.eschool.entity.States;
import com.home.eschool.entity.Teachers;
import com.home.eschool.entity.enums.StateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeachersRepo extends JpaRepository<Teachers, UUID> {

    @Query("select t from Teachers t where t.state = ?1")
    Page<Teachers> listOfActiveTeachers(Pageable pageable, States state);

    @Query("select t from Teachers t where t.state = ?1 and " +
            "( lower(t.firstName) like lower(concat('%', ?2,'%')) or " +
            "  lower(t.lastName) like lower(concat('%', ?2,'%'))  or " +
            "  lower(t.sureName) like lower(concat('%', ?2,'%')))")
    Page<Teachers> listOfActiveTeachersWithSearch(Pageable pageable, States state, String search);

    List<Teachers> findAllByStateLabel(StateEnum stateEnum);

    Optional<Teachers> findByInn(String inn);

    Optional<Teachers> findByPassportNumber(String passportNumber);

    Optional<Teachers> findByPhoneNumber(String phoneNumber);

    Optional<Teachers> findByProfileId(UUID userId);

}

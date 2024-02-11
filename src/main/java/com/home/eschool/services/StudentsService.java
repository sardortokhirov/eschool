package com.home.eschool.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.home.eschool.entity.States;
import com.home.eschool.entity.Students;
import com.home.eschool.entity.enums.StateEnum;
import com.home.eschool.models.dto.StudentsDto;
import com.home.eschool.models.payload.*;
import com.home.eschool.repository.StudentsRepo;
import com.home.eschool.services.interfaces.CrudInterface;
import com.home.eschool.utils.Settings;
import com.home.eschool.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class StudentsService implements CrudInterface<StudentsDto, StudentsPayload> {

    private final StudentsRepo studentsRepo;
    private final FilesService filesService;
    private final StudentClassesService studentClassesService;
    private final StateService stateService;

    public StudentsService(StudentsRepo studentsRepo,
                           FilesService filesService,
                           StudentClassesService studentClassesService,
                           StateService stateService) {
        this.studentsRepo = studentsRepo;
        this.filesService = filesService;
        this.studentClassesService = studentClassesService;
        this.stateService = stateService;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void create(StudentsDto studentsDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Students students = new Students();
        students.setId(UUID.randomUUID());
        students.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        students.setCreateUser(Settings.getCurrentUser());
        students.setFirstName(studentsDto.getFirstName());
        students.setLastName(studentsDto.getLastName());
        students.setSureName(studentsDto.getSureName());
        students.setAvatar_id(studentsDto.getAvatar_id());
        students.setAddress(studentsDto.getAddress());
        students.setPhoneNumber(studentsDto.getPhoneNumber());
        students.setMonthlyPayment(studentsDto.getMonthlyPayment());
        students.setState(stateService.getStateByLabel(StateEnum.ACTIVE));
        students.setSex(studentsDto.getSex());
        students.setFile_id(studentsDto.getFile_id());

        try {
            students.setDateOfBirth(new Date(simpleDateFormat.parse(studentsDto.getDateOfBirth()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (studentsDto.getMother() != null) {
            students.setMother(Utils.convertToString(studentsDto.getMother()));
        }
        if (studentsDto.getFather() != null) {
            students.setFather(Utils.convertToString(studentsDto.getFather()));
        }
        if (studentsDto.getBirthInfo() != null) {
            students.setBirthInfo(Utils.convertToString(studentsDto.getBirthInfo()));
        }
        if (studentsDto.getAdditionalInfo() != null) {
            students.setAdditionalInfo(Utils.convertToString(studentsDto.getAdditionalInfo()));
        }

        studentsRepo.save(students);
        studentClassesService.create(students, studentsDto.getClassId());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(StudentsDto studentsDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Students students = studentsRepo.findById(studentsDto.getId()).orElse(null);
        if (students == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect Teacher Id");
        }

        students.setChangeDate(Timestamp.valueOf(LocalDateTime.now()));
        students.setChangeUser(Settings.getCurrentUser());
        students.setFirstName(studentsDto.getFirstName());
        students.setLastName(studentsDto.getLastName());
        students.setSureName(studentsDto.getSureName());
        students.setAvatar_id(studentsDto.getAvatar_id());
        students.setAddress(studentsDto.getAddress());
        students.setPhoneNumber(studentsDto.getPhoneNumber());
        students.setMonthlyPayment(studentsDto.getMonthlyPayment());
        students.setSex(studentsDto.getSex());
        students.setFile_id(studentsDto.getFile_id());
        students.setAdditionalInfo(studentsDto.getAdditionalInfo().asText());
        students.setDiscount(studentsDto.getDiscount().asText());

        try {
            students.setDateOfBirth(new Date(simpleDateFormat.parse(studentsDto.getDateOfBirth()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (studentsDto.getMother() != null) {
            students.setMother(Utils.convertToString(studentsDto.getMother()));
        }
        if (studentsDto.getFather() != null) {
            students.setFather(Utils.convertToString(studentsDto.getFather()));
        }
        if (studentsDto.getBirthInfo() != null) {
            students.setBirthInfo(Utils.convertToString(studentsDto.getBirthInfo()));
        }
        if (studentsDto.getAdditionalInfo() != null) {
            students.setAdditionalInfo(Utils.convertToString(studentsDto.getAdditionalInfo()));
        }

        studentsRepo.save(students);
        studentClassesService.update(students, studentsDto.getClassId());
    }

    @Override
    public PageablePayload getAll(int page, String search) {

        int size = 10;
        List<StudentsPayloadForList> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        States states = stateService.getStateByLabel(StateEnum.ACTIVE);

        Page<Students> studentsPage;

        if (!Utils.isEmpty(search)) {

            studentsPage = studentClassesService.getStudentsByClassName(PageRequest.of(page, size),
                    states, search);
        } else {
            studentsPage = studentsRepo.listOfActiveStudents(
                    PageRequest.of(page, size, Sort.by("lastName")), states);
        }

        studentsPage.forEach(t -> list.add(
                new StudentsPayloadForList(
                        t.getId(),
                        t.getFirstName(),
                        t.getLastName(),
                        t.getSureName(),
                        simpleDateFormat.format(t.getDateOfBirth()),
                        t.getPhoneNumber(),
                        studentClassesService.getClassesInfoV2(t),
                        filesService.getFileInfo(t.getAvatar_id())
                )));

        return new PageablePayload(
                studentsPage.getTotalPages(),
                studentsPage.getTotalElements(),
                studentsPage.getSize(),
                list);
    }

    @Override
    public StudentsPayloadDetails getById(UUID id) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Students students = studentsRepo.findById(id).orElse(null);
        if (students == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect Student Id");
        }

        return new StudentsPayloadDetails(
                students.getId(),
                students.getFirstName(),
                students.getLastName(),
                students.getSureName(),
                simpleDateFormat.format(students.getDateOfBirth()),
                students.getPhoneNumber(),
                studentClassesService.getClassesInfo(students),
                students.getAddress(),
                filesService.getFileInfo(students.getAvatar_id()),
                Utils.convertToObject(students.getMother()),
                Utils.convertToObject(students.getFather()),
                Utils.convertToObject(students.getBirthInfo()),
                students.getMonthlyPayment(),
                Utils.convertToObject(students.getAdditionalInfo(), JsonNode.class),
                students.getSex(),
                filesService.getFileInfo(students.getFile_id()),
                Utils.convertToObject(students.getDiscount(), JsonNode.class)
        );
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(List<UUID> teachers) {
        States states = stateService.getStateByLabel(StateEnum.DELETED);
        teachers.forEach(s -> {
            Optional<Students> optional = studentsRepo.findById(s);
            if (optional.isPresent()) {
                Students t = optional.get();
                t.setState(states);
                studentsRepo.save(t);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Incorrect Student Id");
            }
        });
    }

    public List<ClassStudentsPayload> getStudentsByClass(UUID classId) {
        return studentClassesService.getStudentsByClass(classId);
    }

    public Students getStudentById(UUID id) {
        Students students = studentsRepo.findById(id).orElse(null);
        if (students == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bunday ID ga ega o'quvchi topilmadi !");
        }
        return students;
    }

    public StudentsPayload getStudentsPayload(Students student) {
        if (student == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return new StudentsPayload(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getSureName(),
                simpleDateFormat.format(student.getDateOfBirth()),
                student.getPhoneNumber(),
                studentClassesService.getClassesInfo(student)
        );
    }

    public List<Students> findStudentsByName(String name) {
        return studentsRepo.findAllByName(name.toLowerCase());
    }

    public List<Students> getAllStudents() {
        return studentsRepo.findAllByStateLabel(StateEnum.ACTIVE);
    }
}

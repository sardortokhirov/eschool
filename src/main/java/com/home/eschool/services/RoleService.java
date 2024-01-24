package com.home.eschool.services;

import com.home.eschool.entity.Roles;
import com.home.eschool.entity.enums.RoleEnum;
import com.home.eschool.repository.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Roles> generateRoles() {
        if (roleRepo.count() == 0) {
            List<Roles> list = new ArrayList<>();
            list.add(new Roles(UUID.randomUUID(), "Administrator", RoleEnum.ROLE_ADMIN));
            list.add(new Roles(UUID.randomUUID(), "Teacher", RoleEnum.ROLE_TEACHER));
            list.add(new Roles(UUID.randomUUID(), "Accountant", RoleEnum.ROLE_ACCOUNTANT));

            return roleRepo.saveAll(list);
        }
        return new ArrayList<>();
    }

    public List<Roles> getRolesList() {
        return roleRepo.findAll();
    }

    public Roles getRoleByLabel(RoleEnum label) {
        return roleRepo.getRolesByLabel(label);
    }

    public Roles getRoleById(UUID id) {
        return roleRepo.getById(id);
    }
}

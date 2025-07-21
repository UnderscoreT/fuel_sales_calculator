package com.blestcodestudios.fuelsalesapp.repository;

import com.blestcodestudios.fuelsalesapp.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
package com.blestcodestudios.fuelsalesapp.repository;

import com.blestcodestudios.fuelsalesapp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUsernameIgnoreCase(String username);

}

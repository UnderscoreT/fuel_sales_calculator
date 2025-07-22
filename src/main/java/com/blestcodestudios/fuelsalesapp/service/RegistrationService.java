package com.blestcodestudios.fuelsalesapp.service;

import com.blestcodestudios.fuelsalesapp.dto.UserCredentialsDto;
import com.blestcodestudios.fuelsalesapp.entity.AppUser;
import com.blestcodestudios.fuelsalesapp.entity.Role;
import com.blestcodestudios.fuelsalesapp.repository.RoleRepository;
import com.blestcodestudios.fuelsalesapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public boolean userExists(UserCredentialsDto userCredentialsDto) {

        return userRepository.findByUsernameIgnoreCase(userCredentialsDto.getUsername())
                .isPresent();

    }

    public void save(AppUser model) {

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found!"));

        model.getRoles().add(userRole);

        userRepository.save(model);
        System.out.println("Saving new user: " + model.getUsername());
    }
}

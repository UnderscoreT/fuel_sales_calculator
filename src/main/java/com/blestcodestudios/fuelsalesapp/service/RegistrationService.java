package com.blestcodestudios.fuelsalesapp.service;

import com.blestcodestudios.fuelsalesapp.dto.UserCredentialsDto;
import com.blestcodestudios.fuelsalesapp.entity.AppUser;
import com.blestcodestudios.fuelsalesapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;

    public boolean userExists(UserCredentialsDto userCredentialsDto) {

        return userRepository.findByUsernameIgnoreCase(userCredentialsDto.getUsername())
                .isPresent();

    }

    public void save(AppUser model) {
        userRepository.save(model);
    }
}

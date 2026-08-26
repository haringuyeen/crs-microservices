package hn.registrationservice.controller;

import hn.registrationservice.dto.RegistrationRequestDTO;
import hn.registrationservice.entity.Registration;
import hn.registrationservice.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Registration register(@Valid @RequestBody RegistrationRequestDTO dto) {
        return registrationService.register(dto);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        registrationService.cancel(id);
    }
    @GetMapping("/my")
    public List<Registration> getMyRegistrations(Authentication authentication) {
        if (authentication == null || authentication.getCredentials() == null) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "Chua dang nhap");
        }
        Long studentId;
        if (authentication.getCredentials() instanceof Number number) {
            studentId = number.longValue();
        } else {
            studentId = Long.valueOf(authentication.getCredentials().toString());
        }
        return registrationService.getMyRegistrations(studentId);
    }
}
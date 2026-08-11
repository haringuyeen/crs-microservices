package hn.authservice.service;

import hn.authservice.dto.LoginRequestDTO;
import hn.authservice.dto.LoginResponseDTO;
import hn.authservice.entity.User;
import hn.authservice.exception.InvalidCredentialsException;
import hn.authservice.repository.UserRepository;
import hn.authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Sai username hoac password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
        {
            throw new InvalidCredentialsException("Sai username hoac password");
        }

        String token = jwtUtil.generateToken(user.getUsername(),
                user.getRole());
        return new LoginResponseDTO(token, user.getUsername(),
                user.getRole());
    }
} 

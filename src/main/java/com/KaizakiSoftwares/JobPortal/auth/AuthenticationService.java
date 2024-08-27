package com.KaizakiSoftwares.JobPortal.auth;

import com.KaizakiSoftwares.JobPortal.email.EmailService;
import com.KaizakiSoftwares.JobPortal.email.EmailTemplateName;
import com.KaizakiSoftwares.JobPortal.role.RoleRepository;
import com.KaizakiSoftwares.JobPortal.security.JwtService;
import com.KaizakiSoftwares.JobPortal.user.Token;
import com.KaizakiSoftwares.JobPortal.user.TokenRepository;
import com.KaizakiSoftwares.JobPortal.user.User;
import com.KaizakiSoftwares.JobPortal.user.UserRepository;
import jakarta.mail.IllegalWriteException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private  final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        if("ADMIN".equals(request.getRole())){
            throw new IllegalWriteException("Cannot register an admin");
        }
        var userRole = roleRepository.findByName(request.getRole())
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE NOT FOUND"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendvalidationEmail(user);
    }

    private void sendvalidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        //send email
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl+"?token="+newToken,
                newToken.toString(),
                "Account activation"
        );
    }

    private Object generateAndSaveActivationToken(User user) {
        //generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length()); // 0..9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                //todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendvalidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent");
        }

        var user  = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}

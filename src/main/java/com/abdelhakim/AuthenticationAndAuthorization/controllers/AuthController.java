package com.abdelhakim.AuthenticationAndAuthorization.controllers;


import com.abdelhakim.AuthenticationAndAuthorization.models.User;
import com.abdelhakim.AuthenticationAndAuthorization.payload.request.SignInRequest;
import com.abdelhakim.AuthenticationAndAuthorization.payload.request.SignUpRequest;
import com.abdelhakim.AuthenticationAndAuthorization.payload.response.AuthenticationResponse;
import com.abdelhakim.AuthenticationAndAuthorization.payload.response.MessageResponse;
import com.abdelhakim.AuthenticationAndAuthorization.repository.UserRepository;
import com.abdelhakim.AuthenticationAndAuthorization.security.jwt.JwtUtils;
import com.abdelhakim.AuthenticationAndAuthorization.security.services.UserDetailsImpl;
import com.abdelhakim.AuthenticationAndAuthorization.security.services.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtUtils jwtUtils;

  /**
   * Endpoint for authenticating users.
   *
   * @param loginRequest The SignInRequest object containing user credentials
   * @return ResponseEntity with AuthenticationResponse and RefreshToken if authentication is successful
   */
  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String token = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return ResponseEntity.ok(new AuthenticationResponse(
            token,
            userDetails.getId(),
            userDetails.getEmail(),
            roles
    ));
  }

  /**
   * Endpoint for registering new users.
   *
   * @param signUpRequest The SignUpRequest object containing user details
   * @return ResponseEntity with a MessageResponse indicating successful registration
   *         or an error message if email is already in use
   */
  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User();
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    user.setRole(signUpRequest.getRole().equalsIgnoreCase("admin") ? "ADMIN" : "CLIENT");

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User Registered Successfully!"));
  }

  /**
   * Endpoint for logging out users.
   *
   * @return ResponseEntity with a MessageResponse indicating successful logout
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/sign-out")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've Been Signed Out!"));
  }

}

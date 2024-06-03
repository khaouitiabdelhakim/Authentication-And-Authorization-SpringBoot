package com.abdelhakim.AuthenticationAndAuthorization.controllers;

import com.abdelhakim.AuthenticationAndAuthorization.payload.response.UserInfoResponse;
import com.abdelhakim.AuthenticationAndAuthorization.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app")
public class AppController {

    /**
     * Endpoint to get the authenticated user's profile.
     * Only accessible by authenticated users.
     *
     * @return ResponseEntity with UserInfoResponse containing user's profile details
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                roles
        );

        return ResponseEntity.ok(userInfoResponse);
    }
}

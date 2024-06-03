package com.abdelhakim.AuthenticationAndAuthorization.controllers;

import com.abdelhakim.AuthenticationAndAuthorization.models.User;
import com.abdelhakim.AuthenticationAndAuthorization.payload.response.MessageResponse;
import com.abdelhakim.AuthenticationAndAuthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint for getting all clients.
     * Only accessible by users with ROLE_ADMIN.
     *
     * @return ResponseEntity with the list of clients
     */

    @GetMapping("/clients")
    public ResponseEntity<?> getAllClients() {
        List<User> clients = userRepository.findAll().stream()
                .filter(user -> user.getRole().equalsIgnoreCase("CLIENT"))
                .collect(Collectors.toList());

        return ResponseEntity.ok(clients);
    }

    /**
     * Example endpoint for an admin-specific action.
     * Only accessible by users with ROLE_ADMIN.
     *
     * @return ResponseEntity with a message response
     */
    @GetMapping("/example")
    public ResponseEntity<?> adminExample() {
        return ResponseEntity.ok(new MessageResponse("This is an admin-specific action!"));
    }
}

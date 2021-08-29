package ftn.project.web.controllers;

import ftn.project.jwt.JwtResponse;
import ftn.project.jwt.TokenUtils;
import ftn.project.models.Authority;
import ftn.project.models.User;
import ftn.project.services.UserService;
import ftn.project.support.LogEvents;
import ftn.project.web.dto.ChangePasswordDto;
import ftn.project.web.dto.UserLoginDto;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    //LOGIN
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        System.out.println("LocalDate " + LocalDate.now());
        System.out.println("Login pogodjen " + userLoginDto.toString());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();
//        LogEvents.log(AuthController.class, "User login: " + userDetails);
        String jwt = tokenUtils.generateJwtToken(authentication);

        JwtResponse jwtResponse = new JwtResponse(jwt);
//        List<Authority> roles = userDetails.getAuthorities();

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping(value = "/change-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto, Principal principal) throws Exception {
        System.out.println("Change password hitted: " +changePasswordDto);
        userService.changePassword(changePasswordDto);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }
}

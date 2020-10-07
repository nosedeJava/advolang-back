package advolang.app.controllers;

import advolang.app.exceptions.UserNotFound;
import advolang.app.models.ERole;
import advolang.app.models.Recommendation;
import advolang.app.models.Role;
import advolang.app.models.User;
import advolang.app.repository.UserRepository;
import advolang.app.services.security.payload.request.LoginRequest;
import advolang.app.services.security.payload.request.SignupRequest;
import advolang.app.services.security.payload.response.JwtResponse;
import advolang.app.services.security.payload.response.MessageResponse;
import advolang.app.repository.RoleRepository;
import advolang.app.services.RecommendationService;
import advolang.app.services.UserService;
import advolang.app.services.security.jwt.JwtUtils;
import advolang.app.services.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private RecommendationService recommendationService;
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
    private UserService userService;

	@Autowired
    private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
    private JwtUtils jwtUtils;

	
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @GetMapping("/signup")
    public ResponseEntity<?> registerUser() {
        try {
			if (userService.getUserByUsername("maxo7")!= null) {
				
			    return ResponseEntity
			            .badRequest()
			            .body(new MessageResponse("Error: Username is already taken!"));
			}
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Create new user's account
        User user = new User("Natalia45", "maxo7",
        		"maxo12354",
                "12345");

        Set<String> strRoles = new HashSet<>();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        
        try {
			System.out.println(recommendationService.getUserRecommendations(userService.getUserByUsername("nduran06")).size());
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        user.setRoles(roles);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
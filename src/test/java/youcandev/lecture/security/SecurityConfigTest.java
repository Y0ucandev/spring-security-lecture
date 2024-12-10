package youcandev.lecture.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    void testUserDetailsService() {
        assertNotNull(userDetailsService.loadUserByUsername("user"), "User should be loaded");
        assertNotNull(userDetailsService.loadUserByUsername("admin"), "Admin should be loaded");
    }

    @Test
    void testPasswordEncoder() {
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Password should match the encoded password");
    }

    @Test
    void testAuthenticationManager() {
        assertNotNull(authenticationManager, "AuthenticationManager should be initialized");
    }
}

package youcandev.lecture.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "testUser";
        String token = jwtUtils.generateToken(username);

        assertNotNull(token, "Token should not be null");
        assertEquals(username, jwtUtils.validateToken(token), "Token should be valid and return the correct username");
    }

    @Test
    void testInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertThrows(Exception.class, () -> jwtUtils.validateToken(invalidToken), "Invalid token should throw an exception");
    }
}

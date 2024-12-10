package youcandev.lecture.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        SecurityContextHolder.clearContext(); // Clear context before each test
    }

    @Test
    void testValidToken() throws Exception {
        String token = "valid.token.here";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateToken(token)).thenReturn("testUser");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be set");
        assertEquals("testUser", SecurityContextHolder.getContext().getAuthentication().getName(), "Username should match");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testInvalidToken() throws Exception {
        String invalidToken = "invalid.token.here";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(jwtUtils.validateToken(invalidToken)).thenThrow(new RuntimeException("Invalid token"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set for invalid token");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testNoToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set when no token is provided");
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
package com.example.ms1.controller;

import com.example.ms1.service.Ms1Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link MS1Controller} using the Spring MVC web-layer slice.
 *
 * <h2>Strategy</h2>
 * <ul>
 *   <li>{@code @WebMvcTest} loads <em>only</em> the web layer (DispatcherServlet,
 *       filters, controllers, Jackson, etc.) — the full application context and
 *       embedded server are <strong>never started</strong>.</li>
 *   <li>{@code @MockBean} injects a Mockito-managed double for
 *       {@link Ms1Service}, completely isolating controller logic from
 *       the service layer.</li>
 *   <li>MockMvc is auto-configured and injected by Spring Test.</li>
 * </ul>
 *
 * <h2>How to Run</h2>
 * <p>In STS4 / Eclipse: right-click this class → <em>Run As → JUnit Test</em>.</p>
 */
@WebMvcTest(MS1Controller.class)   // Step 1 – load only the web layer for MS1Controller
class MS1ControllerTest {

    // ---------------------------------------------------------------
    // Step 1 – Injected by @WebMvcTest; no server is started
    // ---------------------------------------------------------------

    /** Auto-configured MockMvc instance for simulating HTTP requests. */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mockito mock for the service layer.
     * Spring replaces the real {@link Ms1Service} bean with this mock
     * so the controller can be tested without any service-layer logic.
     */
    @MockitoBean
    private Ms1Service ms1Service;

    // ---------------------------------------------------------------
    // Step 2 & 3 – Test: GET /ms1/add?a=5&b=10
    // ---------------------------------------------------------------

    /**
     * <strong>Unit test: addition endpoint</strong>
     *
     * <p><b>Given</b>  the service returns {@code "MS 1.1 - Additon = 15"}
     * when called with {@code add(5, 10)}.<br>
     * <b>When</b>   a GET request is made to {@code /ms1/add?a=5&b=10}.<br>
     * <b>Then</b>   the response status is {@code 200 OK} and the body equals
     * {@code "MS 1.1 - Additon = 15"} exactly.</p>
     *
     * <p>Steps covered:
     * <ol>
     *   <li>Step 2 – Simulate the HTTP GET request via MockMvc.</li>
     *   <li>Step 3 – Assert HTTP 200 and exact body text.</li>
     * </ol>
     * </p>
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/add?a=5&b=10 → 200 OK with body 'MS 1.1 - Additon = 15'")
    void testAddEndpoint_Returns200WithCorrectBody() throws Exception {

        // ---- ARRANGE (Step 2 – stub the mocked service) ----------------
        // Tell Mockito what to return when the controller calls ms1Service.add(5, 10)
        when(ms1Service.add(5, 10)).thenReturn("MS 1.1 - Additon = 15");

        // ---- ACT & ASSERT (Step 2 & 3) ---------------------------------
        mockMvc.perform(
                    get("/ms1/add")                              // simulate GET /ms1/add
                        .param("a", "5")                        // query param a=5
                        .param("b", "10")                       // query param b=10
                        .accept(MediaType.TEXT_PLAIN)           // accept plain text
                )
                // Step 3 – verify HTTP status 200 OK
                .andExpect(status().isOk())
                // Step 3 – verify the exact response body
                .andExpect(content().string("MS 1.1 - Additon = 15"));
    }

    // ---------------------------------------------------------------
    // Additional unit tests for other endpoints (bonus coverage)
    // ---------------------------------------------------------------

    /**
     * Verifies the subtraction endpoint returns the correct result.
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/subtract?a=10&b=3 → 200 OK with body 'MS 1.1 - Subtraction = 7'")
    void testSubtractEndpoint_Returns200WithCorrectBody() throws Exception {
        when(ms1Service.subtract(10, 3)).thenReturn("MS 1.1 - Subtraction = 7");

        mockMvc.perform(
                    get("/ms1/subtract")
                        .param("a", "10")
                        .param("b", "3")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("MS 1.1 - Subtraction = 7"));
    }

    /**
     * Verifies the multiplication endpoint returns the correct result.
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/multiply?a=4&b=5 → 200 OK with body 'MS 1.1 - Multiplication = 20'")
    void testMultiplyEndpoint_Returns200WithCorrectBody() throws Exception {
        when(ms1Service.multiply(4, 5)).thenReturn("MS 1.1 - Multiplication = 20");

        mockMvc.perform(
                    get("/ms1/multiply")
                        .param("a", "4")
                        .param("b", "5")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("MS 1.1 - Multiplication = 20"));
    }

    /**
     * Verifies the division endpoint returns the correct result for valid input.
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/divide?a=20&b=4 → 200 OK with body 'MS 1.1 - Division = 5'")
    void testDivideEndpoint_Returns200WithCorrectBody() throws Exception {
        when(ms1Service.divide(20, 4)).thenReturn("MS 1.1 - Division = 5");

        mockMvc.perform(
                    get("/ms1/divide")
                        .param("a", "20")
                        .param("b", "4")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("MS 1.1 - Division = 5"));
    }

    /**
     * Verifies the division endpoint returns 400 Bad Request when dividing by zero.
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/divide?a=10&b=0 → 400 Bad Request (division by zero)")
    void testDivideEndpoint_DivisionByZero_Returns400() throws Exception {
        when(ms1Service.divide(10, 0))
                .thenThrow(new ArithmeticException("Division by zero is not allowed."));

        mockMvc.perform(
                    get("/ms1/divide")
                        .param("a", "10")
                        .param("b", "0")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Division by zero is not allowed."));
    }

    /**
     * Verifies the greet endpoint returns a personalised greeting.
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/greet?name=Alice → 200 OK with greeting")
    void testGreetEndpoint_Returns200WithGreeting() throws Exception {
        when(ms1Service.greetUser("Alice")).thenReturn("MS 1.1 - Hello, Alice!");

        mockMvc.perform(
                    get("/ms1/greet")
                        .param("name", "Alice")
                        .accept(MediaType.TEXT_PLAIN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("MS 1.1 - Hello, Alice!"));
    }

    /**
     * Verifies the health-check endpoint responds with {@code 200 OK}.
     *
     * @throws Exception propagated from {@code mockMvc.perform()}
     */
    @Test
    @DisplayName("GET /ms1/health → 200 OK with 'MS1 is UP'")
    void testHealthEndpoint_Returns200() throws Exception {
        mockMvc.perform(get("/ms1/health"))
               .andExpect(status().isOk())
               .andExpect(content().string("MS1 is UP"));
    }
}

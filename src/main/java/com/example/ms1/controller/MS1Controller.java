package com.example.ms1.controller;

import com.example.ms1.service.Ms1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Microservice 1 (MS1).
 *
 * <p>Handles arithmetic operations and user management HTTP endpoints
 * under the base path {@code /ms1}.</p>
 *
 * <p>All arithmetic methods delegate to {@link Ms1Service}, making
 * this controller thin and easily unit-testable with MockMvc.</p>
 */
@RestController
@RequestMapping("/ms1")
public class MS1Controller {

    /** Business-logic service injected by Spring's DI container. */
    private final Ms1Service ms1Service;

    /**
     * Constructor injection – preferred style for testability;
     * allows Mockito to inject a mock without Spring context.
     *
     * @param ms1Service the arithmetic / user-management service
     */
    @Autowired
    public MS1Controller(Ms1Service ms1Service) {
        this.ms1Service = ms1Service;
    }

    // ---------------------------------------------------------------
    // Arithmetic Endpoints
    // ---------------------------------------------------------------

    /**
     * Adds two integers supplied as query parameters.
     *
     * <p>Example: {@code GET /ms1/add?a=5&b=10}
     * → {@code 200 OK} body: {@code "MS 1.1 - Additon = 15"}</p>
     *
     * @param a first operand (query param)
     * @param b second operand (query param)
     * @return {@code 200 OK} with the formatted addition result
     */
    @GetMapping("/add")
    public ResponseEntity<String> add(
            @RequestParam("a") int a,
            @RequestParam("b") int b) {
        String result = ms1Service.add(a, b);
        return ResponseEntity.ok(result);
    }

    /**
     * Subtracts {@code b} from {@code a}.
     *
     * <p>Example: {@code GET /ms1/subtract?a=10&b=3}
     * → {@code 200 OK} body: {@code "MS 1.1 - Subtraction = 7"}</p>
     *
     * @param a first operand (query param)
     * @param b second operand (query param)
     * @return {@code 200 OK} with the formatted subtraction result
     */
    @GetMapping("/subtract")
    public ResponseEntity<String> subtract(
            @RequestParam("a") int a,
            @RequestParam("b") int b) {
        String result = ms1Service.subtract(a, b);
        return ResponseEntity.ok(result);
    }

    /**
     * Multiplies two integers.
     *
     * <p>Example: {@code GET /ms1/multiply?a=4&b=5}
     * → {@code 200 OK} body: {@code "MS 1.1 - Multiplication = 20"}</p>
     *
     * @param a first operand (query param)
     * @param b second operand (query param)
     * @return {@code 200 OK} with the formatted multiplication result
     */
    @GetMapping("/multiply")
    public ResponseEntity<String> multiply(
            @RequestParam("a") int a,
            @RequestParam("b") int b) {
        String result = ms1Service.multiply(a, b);
        return ResponseEntity.ok(result);
    }

    /**
     * Divides {@code a} by {@code b}.
     *
     * <p>Example: {@code GET /ms1/divide?a=20&b=4}
     * → {@code 200 OK} body: {@code "MS 1.1 - Division = 5"}</p>
     *
     * @param a dividend (query param)
     * @param b divisor (query param; must not be zero)
     * @return {@code 200 OK} with the formatted division result,
     *         or {@code 400 Bad Request} if {@code b} is zero
     */
    @GetMapping("/divide")
    public ResponseEntity<String> divide(
            @RequestParam("a") int a,
            @RequestParam("b") int b) {
        try {
            String result = ms1Service.divide(a, b);
            return ResponseEntity.ok(result);
        } catch (ArithmeticException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // User Management Endpoints
    // ---------------------------------------------------------------

    /**
     * Returns a personalised greeting for the given user name.
     *
     * <p>Example: {@code GET /ms1/greet?name=Alice}
     * → {@code 200 OK} body: {@code "MS 1.1 - Hello, Alice!"}</p>
     *
     * @param name the user name (query param)
     * @return {@code 200 OK} with the greeting string
     */
    @GetMapping("/greet")
    public ResponseEntity<String> greet(
            @RequestParam("name") String name) {
        String result = ms1Service.greetUser(name);
        return ResponseEntity.ok(result);
    }

    /**
     * Health-check endpoint for the microservice.
     *
     * <p>Example: {@code GET /ms1/health}
     * → {@code 200 OK} body: {@code "MS1 is UP"}</p>
     *
     * @return {@code 200 OK} confirming the service is live
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("MS1 is UP");
    }
}

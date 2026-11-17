package com.example.ms1.service;

import org.springframework.stereotype.Service;

/**
 * Service layer for MS1Controller.
 *
 * <p>Provides arithmetic operations and user management business logic.
 * Separating concerns into a @Service bean allows MS1Controller to be
 * unit-tested in isolation by mocking this dependency via Mockito.</p>
 */
@Service
public class Ms1Service {

    /**
     * Adds two integers and returns the formatted result string.
     *
     * @param a first operand
     * @param b second operand
     * @return formatted string in the form "MS 1.1 - Additon = &lt;result&gt;"
     */
    public String add(int a, int b) {
        // Note: intentional typo "Additon" matches expected test output exactly
        return "MS 1.1 - Additon = " + (a + b);
    }

    /**
     * Subtracts b from a and returns the formatted result string.
     *
     * @param a first operand
     * @param b second operand
     * @return formatted string with subtraction result
     */
    public String subtract(int a, int b) {
        return "MS 1.1 - Subtraction = " + (a - b);
    }

    /**
     * Multiplies two integers and returns the formatted result string.
     *
     * @param a first operand
     * @param b second operand
     * @return formatted string with multiplication result
     */
    public String multiply(int a, int b) {
        return "MS 1.1 - Multiplication = " + (a * b);
    }

    /**
     * Divides a by b and returns the formatted result string.
     *
     * @param a dividend
     * @param b divisor
     * @return formatted string with division result
     * @throws ArithmeticException if b is zero
     */
    public String divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return "MS 1.1 - Division = " + (a / b);
    }

    /**
     * Returns a greeting for the given user name.
     *
     * @param name user name
     * @return welcome string
     */
    public String greetUser(String name) {
        return "MS 1.1 - Hello, " + name + "!";
    }
}

package Feedback;

import java.util.ArrayList;
import java.util.List;

public class FormValidator {
    private final List<String> errorMessages = new ArrayList<>();

    /**
     * Validates that the given field is not null or empty.
     *
     * @param fieldName the name of the field being validated
     * @param value     the value to validate
     */
    public void validatePresence(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            errorMessages.add(fieldName + " is required.");
        }
    }

    /**
     * Checks if there are any validation errors.
     *
     * @return true if there are errors, false otherwise
     */
    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }

    /**
     * Returns a copy of the error messages list to ensure encapsulation.
     *
     * @return a list of error messages
     */
    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages); // Return a copy to prevent external modifications
    }

    /**
     * Clears all validation errors.
     */
    public void clearErrors() {
        errorMessages.clear();
    }
}

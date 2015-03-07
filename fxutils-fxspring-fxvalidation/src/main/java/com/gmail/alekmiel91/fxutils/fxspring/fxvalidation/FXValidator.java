package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation;

import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
public class FXValidator implements Validator<Object> {
    private final javax.validation.Validator validator;
    private final Object model;
    private final String property;

    public FXValidator(javax.validation.Validator validator, Object model, String property) {
        this.validator = validator;
        this.model = model;
        this.property = property;
    }

    @Override
    public ValidationResult apply(Control control, Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(model, property);

        if (constraintViolations.isEmpty()) {
            return new ValidationResult();
        }

        String message = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(System.lineSeparator()));

        return ValidationResult.fromMessages(ValidationMessage.error(control, message));
    }
}

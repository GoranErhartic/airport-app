package com.airport.app.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FlightCodeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FlightCodeConstraint {

    String message() default "Flight code is invalid format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.airport.app.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlightCodeValidator implements ConstraintValidator<FlightCodeConstraint, String> {

    @Override
    public void initialize(FlightCodeConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String flightCode,
                           ConstraintValidatorContext cxt) {
        return flightCode != null && flightCode.matches("^[aA-zZ]{3}-[0-9]{4}$");
    }
}

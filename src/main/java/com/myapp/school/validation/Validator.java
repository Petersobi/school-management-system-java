package com.myapp.school.validation;

public interface Validator <T>{
    ValidationResult validate(T target);
}

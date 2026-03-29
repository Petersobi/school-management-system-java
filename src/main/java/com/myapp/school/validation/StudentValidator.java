package com.myapp.school.validation;

import com.myapp.school.model.Student;
import com.myapp.school.validation.ValidationResult;

public class StudentValidator implements Validator<Student> {
    @Override
    public ValidationResult validate(Student student){
        if (student==null){
            return ValidationResult.fail("Student cant be null");
        }
        if(student.getFirstName()==null||student.getFirstName().isBlank()){
         return    ValidationResult.fail("Student Firstname is required!");
        }
        if(student.getLastName()==null||student.getLastName().isBlank()){
         return    ValidationResult.fail("Student Lastname is required!");
    }
        if(student.getClassName()==null||student.getClassName().isBlank()){
         return    ValidationResult.fail("Student Class is required!");

}
        return ValidationResult.ok();
    }
}


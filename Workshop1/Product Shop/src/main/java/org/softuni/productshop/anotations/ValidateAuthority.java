package org.softuni.productshop.anotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface ValidateAuthority {
	
	String[] acceptedValues();
	
	String message() default "Wrong authority value!!!";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}

package JavaCA.validation;
//pls push
import java.lang.annotation.ElementType; 
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CarplateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CarplateContraint {
	 //error message  
    public String message() default "Must be empty, or start with E or S";  
    //represents group of constraints     
    public Class<?>[] groups() default {};  
    //represents additional information about annotation  
    public Class<? extends Payload>[] payload() default {};  
}

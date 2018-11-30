package lu.wealins.liability.services.core.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // on class level
public @interface Historizable {

	String id();

	String createdBy() default "createdBy";

	String createdDate() default "createdDate";

	String createdTime() default "createdTime";

	String createdProcess() default "createdProcess";

	String modifyBy() default "modifyBy";

	String modifyDate() default "modifyDate";

	String modifyTime() default "modifyTime";

	String modifyProcess() default "modifyProcess";
}

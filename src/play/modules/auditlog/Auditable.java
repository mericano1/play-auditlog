package play.modules.auditlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auditable {
	public static enum Operation {
	    CREATE, READ, UPDATE, DELETE
	}
	/* Creates a record for the specified activity types*/
	Operation [] recordOn() default {Operation.CREATE, Operation.UPDATE, Operation.DELETE};
}



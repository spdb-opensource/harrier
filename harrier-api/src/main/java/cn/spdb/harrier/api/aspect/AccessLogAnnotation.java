
package cn.spdb.harrier.api.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLogAnnotation {
    // ignore request args
    String[] ignoreRequestArgs() default {};

    boolean ignoreRequest() default false;

    boolean ignoreResponse() default true;
}

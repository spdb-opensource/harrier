
package cn.spdb.harrier.api.exceptions;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cn.spdb.harrier.api.enums.Status;

/**
 * controller exception annotation
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ApiException {
    Status value();
}

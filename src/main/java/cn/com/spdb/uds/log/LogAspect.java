package cn.com.spdb.uds.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(* com.amarsoft.uds.core..*(..))")
	private void aspectjMethod() {

	}

	@AfterThrowing(value = "aspectjMethod()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		Object[] args = joinPoint.getArgs();
		String argsStr = "";
		if (args != null && args.length > 0) {
			argsStr = JSON.toJSONString(args);
		}
		String className = joinPoint.getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		UdsLogger.logEvent(LogEvent.ERROR_THROWABLE, className, methodName, argsStr, e.getMessage());
	}

}

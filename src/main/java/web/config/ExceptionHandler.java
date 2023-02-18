package web.config;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import web.common.exception.ParameterErrorException;
import web.common.response.Response;
import web.common.response.RspCode;
import web.common.util.ExceptionUtil;
import web.common.util.IpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
@Aspect
@Component
public class ExceptionHandler {

	@Autowired
	private AppConfig config;

	@Pointcut("execution(* web.controller.*.*.*(..))")
	private void allMethod() {
	}

	@Around("allMethod()")
	public Object doAround(ProceedingJoinPoint call) {

		try {
			Object result = call.proceed();

			if (result instanceof Response) {
				Response response = (Response) result;
				if (config.getRequestLog()) {
					StringBuilder basicBuilder = getBasicBuilder(call);
					basicBuilder.append("Response: ");
					basicBuilder.append(response.toString());
					log.info(basicBuilder.toString());
				}
				return response;
			}

			if (config.getRequestLog()) {
				StringBuilder basicBuilder = getBasicBuilder(call);
				basicBuilder.append("Response: ");
				basicBuilder.append(JSON.toJSONString(result));
				log.info(basicBuilder.toString());
			}

			return result;

		} catch (ConstraintViolationException c) {
			Set<ConstraintViolation<?>> constraintViolations = c.getConstraintViolations();
			for (ConstraintViolation<?> con : constraintViolations) {
				if (config.getRequestLog()) {
					StringBuilder basicBuilder = getBasicBuilder(call);
					basicBuilder.append("Invalid parameters: ");
					basicBuilder.append(con.getMessage());
					log.info(basicBuilder.toString());
				}
				return Response.build(RspCode.PARAMETER_ERROR, con.getMessage(), null);
			}
			if (config.getRequestLog()) {
				StringBuilder basicBuilder = getBasicBuilder(call);
				basicBuilder.append("Invalid parameters: ");
				log.info(basicBuilder.toString());
			}
			return Response.build(RspCode.PARAMETER_ERROR);
		} catch (ParameterErrorException p) {
			if (config.getRequestLog()) {
				StringBuilder basicBuilder = getBasicBuilder(call);
				basicBuilder.append("Invalid parameters: ");
				basicBuilder.append(p.getMessage());
				log.info(basicBuilder.toString());
			}
			return Response.build(RspCode.PARAMETER_ERROR, p.getMessage(), null);
		} catch (Throwable e) {
			log.error(ExceptionUtil.getStackTraceWithCustomInfoToStr(e));
			return Response.build(RspCode.UNKNOWN);
		}
	}

	private StringBuilder getBasicBuilder(ProceedingJoinPoint call) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

		MethodSignature signature = (MethodSignature) call.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
		String[] classNameArray = method.getDeclaringClass().getName().split("\\.");
		String className = classNameArray[classNameArray.length - 1];

		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append(">>>>>>>>Request Log<<<<<<<<<");
		builder.append("\n");
		builder.append("Request method: ");
		builder.append(className + "." + methodName);
		builder.append("\n");
		builder.append("Request IP: ");
		builder.append(IpUtil.getIpAddress(request));
		builder.append("\n");

		return builder;
	}
}

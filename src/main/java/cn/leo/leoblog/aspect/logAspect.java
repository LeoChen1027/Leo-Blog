package cn.leo.leoblog.aspect;
    /*1.请求url;
    2.访问者ip;
    3.调用方法classMethod;
    4.参数args;
    5.返回内容.*/

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class logAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* cn.leo.leoblog.web.*.*(..))")
    public void log(){
    } //该方法只是一个标识，供@Pointcut注解依附

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
        logger.info("Request:{}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("----------doAfter-----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",           result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classname;
        private Object[] args;

        public RequestLog(String url, String ip, String classname, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classname = classname;
            this.args = args;
        }

        @Override
        public String toString() {
            return "Requesting{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classname='" + classname + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

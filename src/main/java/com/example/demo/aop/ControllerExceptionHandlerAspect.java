package com.example.demo.aop;


import com.example.demo.exception.UserNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerExceptionHandlerAspect {
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object handleControllerExceptions(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR); // 500
        } catch (Throwable th) {
            return new ResponseEntity<>("Unknown Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

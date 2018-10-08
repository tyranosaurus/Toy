package com.midasit.bungae.advice;

import com.midasit.bungae.errorcode.ServerErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class LoginControllerAdvice  {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> isValidUserExceptionHandler(MethodArgumentNotValidException e,
                                                           HttpServletResponse response) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, Object> map = new HashMap<>();

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("ErrorCode", ServerErrorCode.EMPTY_VALUE_OF_FIELD.getValue());
        map.put("ErrorMessage", messageSource.getMessage("valid.field.empty", new String[] { bindingResult.getFieldErrors().get(0).getField() }, Locale.KOREAN));

        return map;
    }
}

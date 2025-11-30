package com.eeit205team5.rentalmanagement.common.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 處理業務邏輯例外
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        log.warn("Business exception: {}", e.getMessage());

        return ApiResponse.fail(e.getMessage()); // 自訂錯誤訊息
    }

    // 處理 JSON 解析錯誤（包含 enum 錯誤）
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ApiResponse<?> handleJsonParseError(
            HttpMessageNotReadableException ex) {

        String message = "請求格式錯誤";

        // 可以進一步解析具體原因
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            if (ife.getTargetType().isEnum()) {
                message = String.format("無效的值 '%s'，允許的值為: %s",
                        ife.getValue(),
                        Arrays.toString(ife.getTargetType().getEnumConstants()));
            }
        }

        return ApiResponse.fail(message);
    }

    // 處理驗證例外(@Vaild)
    // { "email": "Email格式不正確", "password": "密碼至少8個字元" }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ApiResponse<?> handleValidationException(
            MethodArgumentNotValidException e) {
        log.warn("Validation errors: {}", e.getBindingResult().getAllErrors());

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return ApiResponse.fail(errors, "驗證失敗");
    }

    // 處理帳號密碼錯誤
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ApiResponse<?> handleBadCredentials(BadCredentialsException e) {
        log.warn("Bad credentials: {}", e.getMessage());

        return ApiResponse.fail("帳號或密碼錯誤");
    }

    // 處理找不到使用者
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ApiResponse<?> handleUsernameNotFound(UsernameNotFoundException e) {
        log.warn("Username not found: {}", e.getMessage());

        return ApiResponse.fail("找不到使用者");
    }

    // 處理參數錯誤
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ApiResponse<?> handleBadRequest(IllegalArgumentException e) {
        log.warn("Entity not found: {}", e.getMessage());

        return ApiResponse.fail("參數錯誤: " + e.getMessage());
    }

    // 處理找不到資料
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ApiResponse<Void> handleEntityNotFound(EntityNotFoundException e) {
        log.warn("Invalid argument: {}", e.getMessage());

        return ApiResponse.fail("查無資料");
    }

    // 處理RuntimeException
    // 不能把e.getMessage()回傳給前端，會洩漏內部資訊
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ApiResponse<?> handleRuntime(RuntimeException e) {
        log.error("Runtime exception: ", e);

        return ApiResponse.fail("系統執行錯誤");
    }

    // 處理其他未預期的例外
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ApiResponse<?> handleOther(Exception e) {
        log.error("Unhandled exception: ", e);

        return ApiResponse.fail("系統發生未預期錯誤");
    }
}
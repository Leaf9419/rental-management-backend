package com.eeit205team5.rentalmanagement.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eeit205team5.rentalmanagement.common.dto.response.ApiResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 處理業務邏輯例外
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        log.warn("Business exception: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400
                .body(ApiResponse.fail(e.getMessage())); // 自訂錯誤訊息
    }

    // 處理驗證例外(@Vaild)
    // { "email": "Email格式不正確", "password": "密碼長度至少8位" }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e) {
        log.warn("Validation errors: {}", e.getBindingResult().getAllErrors());

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400
                .body(ApiResponse.fail(errors, "驗證失敗"));
    }

    // 處理帳號密碼錯誤
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(BadCredentialsException e) {
        log.warn("Bad credentials: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401
                .body(ApiResponse.fail("帳號或密碼錯誤"));
    }

    // 處理找不到使用者
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameNotFound(UsernameNotFoundException e) {
        log.warn("Username not found: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
                .body(ApiResponse.fail("找不到使用者"));
    }

    // 處理參數錯誤
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(IllegalArgumentException e) {
        log.warn("Entity not found: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400
                .body(ApiResponse.fail("參數錯誤: " + e.getMessage()));
    }

    // 處理找不到資料
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException e) {
        log.warn("Invalid argument: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
                .body(ApiResponse.fail("查無資料"));
    }

    // 處理RuntimeException
    // 能把e.getMessage()回傳給前端，會洩漏內部資訊
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(RuntimeException e) {
        log.error("Runtime exception: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body(ApiResponse.fail("系統執行錯誤"));
    }

    // 處理其他未預期的例外
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOther(Exception e) {
        log.error("Unhandled exception: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body(ApiResponse.fail("系統發生未預期錯誤"));
    }
}
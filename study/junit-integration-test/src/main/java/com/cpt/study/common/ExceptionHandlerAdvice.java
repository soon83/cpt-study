package com.cpt.study.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.security.sasl.AuthenticationException;
import javax.validation.UnexpectedTypeException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * HttpStatus 500
     * 시스템 예외 상황
     * 집중 모니터링 대상
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Res> onException(Exception e) {
        log.error("[Exception] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 500
     * validation annotation 을 올바르게 사용하지 못함
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    protected ResponseEntity<Res> onUnexpectedTypeException(UnexpectedTypeException e) {
        log.error("[UnexpectedTypeException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 500
     * 클라이언트에서 발생한 오류
     * 별로 중요하지 않음
     */
    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<Res> ClientAbortException(Exception e) {
        log.warn("[ClientAbortException] {}",  NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.OK).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 200
     * 비즈니스로직에서 에러가 발생
     * 시스템 문제는 없음
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Res> onBaseException(BaseException e) {
        log.warn("[BaseException] {}",  NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(e.getErrorCode());
        return ResponseEntity.status(HttpStatus.OK).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400
     * validation error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Res> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("[MethodArgumentNotValidException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400
     * 클라이언트에서 JSON 포맷을 똑바로 안보냄
     * JSON parse error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Res> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("[HttpMessageNotReadableException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400
     * 파라미터 바인딩 중 필드 타입 불일치가 발생함
     * 주로 enum 이나 time format 이 문제가 됨
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Res> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("[MethodArgumentTypeMismatchException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 400
     * request parameter bind error
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Res> bindException(BindException e) {
        log.warn("[BindException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_PARAMETER, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 401
     * 인증 오류
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Res> authenticationException(AuthenticationException e) {
        log.warn("[AuthenticationException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Res.fail(errorResponse));
    }

    /**
     * HttpStatus 403
     * 인증은 되었으나
     * 권한 없음
     */
    /*@ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Res> accessDeniedException(AccessDeniedException e) {
        log.warn("[AccessDeniedException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Res.fail(errorResponse));
    }*/

    /**
     * HttpStatus 405
     * 존재하지 않는 url mapping
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Res> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("[HttpRequestMethodNotSupportedException] {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_METHOD_NOT_ALLOWED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(Res.fail(errorResponse));
    }
}
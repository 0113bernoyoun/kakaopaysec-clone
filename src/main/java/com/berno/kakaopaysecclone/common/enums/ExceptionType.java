package com.berno.kakaopaysecclone.common.enums;

import com.berno.kakaopaysecclone.common.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExceptionType {


    ERROR_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "EX1000", "Error code를 찾을 수 없습니다."),
    REQUEST_NOT_VALID(HttpStatus.BAD_REQUEST, "RQ1000", "요청 형식이 알맞지 않습니다."),
    CSV_READ_FAIL(HttpStatus.NO_CONTENT, "IO1000", "csv를 읽는 중 에러가 발생했습니다."),
    CANNOT_SAVE_RANDOM_DATA(HttpStatus.NO_CONTENT, "ST1000", "데이터를 저장하던 중 에러가 발생했습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    public static ExceptionType findByErrorCode(String errorCode) {
        return Arrays.stream(ExceptionType.values())
                .filter(exceptionType -> exceptionType.getErrorCode().equals(errorCode))
                .findFirst()
                .orElseThrow(() -> new CustomException(ExceptionType.ERROR_CODE_NOT_FOUND));
    }
}

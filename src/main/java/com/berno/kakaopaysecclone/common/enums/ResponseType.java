package com.berno.kakaopaysecclone.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResponseType {
    OK(HttpStatus.OK, "정상적으로 응답하였습니다."),
    CREATED(HttpStatus.CREATED, "정상적으로 생성하였습니다."),
    SELECT_OK(HttpStatus.CREATED, "정상적으로 선택되었습니다."),

    NO_CONTENT(HttpStatus.NO_CONTENT, "정상적으로 삭제되었습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}

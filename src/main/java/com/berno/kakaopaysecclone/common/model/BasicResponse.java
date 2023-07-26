package com.berno.kakaopaysecclone.common.model;

import com.berno.kakaopaysecclone.common.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BasicResponse<T> {

    private final int statusCode;
    private final ResponseType status;
    private final T data;
    public static <T> BasicResponse toResponse(ResponseType type, T response) {
        return  BasicResponse.builder()
                .statusCode(type.getStatus().value())
                .status(type)
                .data(response)
                .build();
    }

    public BasicResponse() {
        this.statusCode = 200;
        this.status = ResponseType.OK;
        this.data = null;
    }
}

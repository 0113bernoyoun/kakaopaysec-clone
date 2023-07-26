package com.berno.kakaopaysecclone.common.exception;

import com.berno.kakaopaysecclone.common.enums.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{

    private final ExceptionType exceptionType;
}

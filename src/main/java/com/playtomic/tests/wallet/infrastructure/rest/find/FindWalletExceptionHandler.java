package com.playtomic.tests.wallet.infrastructure.rest.find;

import com.playtomic.tests.wallet.domain.error.WalletNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = FindWalletController.class)
public class FindWalletExceptionHandler {

    @ExceptionHandler(WalletNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handle(WalletNotFoundError error) {
    }
}

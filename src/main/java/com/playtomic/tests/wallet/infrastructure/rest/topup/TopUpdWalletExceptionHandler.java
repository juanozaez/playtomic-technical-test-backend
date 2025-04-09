package com.playtomic.tests.wallet.infrastructure.rest.topup;

import com.playtomic.tests.card.domain.error.InvalidCardError;
import com.playtomic.tests.wallet.domain.error.ExistingTransactionError;
import com.playtomic.tests.wallet.domain.error.NegativeAmountError;
import com.playtomic.tests.wallet.domain.error.WalletNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = TopUpdWalletController.class)
public class TopUpdWalletExceptionHandler {

    @ExceptionHandler(WalletNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handle(WalletNotFoundError error) {
    }

    @ExceptionHandler(NegativeAmountError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handle(NegativeAmountError error) {
    }

    @ExceptionHandler(InvalidCardError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handle(InvalidCardError error) {
    }

    @ExceptionHandler(ExistingTransactionError.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    void handle(ExistingTransactionError error) {
    }
}

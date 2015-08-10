package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException</code>
 * is thrown when an error occurs trying to create a new contact for a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantCreateWalletContactException extends WalletContactsException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE REQUESTED CONTACT EXCEPTION";

    public CantCreateWalletContactException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateWalletContactException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateWalletContactException(final String message) {
        this(message, null);
    }

    public CantCreateWalletContactException() {
        this(DEFAULT_MESSAGE);
    }
}

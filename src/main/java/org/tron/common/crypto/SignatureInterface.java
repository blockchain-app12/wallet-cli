package org.kcoin.common.crypto;

public interface SignatureInterface {
    boolean validateComponents();

    byte[] toByteArray();
}
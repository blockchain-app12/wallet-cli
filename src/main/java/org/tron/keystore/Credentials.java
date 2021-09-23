package org.kcoin.keystore;


import org.kcoin.common.crypto.SignInterface;

public interface Credentials {
  SignInterface getPair();

  String getAddress();
}

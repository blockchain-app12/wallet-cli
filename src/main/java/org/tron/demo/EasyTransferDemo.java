package org.kcoin.demo;

import org.kcoin.api.GrpcAPI.EasyTransferResponse;
import org.kcoin.common.crypto.ECKey;
import org.kcoin.common.crypto.Sha256Sm3Hash;
import org.kcoin.common.utils.ByteArray;
import org.kcoin.common.utils.Utils;
import org.kcoin.protos.Protocol.Transaction;
import org.kcoin.walletserver.WalletApi;

import java.util.Arrays;

public class EasyTransferDemo {

  private static byte[] getAddressByPassphrase(String passPhrase) {
    byte[] privateKey = Sha256Sm3Hash.hash(passPhrase.getBytes());
    ECKey ecKey = ECKey.fromPrivate(privateKey);
    byte[] address = ecKey.getAddress();
    return address;
  }

  public static void main(String[] args) {
    String passPhrase = "test pass phrase";
    byte[] address = WalletApi.createAdresss(passPhrase.getBytes());
    if (!Arrays.equals(address, getAddressByPassphrase(passPhrase))) {
      System.out.println("The address is diffrent !!");
    }
    System.out.println("address === " + WalletApi.encode58Check(address));

    EasyTransferResponse response = WalletApi
        .easyTransfer(passPhrase.getBytes(), getAddressByPassphrase("test pass phrase 2"), 10000L);
    if (response.getResult().getResult() == true) {
      Transaction transaction = response.getTransaction();
      System.out.println("Easy transfer successful!!!");
      System.out.println(
          "Receive txid = " + ByteArray.toHexString(response.getTxid().toByteArray()));
      System.out.println(Utils.printTransaction(transaction));
    } else {
      System.out.println("Easy transfer failed!!!");
      System.out.println("Code = " + response.getResult().getCode());
      System.out.println("Message = " + response.getResult().getMessage().toStringUtf8());
    }
  }
}

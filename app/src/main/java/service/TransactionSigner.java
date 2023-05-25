package service;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import model.RawTransaction;

/**
 * Sign transaction with user cridentials
 */
public class TransactionSigner {

    private TransactionSigner signer;
    public TransactionSigner(){}

    public String SignTransaction(RawTransaction rawTransaction, String privateKey){
        Credentials credentials = Credentials.create(privateKey);
        org.web3j.crypto.RawTransaction tx = ToModel(rawTransaction);
        byte[] signedMessage = TransactionEncoder.signMessage(tx, credentials);

        // идея пересылать не строку а байты
        return Numeric.toHexString(signedMessage);
    }

    private  org.web3j.crypto.RawTransaction ToModel(RawTransaction rawTransaction){
        return org.web3j.crypto.RawTransaction.createTransaction(
                BigInteger.valueOf(rawTransaction.getNonce()),
                BigInteger.valueOf(rawTransaction.getGas()),
                BigInteger.valueOf(rawTransaction.getGasLimit()),
                rawTransaction.getAddressTo(),
                BigInteger.ZERO,
                rawTransaction.getData()
        );
    }
}

package service;

import androidx.lifecycle.LiveData;

import repository.TransactionRespoitory;
import service.utils.Result;

public class TransactionSender {

    private final TransactionRespoitory transactionRespoitory;

    public TransactionSender() {
        transactionRespoitory = new TransactionRespoitory();
    }
    public void sendSignedTransaction(String transactionHex){
        transactionRespoitory.sendTransaction(transactionHex);
    }

    public LiveData<Result<Void>> getSendTransactionResult(){
        return transactionRespoitory.getSendTransactionResult();
    }
}

/*
        try {
            setupBouncyCastle();
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();

            Credentials credentials = Credentials
                    .create(ecKeyPair);

            System.out.println(ecKeyPair.getPrivateKey().toString(16));
            System.out.println(credentials.getEcKeyPair().getPrivateKey());
            System.out.println(credentials.getEcKeyPair().getPublicKey());
            System.out.println(credentials.getAddress());

        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

*/

//  Credentials credentials = Credentials
//  .create("0xb8126ececab475ff0df30799439cee9d340b12dad5c3fb380315686eacf014ca");
/*
        Credentials credentials = Credentials.create("0x93c27fab2f6184bc1c96413a386fb3ff1cc272c818a20d24f9cd520a203a3735");

        RawTransaction rawTransaction  = RawTransaction
                .createTransaction(
                        new BigInteger("3"),
                        new BigInteger("20000000000"),
                        new BigInteger("10021975"),
                        "25B9a573399CF9D1E50fcdE89aB8782271531CeE",
                        BigInteger.ZERO,
                        "b9ec3d09000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000000100000000000000000000000025b9a573399cf9d1e50fcde89ab8782271531cee00000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000024ef00ef43000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000");


        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hex = Numeric.toHexString(signedMessage);

*/
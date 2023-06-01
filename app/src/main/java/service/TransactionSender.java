package service;

import androidx.lifecycle.LiveData;

import repository.TransactionRespoitory;
import service.utils.Result;

public class TransactionSender {

    private final TransactionRespoitory transactionRespoitory;

    public TransactionSender(String token) {
        transactionRespoitory = new TransactionRespoitory(token);
    }
    public void sendSignedTransaction(String transactionHex){
        transactionRespoitory.sendTransaction(transactionHex);
    }

    public LiveData<Result<Void>> getSendTransactionResult(){
        return transactionRespoitory.getSendTransactionResult();
    }
}
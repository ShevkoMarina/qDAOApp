package service;

import androidx.lifecycle.LiveData;

import repository.TransactionRespoitory;

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

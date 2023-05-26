package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import model.ProposalInfo;

import model.RawTransaction;
import repository.ProposalRepository;
import service.utils.Result;
import service.TransactionSender;
import service.TransactionSigner;

public class ProposalVotingViewModel extends AndroidViewModel {

    private final ProposalRepository proposalRepository;
    private final TransactionSender transactionSender;
    private  final TransactionSigner transactionSigner;

    public ProposalVotingViewModel(@NonNull Application application) {
        super(application);

        proposalRepository = new ProposalRepository();
        transactionSender = new TransactionSender();
        transactionSigner = new TransactionSigner();
    }

    public void voteForProposal(long proposalId){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);
        proposalRepository.voteProposal(proposalId, userId, true);
    }

    public void voteAgainstProposal(long proposalId){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        proposalRepository.voteProposal(proposalId, userId, false);
    }

    public LiveData<Result<RawTransaction>> getVoteProposalTransactionResult(){
        return proposalRepository.getVoteProposalTransaction();
    }

    public void signAndSendVoteProposalTransaction(RawTransaction transaction){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");
        privateKey = "0x2c72f5cc094ff6beb9c48e8ce90f2fa894473ee097f715b39d5428e493f46963";

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }

    // todo узнать сработает ли это
    public LiveData<Result<Void>> getVoteProposalTransactionSendResult(){
        return transactionSender.getSendTransactionResult();
    }

    public LiveData<Result<ProposalInfo>> getProposalInfo(long proposalId){
        return proposalRepository.getProposalInfoById(proposalId);
    }
}

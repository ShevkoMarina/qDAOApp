package view_model;

import android.app.Application;

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
        int userId = 1;
        proposalRepository.voteProposal(proposalId, userId, true);
    }

    public void voteAgainstProposal(long proposalId){
        int userId = 1;

        proposalRepository.voteProposal(proposalId, userId, false);
    }

    public LiveData<Result<RawTransaction>> getVoteProposalTransactionResult(){
        return proposalRepository.getVoteProposalTransaction();
    }

    public void signAndSendVoteProposalTransaction(RawTransaction transaction){

        String transactionHex = transactionSigner.SignTransaction(transaction);
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

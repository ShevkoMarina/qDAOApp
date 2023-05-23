package view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import model.ProposalType;
import model.RawTransaction;
import repository.ProposalRepository;
import service.utils.Result;
import service.TransactionSender;
import service.TransactionSigner;

public class ProposalCreationViewModel extends ViewModel {
    private final ProposalRepository proposalRepository;
    private final TransactionSender transactionSender;
    private final TransactionSigner transactionSigner;


    public ProposalCreationViewModel(){
        proposalRepository = new ProposalRepository();
        transactionSender = new TransactionSender();
        transactionSigner = new TransactionSigner();
    }


    public void createProposal(String name, String description, String proposalType, String newValue) {
        int userId = 1;
        int value = Integer.parseInt(newValue);

        proposalRepository.createProposal(name, description, Map(proposalType), userId, value);
    }

    public LiveData<Result<RawTransaction>> getProposalTransaction() {
        return proposalRepository.getCreateProposalTransaction();
    }

    public void signAndSendCreateProposalTransaction(RawTransaction transaction){
        String transactionHex = transactionSigner.SignTransaction(transaction);
        transactionSender.sendSignedTransaction(transactionHex);
    }

    public LiveData<Result<Void>> getProposalTransactionResult(){
        return transactionSender.getSendTransactionResult();
    }

    private ProposalType Map(String proposalType){
        switch (proposalType){
            case "Изменить период голосования":
                return ProposalType.UpdateVotingPeriod;
            case "Изменить кворум":
                return ProposalType.UpdateQuorum;
            default:
                return ProposalType.Unknown;
        }
    }
}

package view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import model.ProposalThin;
import repository.ProposalRepository;
import repository.TokenRepository;

public class MyProposalsViewModel extends ViewModel {
    private final ProposalRepository proposalRepository;
    private TokenRepository tokenRepository;
    private MutableLiveData<List<ProposalThin>> proposals;
    private LiveData<Long> balance;


    public MyProposalsViewModel() {
        proposalRepository = new ProposalRepository();
        tokenRepository = new TokenRepository();
    }

    public LiveData<List<ProposalThin>> getProposals() {
        long userId = 1L;
        if (proposals == null) {
            proposals = new MutableLiveData<>();
            proposals = proposalRepository.getProposals(userId);
        }
        return proposals;
    }


    public LiveData<Long> getBalance() {
        if (balance == null) {
            balance = new MutableLiveData<>();
            balance = tokenRepository.getBalance("0x6955887bC161507746B5E83aE2603347F0599725");
        }
        return balance;
    }
}
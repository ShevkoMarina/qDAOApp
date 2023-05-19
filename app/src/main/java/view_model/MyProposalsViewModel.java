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
    private MutableLiveData<List<ProposalThin>> proposals;


    public MyProposalsViewModel() {
        proposalRepository = new ProposalRepository();
    }

    public LiveData<List<ProposalThin>> getProposals() {
        long userId = 1L;
        if (proposals == null) {
            proposals = new MutableLiveData<>();
            proposals = proposalRepository.getProposals(userId);
        }
        return proposals;
    }
}
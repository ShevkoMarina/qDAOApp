package view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import model.ProposalType;
import model.RawTransaction;
import repository.ProposalRepository;

public class ProposalCreationViewModel extends ViewModel {
    private final ProposalRepository proposalRepository;

    public ProposalCreationViewModel(){
        proposalRepository = new ProposalRepository();
    }


    public void createProposal(String name, String description, String newValue) {
        long userId = 1L;

        Long value = Long.parseLong(newValue);

        proposalRepository.createProposal(name, description, ProposalType.UpdateVotingPeriod, userId, value);
    }

    public LiveData<RawTransaction> getRawTransaction() {
        return proposalRepository.getRawTransaction();
    }
}

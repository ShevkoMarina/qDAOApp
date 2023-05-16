package view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.ProposalThin;
import repository.ProposalRepository;
import service.Result;

public class ProposalsManagementViewModel extends AndroidViewModel {

    private final ProposalRepository proposalRepository;

    public ProposalsManagementViewModel(@NonNull Application application) {
        super(application);
        proposalRepository = new ProposalRepository();
    }

    public LiveData<Result<List<ProposalThin>>> getProposalsForPromotion() {
       return proposalRepository.getProposalsForPromotion();
    }
}

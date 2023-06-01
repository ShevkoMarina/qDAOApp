package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.ProposalThin;
import repository.ProposalRepository;
import service.utils.Result;

public class ProposalsForVotingVewModel extends AndroidViewModel {

    private final ProposalRepository proposalRepository;

    public ProposalsForVotingVewModel(@NonNull Application application) {
        super(application);

        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String token = sp.getString("user_token", "");

        proposalRepository = new ProposalRepository(token);
    }

    public LiveData<Result<List<ProposalThin>>> getProposalsActiveForVoting() {
        return proposalRepository.getProposalsActiveForVoting();
    }
}

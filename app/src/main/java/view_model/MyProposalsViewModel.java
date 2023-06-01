package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import model.ProposalThin;
import repository.ProposalRepository;
import repository.TokenRepository;

public class MyProposalsViewModel extends AndroidViewModel {
    private final ProposalRepository proposalRepository;
    private MutableLiveData<List<ProposalThin>> proposals;


    public MyProposalsViewModel(@NonNull Application application) {
        super(application);

        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String token = sp.getString("user_token", "");

        proposalRepository = new ProposalRepository(token);
    }

    public LiveData<List<ProposalThin>> getProposals() {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);
        if (proposals == null) {
            proposals = new MutableLiveData<>();
            proposals = proposalRepository.getProposals(userId);
        }
        return proposals;
    }
}
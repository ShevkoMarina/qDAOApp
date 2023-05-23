package com.example.qdao.ui.proposal_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qdao.R;
import service.utils.ToastHelper;

import model.ProposalInfo;
import model.RawTransaction;
import service.utils.Result;
import view_model.ProposalPromotionDetailsViewModel;

public class ProposalPromotionDetailsActivity extends AppCompatActivity {

    private TextView proposalName;
    private TextView proposalDescription;
    private TextView proposalState;
    private TextView proposalVotesAgainst;
    private TextView proposalVotesFor;
    private ProposalInfo proposalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_promotion_details);

        ProposalPromotionDetailsViewModel viewModel = new ViewModelProvider(this).get(ProposalPromotionDetailsViewModel.class);

        long proposalId = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            proposalId = extras.getLong("proposalId");
        }

        proposalName = findViewById(R.id.proposal_promotion_name);
        proposalDescription = findViewById(R.id.promotion_proposal_description);
        proposalState = findViewById(R.id.promotion_proposal_state);
        proposalVotesAgainst = findViewById(R.id.promotion_votes_against);
        proposalVotesFor = findViewById(R.id.promotion_votes_for);
        Button promotionBtn = findViewById(R.id.promote_btn);

        viewModel.getProposalInfo(proposalId).observe(this, proposalInfoResult -> {
            if (proposalInfoResult.isSuccess()) {
                proposalInfo = proposalInfoResult.getData();
                setProposalInfo(proposalInfo);
            }
        });

        viewModel.getPromotionTransactionResult().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    viewModel.sendPromotionTransaction(rawTransactionResult.getData());
                } else {
                    ToastHelper.make(ProposalPromotionDetailsActivity.this, rawTransactionResult.getErrorMessage());
                }
            }
        });

        promotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.promoteProposal(proposalInfo);
            }
        });
    }

    private void setProposalInfo(ProposalInfo proposalInfo){
        proposalName.setText(proposalInfo.getName());
        proposalDescription.setText(proposalInfo.getDescription());
        proposalState.setText(proposalInfo.getState());
        proposalVotesAgainst.setText(String.valueOf(proposalInfo.getVotesAgainst()));
        proposalVotesFor.setText(String.valueOf(proposalInfo.getVotesFor()));
    }
}
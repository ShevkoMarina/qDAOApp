package com.example.qdao.ui.voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qdao.R;
import service.utils.ToastHelper;

import model.RawTransaction;
import service.utils.Result;
import view_model.ProposalVotingViewModel;

public class ProposalVotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_voting);

        Button voteForBtn = findViewById(R.id.vote_for_btn);
        Button voteAgainstBtn = findViewById(R.id.vote_against_btn);
        TextView proposalNameTV = findViewById(R.id.voting_proposal_name);
        TextView proposalDescriptionTV = findViewById(R.id.voting_proposal_description);
        TextView votesForTV = findViewById(R.id.votes_for);
        TextView votesAgainstTV = findViewById(R.id.votes_against);

        ProposalVotingViewModel viewModel = new ViewModelProvider(this).get(ProposalVotingViewModel.class);

        long proposalId = 1;
        viewModel.getProposalInfo(proposalId).observe(this, proposalsResult -> {
            if (proposalsResult != null) {
                if (proposalsResult.isSuccess()) {
                    proposalNameTV.setText(proposalsResult.getData().getName());
                    proposalDescriptionTV.setText(proposalsResult.getData().getDescription());
                    votesForTV.setText(String.valueOf(proposalsResult.getData().getVotesFor()));
                    votesAgainstTV.setText(String.valueOf(proposalsResult.getData().getVotesAgainst()));
                }
            }
        });


        viewModel.getVoteProposalTransactionResult().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    ToastHelper.make(ProposalVotingActivity.this, "Транзакция успешно сгенерирована");
                    viewModel.signAndSendVoteProposalTransaction(rawTransactionResult.getData());
                } else {
                    ToastHelper.make(ProposalVotingActivity.this, rawTransactionResult.getErrorMessage());
                }
            }
        });

        viewModel.getVoteProposalTransactionSendResult().observe(this, new Observer<Result<Void>>() {
            @Override
            public void onChanged(Result<Void> result) {
                if (result.isSuccess()) {
                    ToastHelper.make(ProposalVotingActivity.this, "Транзакция успешно отправлена");
                } else {
                    ToastHelper.make(ProposalVotingActivity.this, result.getErrorMessage());
                }
            }
        });

        voteForBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int proposalId = 1;
                viewModel.voteForProposal(proposalId);
            }
        });

        voteAgainstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int proposalId = 1;
                viewModel.voteAgainstProposal(proposalId);
            }
        });

    }
}
package com.example.qdao.ui.proposals_for_voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.qdao.R;
import service.utils.ToastHelper;

import com.example.qdao.ui.voting.ProposalVotingActivity;

import java.util.ArrayList;
import java.util.List;

import model.ProposalThin;
import view_model.ProposalsForVotingVewModel;

public class ProposalsForVotingActivity extends AppCompatActivity implements ProposalsForVotingAdapter.OnProposalListener{
    private ProposalsForVotingAdapter adapter;
    private List<ProposalThin> proposalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposals_for_voting);

        ProposalsForVotingVewModel viewModel = new ViewModelProvider(this).get(ProposalsForVotingVewModel.class);

        RecyclerView recyclerView = findViewById(R.id.proposals_for_voting_rv);

        adapter = new ProposalsForVotingAdapter(proposalList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getProposalsActiveForVoting().observe(this, proposalsResult -> {
            if (proposalsResult != null) {
                if (proposalsResult.isSuccess()) {
                    proposalList = proposalsResult.getData();
                    adapter.setProposals(proposalList);
                }
                else {
                    ToastHelper.make(ProposalsForVotingActivity.this, proposalsResult.getErrorMessage());
                }
            }
        });
    }

    @Override
    public void onProposalItemClick(int position) {
        Intent intent = new Intent(ProposalsForVotingActivity.this, ProposalVotingActivity.class);
        long proposalId = proposalList.get(position).getId();
        intent.putExtra("proposalId", proposalId);
        startActivity(intent);
    }
}
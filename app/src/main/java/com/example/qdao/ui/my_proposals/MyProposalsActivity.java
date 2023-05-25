package com.example.qdao.ui.my_proposals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qdao.R;
import com.example.qdao.ui.proposal_creation.ProposalCreationActivity;
import com.example.qdao.ui.proposal_details.ProposalDetailsActivity;
import com.example.qdao.ui.proposals_for_voting.ProposalsForVotingActivity;

import java.util.ArrayList;
import java.util.List;

import model.ProposalThin;
import view_model.MyProposalsViewModel;

public class MyProposalsActivity extends AppCompatActivity implements ProposalItemsAdapter.OnProposalListener{
    private ProposalItemsAdapter adapter;
    private List<ProposalThin> proposalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_proposals);

        MyProposalsViewModel viewModel = new ViewModelProvider(this).get(MyProposalsViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.proposalsRecyclerView);
        Button createProposalBtn = findViewById(R.id.createProposalButton);
        ImageButton navigationBtn = findViewById(R.id.navigation_btn);
        LinearLayout navigationLayout = findViewById(R.id.navigation_layout);

        Button toVotingBtn = findViewById(R.id.to_voting_btn);
        toVotingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProposalsActivity.this, ProposalsForVotingActivity.class);
                startActivity(intent);
            }
        });

        Button toBalanceTb = findViewById(R.id.to_balance_btn);

        navigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationLayout.setVisibility(View.VISIBLE);
            }
        });

        createProposalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProposalsActivity.this, ProposalCreationActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ProposalItemsAdapter(proposalList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getProposals().observe(this, proposals -> {
            if (proposals != null) {
                adapter.setProposals(proposals);
            }
        });
    }

    @Override
    public void onProposalItemClick(int position) {
        Intent intent = new Intent(MyProposalsActivity.this, ProposalDetailsActivity.class);
        startActivity(intent);
        finish();
    }
}
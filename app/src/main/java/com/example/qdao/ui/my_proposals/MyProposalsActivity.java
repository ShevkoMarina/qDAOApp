package com.example.qdao.ui.my_proposals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import com.example.qdao.R;

import java.util.ArrayList;
import java.util.List;

import model.ProposalListDto;

import model.ProposalThin;
import view_model.MyProposalsViewModel;

public class MyProposalsActivity extends AppCompatActivity implements ProposalItemsAdapter.OnProposalListener{

    private ProposalItemsAdapter adapter;
    private List<ProposalListDto> proposalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_proposals);

        MyProposalsViewModel viewModel = new ViewModelProvider(this).get(MyProposalsViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.proposalsRecyclerView);
        Button createProposalBtn = findViewById(R.id.createProposalButton);

        /*
        viewModel.getProposals().observe(this, proposals -> {
            if (proposals != null) {
                List<ProposalListDto> proposalsList = Map(proposals);
                adapter.setProposals(proposalsList);
            }
        });
*/
        proposalList.add(new ProposalListDto("№1: Повысить кворум до 6%", ""));

        adapter = new ProposalItemsAdapter(proposalList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onProposalItemClick(int position) {
        // do on proposal item clock
    }

    private List<ProposalListDto> Map(List<ProposalThin> thinProposals){

        List<ProposalListDto> proposals = new ArrayList<>();

        for (ProposalThin proposal: thinProposals) {
            proposals.add(new ProposalListDto(proposal.getName(), "Hello its state"));
        }
        for (ProposalThin proposal: thinProposals) {
            proposals.add(new ProposalListDto(proposal.getName(), "Hello its state"));
        }
        for (ProposalThin proposal: thinProposals) {
            proposals.add(new ProposalListDto(proposal.getName(), "Hello its state"));
        }
        for (ProposalThin proposal: thinProposals) {
            proposals.add(new ProposalListDto(proposal.getName(), "Hello its state"));
        }
        for (ProposalThin proposal: thinProposals) {
            proposals.add(new ProposalListDto(proposal.getName(), "Hello its state"));
        }

        return proposals;
    }
}
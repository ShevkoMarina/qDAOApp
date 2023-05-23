package com.example.qdao.ui.proposal_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.qdao.R;
import service.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import model.ProposalThin;
import view_model.ProposalsManagementViewModel;

public class ProposalManagementActivity extends AppCompatActivity implements ProposalsPromotionAdapter.OnProposalListener {
    private ProposalsPromotionAdapter adapter;
    private List<ProposalThin> proposalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_management);

        ProposalsManagementViewModel viewModel = new ViewModelProvider(this).get(ProposalsManagementViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.admin_proposals_rv);

        adapter = new ProposalsPromotionAdapter(proposalList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getProposalsForPromotion().observe(this, proposalsResult -> {
            if (proposalsResult.isSuccess()){
                proposalList = proposalsResult.getData();
                adapter.setProposals(proposalList);
            } else {
                ToastHelper.make(ProposalManagementActivity.this, proposalsResult.getErrorMessage());
            }
        });
    }

    @Override
    public void onProposalItemClick(int position) {
        Intent intent = new Intent(ProposalManagementActivity.this, ProposalPromotionDetailsActivity.class);
        long proposalId = proposalList.get(position).getId();
        intent.putExtra("proposalId", proposalId);
        startActivity(intent);
    }
}
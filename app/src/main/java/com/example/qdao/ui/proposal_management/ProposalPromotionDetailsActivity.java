package com.example.qdao.ui.proposal_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
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
    private long proposalId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_promotion_details);

        ProposalPromotionDetailsViewModel viewModel = new ViewModelProvider(this).get(ProposalPromotionDetailsViewModel.class);

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

        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userRole = sp.getInt("user_role", -1);

        // Если принципал
        if (userRole == 2) {
            // Получать только пропозалы в статусе без кворума
            promotionBtn.setText("СОГЛАСОВАТЬ");
            promotionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.generateApproveProposalTransaction(proposalId);
                }
            });
        }
        else {
            if (proposalInfo.getState().equals("Принято")){
                promotionBtn.setText("В ОЧЕРЕДЬ");
            }
            promotionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.promoteProposal(proposalInfo);
                }
            });
        }


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

        viewModel.getApproveProposalTransaction().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()){
                    viewModel.approveProposal(rawTransactionResult.getData());
                    promotionBtn.setVisibility(View.INVISIBLE);
                }
                else {
                    ToastHelper.make(ProposalPromotionDetailsActivity.this, rawTransactionResult.getErrorMessage());
                }
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
package com.example.qdao.ui.proposal_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qdao.R;

import model.RawTransaction;
import service.utils.Result;
import service.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import model.ProposalThin;
import view_model.ProposalsManagementViewModel;

public class ProposalManagementActivity extends AppCompatActivity implements ProposalsPromotionAdapter.OnProposalListener {
    private ProposalsPromotionAdapter adapter;
    private List<ProposalThin> proposalList = new ArrayList<>();
    private boolean pendingSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_management);

        ProposalsManagementViewModel viewModel = new ViewModelProvider(this).get(ProposalsManagementViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.admin_proposals_rv);

        adapter = new ProposalsPromotionAdapter(proposalList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button approveBtn = findViewById(R.id.approve_btn);
        EditText migrationAddress = findViewById(R.id.migrationAddress);

        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userRole = sp.getInt("user_role", -1);

        viewModel.getPendingMigrationAddress().observe(this, pendingAddressResponse-> {
            if (pendingAddressResponse != null && pendingAddressResponse.isSuccess()) {
                if (pendingAddressResponse.getData().equals("0x0000000000000000000000000000000000000000")){
                    // Значит предложений на миграцию нет
                    pendingSet = false;
                } else {
                    migrationAddress.setText(pendingAddressResponse.getData());
                    pendingSet = true;
                }
            } else {
                ToastHelper.make(this, pendingAddressResponse.getErrorMessage());
            }
        });

        viewModel.getPendingTransaction().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    viewModel.signAndSendPendingMigrationTransaction(rawTransactionResult.getData());
                } else {
                    ToastHelper.make(ProposalManagementActivity.this, rawTransactionResult.getErrorMessage());
                }
            }
        });

        viewModel.getProposalsForPromotion().observe(this, proposalsResult -> {
            if (proposalsResult != null && proposalsResult.isSuccess()){
                // Если принципал
                if (userRole == 2) {
                    for (ProposalThin proposal : proposalsResult.getData()) {
                        if (proposal.getState() == 4) {
                            proposalList.add(proposal);
                        }
                    }
                    approveBtn.setText("Согласовать");
                    approveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.approveMigrationTransaction();
                        }
                    });
                }
                else {
                    if (!pendingSet) {
                        approveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.setPendingAddressTransaction(migrationAddress.getText().toString());
                            }
                        });
                    } else {
                        // Если администратор
                        proposalList = proposalsResult.getData();
                        approveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                 viewModel.setMigration();
                            }
                        });
                    }
                }

                adapter.setProposals(proposalList);
            } else {
                ToastHelper.make(ProposalManagementActivity.this, proposalsResult.getErrorMessage());
            }
        });


        viewModel.getApproveMigrationTransaction().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    viewModel.signAndSendApproveMigrationTransaction(rawTransactionResult.getData());
                }
                ToastHelper.make(ProposalManagementActivity.this, rawTransactionResult.getErrorMessage());
            }
        });

        viewModel.getSetMigrationTransaction().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    viewModel.signAndSendSetMigrationTransaction(rawTransactionResult.getData());
                }
                ToastHelper.make(ProposalManagementActivity.this, rawTransactionResult.getErrorMessage());
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
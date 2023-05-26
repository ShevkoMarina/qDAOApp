package com.example.qdao.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qdao.R;
import com.example.qdao.ui.create_dao.CreateDaoActivity;
import com.example.qdao.ui.my_proposals.MyProposalsActivity;

import model.RawTransaction;
import service.utils.Result;
import service.utils.ToastHelper;
import view_model.DaoSettingsViewModel;
import view_model.MyProposalsViewModel;

public class DaoSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao_settings);

        DaoSettingsViewModel viewModel = new ViewModelProvider(this).get(DaoSettingsViewModel.class);

        Button applyPrincipalsBtn = findViewById(R.id.apply_principals);
        Button applyTokensBtn = findViewById(R.id.apply_tokens);
        TextView tv = findViewById(R.id.settings_totalSupply);
        EditText auditorLoginET = findViewById(R.id.add_auditor_address);
        EditText requiredApprovalsET = findViewById(R.id.required_approvals);
        EditText transferTokensAddressET = findViewById(R.id.transfer_tokens_address);
        EditText transferTokensAmountET = findViewById(R.id.transfer_tokens_amount);

        applyPrincipalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addPrincipals(auditorLoginET.getText().toString(), requiredApprovalsET.getText().toString());
            }
        });

        applyTokensBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.transferTokens(transferTokensAddressET.getText().toString(), transferTokensAmountET.getText().toString());
            }
        });


        viewModel.getTransferTokensTransaction().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    viewModel.signAndSendTransferTokensTransaction(rawTransactionResult.getData());
                } else {
                    ToastHelper.make(DaoSettingsActivity.this, rawTransactionResult.getErrorMessage());
                }
            }
        });
    }
}
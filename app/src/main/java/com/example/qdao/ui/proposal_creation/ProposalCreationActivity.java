package com.example.qdao.ui.proposal_creation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.qdao.R;

import model.ProposalType;
import service.utils.ToastHelper;

import model.RawTransaction;
import service.utils.Result;
import view_model.ProposalCreationViewModel;

public class ProposalCreationActivity extends AppCompatActivity {

    private Button createProposalBtn;
    private EditText nameET;
    private EditText descriptionET;
    private EditText newValueET;
    private Spinner proposalActionSpinner;

    private TextView currentValueTV;
    private ProposalCreationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_creation);

        proposalActionSpinner = findViewById(R.id.proposals_type_spinner);
        String[] options = {"Изменить период голосования", "Изменить кворум", "Изменить период задержки до голосования"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        proposalActionSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        createProposalBtn = findViewById(R.id.createProposalBtn);
        nameET = findViewById(R.id.nameET);
        descriptionET = findViewById(R.id.descriptionET);
        newValueET = findViewById(R.id.new_value_ET);
        currentValueTV = findViewById(R.id.current_value_TV);

        createProposalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCreateProposalBtnClick();
            }
        });

        viewModel = new ViewModelProvider(this).get(ProposalCreationViewModel.class);

        viewModel.getUpdatableSettings().observe(this, updatableSettingsInfoResult -> {
            if (updatableSettingsInfoResult != null) {
                if (updatableSettingsInfoResult.isSuccess()){
                    ProposalType selectedOptionType = viewModel.Map(proposalActionSpinner.getSelectedItem().toString());
                    if (selectedOptionType == ProposalType.UpdateQuorum) {
                        currentValueTV.setText(String.valueOf(updatableSettingsInfoResult.getData().getQuorum()));
                    } else if (selectedOptionType == ProposalType.UpdateVotingDelay) {
                        currentValueTV.setText(String.valueOf(updatableSettingsInfoResult.getData().getVotingDelay()));
                    }
                    else if (selectedOptionType == ProposalType.UpdateVotingPeriod) {
                        currentValueTV.setText(String.valueOf(updatableSettingsInfoResult.getData().getVotingPeriod()));
                    }
                    else {
                        currentValueTV.setText(String.valueOf(updatableSettingsInfoResult.getData().getVotingPeriod()));
                        ToastHelper.make(ProposalCreationActivity.this, updatableSettingsInfoResult.getErrorMessage());
                    }
                }
            }
        });

        viewModel.getProposalTransactionResult().observe(this, new Observer<Result<Void>>() {
            @Override
            public void onChanged(Result<Void> result) {
                if (result.isSuccess()){
                    ToastHelper.make(ProposalCreationActivity.this, "Предложение успешно создано");
                } else {
                    ToastHelper.make(ProposalCreationActivity.this, result.getErrorMessage());
                }
            }
        });

        viewModel.getProposalTransaction().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()){
                    viewModel.signAndSendCreateProposalTransaction(rawTransactionResult.getData());
                }
                else {
                    ToastHelper.make(ProposalCreationActivity.this, rawTransactionResult.getErrorMessage());
                }
            }
        });
    }

    private void OnCreateProposalBtnClick(){

        viewModel.createProposal(
                nameET.getText().toString(),
                descriptionET.getText().toString(),
                proposalActionSpinner.getSelectedItem().toString(),
                newValueET.getText().toString());;
    }
}
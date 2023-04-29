package com.example.qdao.ui.proposal_creation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.example.qdao.R;
import com.example.qdao.ui.SignFragment;

import model.RawTransaction;
import view_model.ProposalCreationViewModel;

public class ProposalCreationActivity extends AppCompatActivity {

    private Button createProposalBtn;
    private EditText nameET;
    private EditText descriptionET;
    private EditText newValueET;
    private FrameLayout signFragment;
    private ProposalCreationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_creation);

        Spinner proposalActionSpinner = findViewById(R.id.action_spinner);
        String[] options = {"Изменить период голосования", "Изменить кворум"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        proposalActionSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        createProposalBtn = findViewById(R.id.createProposalBtn);
        nameET = findViewById(R.id.nameET);
        descriptionET = findViewById(R.id.descriptionET);
        newValueET = findViewById(R.id.new_value_ET);
        signFragment = findViewById(R.id.fragment_sign);
        signFragment.setVisibility(View.INVISIBLE);

        createProposalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCreateProposalBtnClick();
            }
        });

        viewModel = new ViewModelProvider(this).get(ProposalCreationViewModel.class);

        //ShowSignTransactionFragment(new RawTransaction(1, 2, 3,"", "", 1));

        viewModel.getRawTransaction().observe(this, new Observer<RawTransaction>() {
            @Override
            public void onChanged(RawTransaction rawTransaction) {
                if (rawTransaction != null){
                    //ShowSignTransactionFragment(rawTransaction);
                }
            }
        });
    }

    private void ShowSignTransactionFragment(RawTransaction rawTransaction){
        signFragment.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
              .setReorderingAllowed(true)
              .add(R.id.fragment_sign, SignFragment.class, null)
              .commit();
    }

    private void OnCreateProposalBtnClick(){

        viewModel.createProposal(
                nameET.getText().toString(),
                descriptionET.getText().toString(),
                newValueET.getText().toString());;
    }
}
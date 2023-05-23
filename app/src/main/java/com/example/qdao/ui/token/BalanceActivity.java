package com.example.qdao.ui.token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qdao.R;
import service.utils.ToastHelper;

import model.RawTransaction;
import service.utils.Result;
import view_model.TokenViewModel;

public class BalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        TokenViewModel viewModel = new ViewModelProvider(this).get(TokenViewModel.class);

        Button delegateBtn = findViewById(R.id.delegate_btn);
        TextView balanceTV = findViewById(R.id.balance_tv);
        TextView weightTV = findViewById(R.id.weight_tv);
        EditText delegateeET = findViewById(R.id.delegatee_login);

        // Получить баланс и вес голосов пользователя
        viewModel.getUserTokenInfo().observe(this, tokenInfoResult -> {
            if (tokenInfoResult != null) {
                if (tokenInfoResult.isSuccess()) {
                    balanceTV.setText(String.valueOf(tokenInfoResult.getData().getBalance()));
                    weightTV.setText(String.valueOf(tokenInfoResult.getData().getWeight()));
                }
                else {
                    ToastHelper.make(BalanceActivity.this, tokenInfoResult.getErrorMessage());
                }
            }
        });

        // Проделегировать права голоса
        delegateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewModel.delegateVotes(delegateeET.getText().toString());
            }
        });

        viewModel.getDelegateVotesTransactionResult().observe(this, new Observer<Result<RawTransaction>>() {
            @Override
            public void onChanged(Result<RawTransaction> rawTransactionResult) {
                if (rawTransactionResult.isSuccess()) {
                    // Отправить транзакцию
                } else {
                    ToastHelper.make(BalanceActivity.this, rawTransactionResult.getErrorMessage());
                }
            }
        });
    }
}
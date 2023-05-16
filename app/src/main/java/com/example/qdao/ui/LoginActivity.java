package com.example.qdao.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qdao.R;
import com.example.qdao.ui.create_dao.CreateDaoActivity;
import com.example.qdao.ui.my_proposals.MyProposalsActivity;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import remote.user_models.AuthorizeUserResponseDto;
import service.AccountCreator;
import service.Result;
import view_model.AuthorizationViewModel;
import view_model.MyProposalsViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText loginET;
    private EditText passwordET;
    private AuthorizationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(AuthorizationViewModel.class);

        viewModel.GetUserAuthorizationResult().observe(this, new Observer<Result<AuthorizeUserResponseDto>>() {
            @Override
            public void onChanged(Result<AuthorizeUserResponseDto> authorizeUserResponseDtoResult) {
                if (authorizeUserResponseDtoResult.isSuccess()){
                    // todo
                }
                else {
                    ToastHelper.make(LoginActivity.this, authorizeUserResponseDtoResult.getErrorMessage());
                }
            }
        });

        viewModel.GetUserRegistrationResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.isSuccess()){
                    // todo
                }
                else {
                    ToastHelper.make(LoginActivity.this, result.getErrorMessage());
                }
            }
        });

        Button createAccountBtn = findViewById(R.id.register_btn);
        Button loginBtn = findViewById(R.id.login_btn);
        passwordET = findViewById(R.id.password);
        loginET = findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.OnLoginBtnClicked(loginET.getText().toString(), passwordET.getText().toString());
            }
        });


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.OnCreateAccountBtnClicked(loginET.getText().toString(), passwordET.getText().toString());
            }
        });

/*
        try {
            setupBouncyCastle();
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();

            Credentials credentials = Credentials
                    .create(ecKeyPair);

            System.out.println(ecKeyPair.getPrivateKey().toString(16));
            System.out.println(credentials.getEcKeyPair().getPrivateKey());
            System.out.println(credentials.getEcKeyPair().getPublicKey());
            System.out.println(credentials.getAddress());

        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

*/

      //  Credentials credentials = Credentials
      //  .create("0xb8126ececab475ff0df30799439cee9d340b12dad5c3fb380315686eacf014ca");
/*
        Credentials credentials = Credentials.create("0x93c27fab2f6184bc1c96413a386fb3ff1cc272c818a20d24f9cd520a203a3735");

        RawTransaction rawTransaction  = RawTransaction
                .createTransaction(
                        new BigInteger("3"),
                        new BigInteger("20000000000"),
                        new BigInteger("10021975"),
                        "25B9a573399CF9D1E50fcdE89aB8782271531CeE",
                        BigInteger.ZERO,
                        "b9ec3d09000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000000100000000000000000000000025b9a573399cf9d1e50fcde89ab8782271531cee00000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000024ef00ef43000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000");


        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hex = Numeric.toHexString(signedMessage);

*/
    }

    private void OpenCreateDaoPage(){
        Intent intent = new Intent(LoginActivity.this, CreateDaoActivity.class);
        startActivity(intent);
        finish();
    }
}



package service;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;

import service.utils.Result;

/**
 * Creates an account in blockchain for new user
 */
public class AccountCreator {

    public Result<String> generateAndStoreInSharedPreferances(Context context) {

        try {
            setupBouncyCastle();
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();

            String privateKey =  ecKeyPair.getPrivateKey().toString();
            String publicKey = ecKeyPair.getPublicKey().toString();

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("private_key", privateKey);
            String userAccount = publicKeyToAddress(publicKey);
            editor.putString("account", userAccount);
            editor.apply();

            return Result.success(userAccount);
        }
        catch (Exception ex){
            return Result.error("Ошибка генерации аккаунта");
        }
    }

    public static String publicKeyToAddress(String publicKeyString) {
        // Convert the public key string to bytes
        byte[] publicKeyBytes = Base64.decode(publicKeyString, Base64.DEFAULT);

        try {
            // Create a message digest instance with Keccak-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");

            // Calculate the hash of the public key
            byte[] hashBytes = digest.digest(publicKeyBytes);

            // Take the last 20 bytes of the hash as the Ethereum address
            byte[] addressBytes = new byte[20];
            System.arraycopy(hashBytes, hashBytes.length - 20, addressBytes, 0, 20);

            // Convert the address bytes to a hexadecimal string
            String address = new BigInteger(1, addressBytes).toString(16);

            // Add leading zeros to the address if necessary
            while (address.length() < 40) {
                address = "0" + address;
            }

            // Add the "0x" prefix to the address
            address = "0x" + address;

            return address;

        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return null;
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }
}

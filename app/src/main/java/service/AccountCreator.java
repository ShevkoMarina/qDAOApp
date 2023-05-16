package service;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyProtection;
import android.util.Base64;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
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
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;
import javax.security.cert.X509Certificate;

/**
 * Creates an account in blockchain for new user
 */
public class AccountCreator {

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String KEY_ALIAS = "my_key_alias";
    private static final String KEY_PASSWORD = "my_key_password";

    public void GenerateNewAccountByMe() throws CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
        try {
            keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(
                    "my_key_alias",
                    KeyProperties.PURPOSE_SIGN)
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setAlgorithmParameterSpec(new ECGenParameterSpec("prime256v1"))
                 //   .setUserAuthenticationRequired(true) прикольно было бы добавить биометрию
                    .build());
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Get the private key
        PrivateKey privateKey = keyPair.getPrivate();

        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        keyStore.load(null);

        KeyStore.Entry entry = keyStore.getEntry("my_key_alias", null);
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            Field field = null;
            try {
                field = ECPrivateKey.class.getDeclaredField("s");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            byte[] privateKeyBytes = new byte[0];
            try {
                privateKeyBytes = (byte[]) field.get(privateKey);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            ECKeyPair ecKeyPair = ECKeyPair.create(privateKeyBytes);
        } else {
            throw new KeyStoreException("Unexpected entry type: " + entry.getClass().getName());
        }

        byte[] message = "Hello, world!".getBytes();

        /*
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withECDSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        try {
            signature.update(message);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
        try {
            byte[] signatureBytes = signature.sign();

        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }

         */
    }

    public void generateAndStoreInEcryptedSharedPreferances(Context context) throws
            InvalidAlgorithmParameterException,
            NoSuchAlgorithmException,
            NoSuchProviderException {

        setupBouncyCastle();
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("private_key", ecKeyPair.getPrivateKey().toString());
        editor.putString("public_key", ecKeyPair.getPublicKey().toString());
        editor.apply();
    }

    private void savePrivateKey(BigInteger privateKey, Context context) {
        try {

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("private_key", "hello");
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BigInteger getPrivateKey(Context context) {
        try {
            SharedPreferences prefs = context.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE);
            String privateKey = prefs.getString("private_key", "unknown");
            return new BigInteger(privateKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void generateECKeyPairAndStore() throws Exception {
        // Generate key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
      //  ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime256v1");
        keyPairGenerator.initialize(ecGenParameterSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();



        // Store private key in Android Keystore system
       // KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
      //  keyStore.load(null);

        // Retrieve private key from Android Keystore system
       // ECPrivateKey privateKey = (ECPrivateKey) keyStore.getKey(KEY_ALIAS, null);

        // Get public key
        PublicKey publicKey = keyPair.getPublic();

        // Get addresses from public key
        String address = publicKeyToAddress(Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));

        // Print the generated key pair and address
       // System.out.println("ECKeyPair: " + keyPair.toString());
        System.out.println("Address: " + address);

        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);



        //KeyStore.Entry entry = new KeyStore.PrivateKeyEntry(keyPair.getPrivate(), null);
        keyStore.setEntry(
                "key2",
                new KeyStore.PrivateKeyEntry(keyPair.getPrivate(), null),
                null);

        // Get private key from Android Keystore system
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, null);

        /*
        Credentials credentials = Credentials
                .create(Numeric.toHexString(privateKey.getEncoded()));
                *
         */

        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        byte[] data = "Hello, world!".getBytes();
        signature.update(data);
        byte[] signatureBytes = signature.sign();

        System.out.println(Numeric.toHexString(signatureBytes));
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

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
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

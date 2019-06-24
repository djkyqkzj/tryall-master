package com.zmj.example.tryall.ui;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.ui.fragment.FingerprintDialogFragment;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class LoginAct extends AppCompatActivity {
	private static final String DEFAULT_KEY_NAME = "default_key";
	KeyStore keyStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (supportFingerPrint()){
			initKey();
			initCipher();
		}
	}

	private boolean supportFingerPrint() {
		if (Build.VERSION.SDK_INT < 23){
			Toast.makeText(this,"您的系统版本太低，不支持指纹功能",Toast.LENGTH_SHORT).show();
			return false;
		}else {
			KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
			FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
			if(!fingerprintManager.isHardwareDetected()){
				Toast.makeText(this,"您的手机不支持指纹功能",Toast.LENGTH_SHORT).show();
				return false;
			}else if (!keyguardManager.isKeyguardSecure()){
				Toast.makeText(this,"您还未设置指纹，请先添加指纹",Toast.LENGTH_SHORT).show();
				return false;
			}else if(!fingerprintManager.hasEnrolledFingerprints()){
				Toast.makeText(this,"您至少需要在系统中添加一个指纹",Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
	}
	//载入key
	@TargetApi(23)
	private void initKey(){
		try{
			keyStore = KeyStore.getInstance("AndroidKeyStore");
			keyStore.load(null);
			KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
			KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
					KeyProperties.PURPOSE_ENCRYPT|
					KeyProperties.PURPOSE_DECRYPT)
					.setBlockModes(KeyProperties.BLOCK_MODE_CBC)
					.setUserAuthenticationRequired(true)
					.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
			keyGenerator.init(builder.build());
			keyGenerator.generateKey();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//加载Cihper
	private void initCipher(){
		try{
			SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME,null);
			Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
					+ KeyProperties.BLOCK_MODE_CBC + "/"
					+ KeyProperties.ENCRYPTION_PADDING_PKCS7);
			cipher.init(Cipher.ENCRYPT_MODE,key);
			shoeFingerPrintDialog(cipher);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void shoeFingerPrintDialog(Cipher cipher) {
		FingerprintDialogFragment fragment = new FingerprintDialogFragment();
		fragment.setCipher(cipher);
		fragment.show(getFragmentManager(),"fingerprint");
	}

	public void onAuthenticated(){
		Intent intent = new Intent(this, FingerPrintSuccessAct.class);
		startActivity(intent);
		finish();
	}
}

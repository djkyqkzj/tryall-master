package com.zmj.example.tryall.ui.fragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.ui.LoginAct;

import javax.crypto.Cipher;

/**
 * Created by ZMJ
 * on 2018/8/28
 */
@TargetApi(23)
	public class FingerprintDialogFragment extends DialogFragment {
	private FingerprintManager fingerprintManager;
	private CancellationSignal mCancellationSignal;
	private Cipher mCipher;
	private TextView error_msg;
	private LoginAct mLoginAct;
	private boolean isSelfCanceled;//标识是否是用户自己取消认证

	public void setCipher(Cipher Cipher) {
		mCipher = Cipher;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mLoginAct = (LoginAct) getActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fingerprintManager = getContext().getSystemService(FingerprintManager.class);
		setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Material_Light_Dialog);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fingerprint_dialog,container,false);
		error_msg = view.findViewById(R.id.error_msg);
		TextView cancel = view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
				stopListening();
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		//开始指纹认证监听
		startListening(mCipher);
	}

	@Override
	public void onPause() {
		super.onPause();
		//停止指纹认证监听
		stopListening();
	}

	private void startListening(Cipher cipher) {
		isSelfCanceled = false;
		mCancellationSignal = new CancellationSignal();
		fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
			@Override
			public void onAuthenticationError(int errorCode, CharSequence errString) {
				if (!isSelfCanceled){
					error_msg.setText(errString);
					if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT){
						Toast.makeText(mLoginAct,errString,Toast.LENGTH_SHORT).show();
						dismiss();
					}
				}
			}

			@Override
			public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
				error_msg.setText(helpString);
			}

			@Override
			public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
				Toast.makeText(mLoginAct,"指纹认证成功",Toast.LENGTH_SHORT).show();
				mLoginAct.onAuthenticated();
			}

			@Override
			public void onAuthenticationFailed() {
				error_msg.setText("指纹认证失败，请在试一次");
			}
		},null);
	}

	private void stopListening(){
		if (mCancellationSignal != null){
			mCancellationSignal.cancel();
			mCancellationSignal = null;
			isSelfCanceled = true;
		}
	}
}

package afpcsoft.com.br.bestplaces.controller;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.zip.Inflater;

import afpcsoft.com.br.bestplaces.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FaceBookLoginActivityFragment extends Fragment {

    private CallbackManager callbackManager;
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private LoginButton loginButton;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            redirect(profile);
            if(profile != null) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Seja bem vindo " + profile.getName());
            }
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = Profile.getCurrentProfile();
                    redirect(profile);
                }
            });
            loginButton.setVisibility(View.GONE);


        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    private void redirect(Profile profile) {
        if(profile != null){
            Intent intent = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
            intent.putExtra("profile", profile);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public FaceBookLoginActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        redirect(profile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_book_login, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageLogin);
        textView = (TextView) view.findViewById(R.id.textLogin);
        button = (Button) view.findViewById(R.id.btnAvancar);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

package bad.twitterfriendschannel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import bad.twitterfriendschannel.gson.response.GCMRegistrationRes;
import bad.twitterfriendschannel.gson.response.LoginRes;
import bad.twitterfriendschannel.gson.response.RegisterRes;
import io.fabric.sdk.android.Fabric;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.*;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.litepal.tablemanager.Connector;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TAG = "LoginActivity";



    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;



    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    TwitterLoginButton twiLoginButton;
    private Button loginButton;
    private Button registerButton;
    private CheckBox rememberCheckbox;

    private String usrName;
    private String pass;

    //broker client
    private String brokerURL = "";
    private String dataverseName = "";
    private String TWITTER_KEY = "";
    private String TWITTER_SECRET = "";
    private String accountPref = "";
    private String twitterPref = "";

    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        brokerURL = getString(R.string.broker_URL);
        TWITTER_KEY = getString(R.string.TWITTER_KEY);
        TWITTER_SECRET = getString(R.string.TWITTER_SECRET);
        dataverseName = getString(R.string.dataverse);

        accountPref = getString(R.string.accountPref);
        twitterPref = getString(R.string.twitterPref);

        System.out.println(FirebaseInstanceId.getInstance().getToken());//added
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        //Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        TwitterConfig.Builder builder=new TwitterConfig.Builder(this);
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());
        setContentView(R.layout.activity_login);
        //get database
        Connector.getDatabase();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        rememberCheckbox = (CheckBox) findViewById(R.id.login_checkbox);
        mPasswordView = (EditText) findViewById(R.id.password);

        populateAutoComplete();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        SharedPreferences pref = getSharedPreferences(accountPref, MODE_PRIVATE);
        if (pref.getBoolean("checked", false)) {
            rememberCheckbox.setChecked(true);
            mEmailView.setText(pref.getString("usrName", ""));
            mPasswordView.setText(pref.getString("pass", ""));
        }

        twiLoginButton = new TwitterLoginButton(this);
        twiLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                long userId  = session.getUserId();
                //register broker using userId
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                SharedPreferences.Editor editor = getSharedPreferences(twitterPref, MODE_PRIVATE).edit();
                editor.putLong("twitterUserId", userId);
                editor.putString("token", token);
                editor.putString("secret", secret);
                editor.commit();

                Request request = OkHttpUtil.getInstance(getApplicationContext()).register(dataverseName, usrName, pass ,usrName);//edited
                OkHttpUtil.getInstance(getApplicationContext()).request(request, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.v(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (res!= null && !res.equals("")) {
                                    RegisterRes registerRes = gson.fromJson(res, RegisterRes.class);
                                    if (registerRes.getStatus().equals("success")) {
                                        SharedPreferences.Editor editor = getSharedPreferences(accountPref, MODE_PRIVATE).edit();
                                        editor.putString("userId", registerRes.getUserId());
                                        editor.commit();
                                    } else {
                                        Toast.makeText(LoginActivity.this, registerRes.getError(), Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, registerRes.getError());
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
                Log.v(TAG, exception.toString());
            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
                Intent intent = getIntent();
                if (intent.hasExtra("notification")) {
                    LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                    broadcaster.sendBroadcast(intent);
                }
                SharedPreferences pref = getSharedPreferences(twitterPref, MODE_PRIVATE);
                Long userId = pref.getLong("twitterUserId", -1);

                final String usrName = mEmailView.getText().toString();
                final String pass = mPasswordView.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences(accountPref, MODE_PRIVATE).edit();
                if (rememberCheckbox.isChecked()) {
                    editor.putString("usrName", usrName);
                    editor.putString("pass", pass);
                    editor.putBoolean("checked", true);
                    editor.commit();
                } else {
                    editor.putBoolean("checked", false);
                    editor.commit();
                }
                if (userId == -1) {
                    //create database
                    Connector.getDatabase();
                    Toast.makeText(LoginActivity.this, "Please register!", Toast.LENGTH_SHORT).show();
                } else {
                    Connector.getDatabase();
                    Log.v("Broker is login", "Ture");
                    if (attemptLogin()) {
                        final Request request = OkHttpUtil.getInstance(getApplicationContext()).login(dataverseName, usrName, pass);
                        OkHttpUtil.getInstance(getApplicationContext()).request(request, new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                                Log.v(TAG, e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String res = response.body().string();
                                final String refreshedToken = FirebaseInstanceId.getInstance().getToken("693061861907", "FCM");
                                Log.v("RefreshToken", refreshedToken);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if ( res != null && !res.equals("")) {
                                            LoginRes loginRes = gson.fromJson(res, LoginRes.class);
                                            if (loginRes.getStatus().equals("success")) {
                                                SharedPreferences.Editor editor = getSharedPreferences(accountPref, MODE_PRIVATE).edit();
                                                editor.putString("userId", loginRes.getUserId());
                                                editor.putString("accessToken", loginRes.getAccessToken());
                                                editor.commit();
                                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                Request gcmRequest = OkHttpUtil.getInstance(getApplicationContext())
                                                        .setFCMRegistrationToken(dataverseName, loginRes.getUserId(), loginRes.getAccessToken(), refreshedToken);
                                                OkHttpUtil.getInstance(getApplicationContext()).request(gcmRequest, new okhttp3.Callback() {
                                                    @Override
                                                    public void onFailure(Call call, IOException e) {

                                                    }

                                                    @Override
                                                    public void onResponse(Call call, Response response) throws IOException {
                                                        String res = response.body().string();
                                                        GCMRegistrationRes gcmRes = gson.fromJson(res, GCMRegistrationRes.class);
                                                        if (gcmRes.getStatus().equals("success")) {
                                                            Log.v("GCM Token Refresh", "Success");
                                                        } else {
                                                            Log.v("GCM Token Refresh", gcmRes.getError());
                                                        }
                                                    }
                                                });
                                                Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                                                startActivity(map);
                                            } else {
                                                Toast.makeText(LoginActivity.this, loginRes.getError(), Toast.LENGTH_SHORT).show();
                                                Log.e(TAG, loginRes.getError());
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Information!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin()) {
                    usrName = mEmailView.getText().toString();
                    pass = mPasswordView.getText().toString();
                    twiLoginButton.performClick();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Information!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: "+ token);
                Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twiLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {
//        if (mAuthTask != null) {
//            return true;
//        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean valid = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            valid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            valid = false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            valid = false;
        }

        if (!valid) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
//        }
        return valid;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}





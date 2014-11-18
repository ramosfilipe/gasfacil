package boleiros.gas_facil.login;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import boleiros.gas_facil.Inicio;
import boleiros.gas_facil.R;

public class LoginSignup extends Activity implements InformacoesNovoUsuario.OnFragmentInteractionListener{
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_login_signup);
        // Locate EditTexts in main.xml
        getActionBar().hide();
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        username = (EditText) findViewById(R.id.campoUsername);
        password = (EditText) findViewById(R.id.campoSenha);
        username.setTextColor(Color.parseColor("#F5F5F5"));
        password.setTextColor(Color.parseColor("#F5F5F5"));
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (password.getText().toString().equals("Senha")) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setTextColor(Color.WHITE);
                    password.setText("");
                }
                return false;
            }
        });
        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(username.getText().toString().equals("Nome de usuário")) {
                    username.setTextColor(Color.WHITE);
                    username.setText("");
                }
                return false;
            }
        });
        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_NEXT){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setTextColor(Color.WHITE);
                    password.setText("");
                    return false;
                }
                return false;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    loginbutton.performClick();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.relativeInfos).setBackgroundColor(Color.parseColor("#BBDEFB"));
     //   findViewById(R.id.relativeInfoLogin).setBackgroundColor(Color.WHITE);
     //   findViewById(R.id.frameLogin).setBackgroundColor(Color.WHITE);
        // Locate Buttons in main.xml

        loginbutton.setBackgroundColor(Color.parseColor("#90CAF9"));
        signup.setBackgroundColor(Color.parseColor("#64B5F6"));
        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString().replaceAll(" ", "");
                passwordtxt = password.getText().toString();
                final ProgressDialog pDialog = ProgressDialog.show(LoginSignup.this, null,
                        "Carregando");
                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            LoginSignup.this,
                                            Inicio.class);
                                    startActivity(intent);
                                    pDialog.dismiss();
                                    finish();
                                } else {
                                    pDialog.dismiss();
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Usuário ou  senha inválidos.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Fragment fragment = new InformacoesNovoUsuario();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.relativeInfos,fragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (ParseUser.getCurrentUser().getObjectId() == null) {
            if (findViewById(R.id.campoUsername).getVisibility() == View.INVISIBLE) {
                Intent intent = new Intent(LoginSignup.this, Login.class);
                startActivity(intent);

            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
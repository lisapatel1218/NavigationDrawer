package com.example.navigationdrawer

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class social_login : AppCompatActivity() {
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googleBtn: ImageView
    private lateinit var mFacebookCallbackManager: CallbackManager
    private lateinit var facebookLoginButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)

        // Initialize callback manager
        mFacebookCallbackManager = CallbackManager.Factory.create()

        // Initialize AppEventsLogger
        AppEventsLogger.activateApp(application)
        setContentView(R.layout.activity_social_login)

        googleBtn = findViewById(R.id.google_btn)
        facebookLoginButton = findViewById(R.id.facebook)



        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc = GoogleSignIn.getClient(this, gso)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToSecondActivity()
        }


        googleBtn.setOnClickListener {
            signIn()
        }
        facebookLoginButton.setOnClickListener {
            // Perform Facebook login
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
        }

        LoginManager.getInstance().registerCallback(mFacebookCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // Handle successful login
                    navigateToNextActivity()
                }

                override fun onCancel() {
                    // Handle login cancellation
                    // You may show a message or perform any action here
                }

                override fun onError(error: FacebookException) {
                    // Handle login error
                    // You may show a message or perform any action here
                }
            })

    }

    private fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun navigateToNextActivity() {
        // Navigate to the second activity
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        finish() // Finish the current activity
    }

    private fun navigateToSecondActivity() {
        finish()
        val intent = Intent(this@social_login, SecondActivity::class.java)
        startActivity(intent)
    }
}

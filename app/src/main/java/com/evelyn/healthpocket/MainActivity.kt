package com.evelyn.healthpocket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.evelyn.healthpocket.ui.theme.HealthPocketTheme
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException

class MainActivity : ComponentActivity() {
    private var msalApp: PublicClientApplication? = null
    private val SCOPES = arrayOf("User.Read")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Load MSAL config từ res/raw/auth_config.json
        msalApp = PublicClientApplication(this.applicationContext, R.raw.auth_config)

        setContent {
            HealthPocketTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        onSignInClick = { signIn() }
                    )
                }
            }
        }
    }

    private fun signIn() {
        msalApp?.acquireToken(this, SCOPES, object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                val accessToken = authenticationResult.accessToken
                Log.d("MSAL", "✅ Access Token: $accessToken")
                // TODO: Gửi token này về backend hoặc chuyển sang DashboardActivity
            }

            override fun onError(exception: MsalException) {
                Log.e("MSAL", "Login failed: ${exception.message}")
            }

            override fun onCancel() {
                Log.d("MSAL", "⚠️ User cancelled login.")
            }
        })
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onSignInClick: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { onSignInClick() }) {
            Text("Sign in with Azure")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    HealthPocketTheme {
        LoginScreen(onSignInClick = {})
    }
}

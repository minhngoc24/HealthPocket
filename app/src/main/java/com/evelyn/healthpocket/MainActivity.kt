package com.evelyn.healthpocket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evelyn.healthpocket.ui.theme.HealthPocketTheme
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.IPublicClientApplication
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.exception.MsalException

class MainActivity : ComponentActivity() {

    private var msalApp: IPublicClientApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        PublicClientApplication.create(
            applicationContext,
            R.raw.msal_config,
            object : IPublicClientApplication.ApplicationCreatedListener {
                override fun onCreated(application: IPublicClientApplication) {
                    msalApp = application
                    Log.d("MSAL", "MSAL created")
                }
                override fun onError(exception: MsalException) {
                    Log.e("MSAL", "Create MSAL failed", exception)
                }
            }
        )

        setContent {
            HealthPocketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding),
                        onSignIn = { handlers ->
                            val scopes = arrayOf(
                                "openid", "profile", "offline_access"
                                // Nếu đã Expose API: thêm "api://<your-api>/access_as_user"
                            )
                            val app = msalApp
                            if (app == null) {
                                handlers.onError(IllegalStateException("MSAL not ready yet"))
                                return@Content
                            }
                            app.acquireToken(
                                this@MainActivity,
                                scopes,
                                object : AuthenticationCallback {
                                    override fun onSuccess(result: IAuthenticationResult) {
                                        handlers.onToken(result)
                                    }
                                    override fun onError(e: MsalException) {
                                        handlers.onError(e)
                                    }
                                    override fun onCancel() {
                                        handlers.onCancel()
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

private class SignInHandlers(
    val onToken: (IAuthenticationResult) -> Unit,
    val onError: (Throwable) -> Unit,
    val onCancel: () -> Unit = {}
)

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onSignIn: (SignInHandlers) -> Unit
) {
    var token by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val handlers = remember {
        SignInHandlers(
            onToken = { r ->
                token = r.accessToken
                error = null
                Log.d("MSAL", "Token OK, exp=${r.expiresOn}")
            },
            onError = { e ->
                token = null
                error = e.message ?: e.toString()
                Log.e("MSAL", "Auth error", e)
            },
            onCancel = { Log.d("MSAL", "User canceled") }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("MSAL Login (Compose)", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { onSignIn(handlers) }) {
            Text("Sign in with Microsoft")
        }
        token?.let { Text("Access Token (first 32): ${it.take(32)}...") }
        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }
    }
}


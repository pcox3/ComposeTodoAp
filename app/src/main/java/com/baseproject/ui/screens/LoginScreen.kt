package com.baseproject.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.baseproject.navigation.Route
import com.baseproject.network.NetworkStatusCode
import com.baseproject.theme.extraLargeSpacing
import com.baseproject.theme.mediumSpacing
import com.baseproject.theme.smallSpacing
import com.baseproject.ui.custom.OutlineInputField
import com.baseproject.viewModel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(
    navController: NavController? = null
){

    val viewModel by remember { mutableStateOf(AuthViewModel()) }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.isSuccessful.value){
        when(viewModel.isSuccessful.value){
            is NetworkStatusCode.Error -> {
                isLoading = false
                Toast.makeText(navController?.context, "Error, Please try again!",
                    Toast.LENGTH_SHORT).show()
            }
            is NetworkStatusCode.Exception -> {}
            is NetworkStatusCode.Success -> {
                isLoading = false
                navController?.navigate(Route.HOME)
            }
            is NetworkStatusCode.Timeout -> {
                Toast.makeText(navController?.context, "Error, Timeout",
                    Toast.LENGTH_SHORT).show()
                isLoading = false
            }
            else -> {}
        }
    }

    Column(modifier = Modifier
        .padding(extraLargeSpacing)
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        
        Text(text = "Todo", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.padding(extraLargeSpacing))

        Text(
            text = "Email",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        OutlineInputField(
            focusManager = focusManager,
            value = email,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            placeholder = "Enter email",
            isEnabled = !isLoading,
            onValueChange = {
                email = it
            }
        )

        Spacer(modifier = Modifier.padding(mediumSpacing))

        Text(
            text = "Password",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        OutlineInputField(
            focusManager = focusManager,
            value = password,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            placeholder = "Enter password",
            isEnabled = !isLoading,
            onValueChange = { password = it },
            actionDone = {
                login(
                    email,
                    password,
                    viewModel,
                    scope,
                    navController){
                    isLoading = true
                }
            }
        )

        Spacer(modifier = Modifier.padding(extraLargeSpacing))

        if (isLoading) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(smallSpacing),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            Button(
                onClick = {
                    login(
                        email,
                        password,
                        viewModel,
                        scope,
                        navController){
                        isLoading = true
                    }
                }
            ) {
                Text(modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Login")
            }
        }
    }
}


private fun login(
    email: String,
    password: String,
    viewModel: AuthViewModel,
    scope: CoroutineScope,
    navController: NavController?,
    action: () -> Unit = {}) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        scope.launch {
            action()
            withContext(Dispatchers.IO){
                viewModel.login(email, password)
            }
        }
    }else{
        Toast.makeText(navController?.context, "Fields cannot be empty",
            Toast.LENGTH_SHORT).show()
    }
}



@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}

package com.example.studyapp.authentication



import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.Data.UserRepository
import com.example.studyapp.Data.database.User
import com.example.studyapp.Data.database.UserDAO
import com.example.studyapp.R
import com.example.studyapp.viewModel.SignUpViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun SignupScreen(modifier: Modifier = Modifier, viewModel: SignUpViewModel, navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                SignupHeading()
                SignupUsernameField(username = username, onUsernameChange = { username = it })
                SignupPasswordField(password = password, onPasswordChange = { password = it })
                SignupButton(onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        viewModel.signUp(username, password) { success ->
                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Sign-Up Successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(context, "Sign-Up Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter both username and password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                LoginRedirect(navController = navController)
            }
        }
    }
}

@Composable
fun LoginRedirect(navController: NavHostController) {
    Text(
        text = "Already have an account? Log In",
        color = Color(0xFF6200EE),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                navController.navigate("login_screen") // Navigate to login screen
            },
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    )
}


@Composable
fun SignupHeading() {
    Text(
        text = "SIGN UP",
        fontSize = 45.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SignupUsernameField(username: String, onUsernameChange: (String) -> Unit) {
    TextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Username", fontSize = 18.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignupPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password", fontSize = 18.sp) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignupButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF428042))
    ) {
        Text("SIGN UP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSignupScreen() {
    // Manually create a mock UserDAO
    val mockUserDAO = object : UserDAO {
        override suspend fun insert(user: User) {
            // Simulate insert logic
        }

        override suspend fun update(user: User) {
            // Simulate update logic
        }

        override suspend fun delete(user: User) {
            // Simulate delete logic
        }

        override fun getUser(username: String, password: String): Flow<User> {
            return flowOf(User(username = username, password = password))
        }

    }

    // Use the mock UserDAO in the UserRepository
    val mockUserRepository = UserRepository(mockUserDAO)

    // Create a mock SignUpViewModel
    val mockViewModel = SignUpViewModel(mockUserRepository)

    val navController = rememberNavController()

    // Set up the navigation with the NavHost
    NavHost(navController = navController, startDestination = "signup_screen") {
        composable("signup_screen") {
            // Pass navController to the SignupScreen
            SignupScreen(viewModel = mockViewModel, navController = navController)
        }
        // Add other composable destinations here if needed (e.g., login_screen)
    }
}
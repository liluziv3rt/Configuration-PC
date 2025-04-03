import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.mangaproj.presentation.viewmodels.SupabaseAuthViewModel
import com.example.myapplication.Registration.RegistrationViewModel
import com.example.myapplication.Registration.isEmailValid
import com.example.myapplication.data.State.ResultState

@Composable
fun SecondActivity(navController: NavHostController,  RegistrationViewModel: RegistrationViewModel = viewModel())
{
    val uiState = RegistrationViewModel.uiState // Получение текущего состояния UI из ViewMode

    val mContext = LocalContext.current // Получение контекста Android

    val Name = remember {
        mutableStateOf("")
    }
    val Surname = remember {
        mutableStateOf("")
    }
    val Email = remember {
        mutableStateOf("")
    }
    val Password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
    }


    val resultState by RegistrationViewModel.screenState.collectAsState() //Собирает значения из StateFlow в ViewModel, используя collectAsState() для синхронного доступа в Composable

    Column(modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Введите имя")
        TextField(
            value = uiState.name,
            onValueChange = { newName ->
                RegistrationViewModel.updateState(
                    uiState.copy(name = newName)
                )
            },
            label = { Text("Введите имя") })
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Введите фамилию")
        TextField(
            value = uiState.surname,
            onValueChange = { newSurname ->
                RegistrationViewModel.updateState(
                    uiState.copy(surname = newSurname)
                )
            },
            label = { Text("Введите фамилию") })
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Введите почту")
            androidx.compose.material3.TextField(
            value = uiState.email,  // Используем email из состояния ViewModel
            onValueChange = { newEmail ->
                RegistrationViewModel.updateState(
                    uiState.copy(
                        email = newEmail,
                        isEmailError = !newEmail.trim().isEmailValid()
                    )
                )
            },

            label = { Text("Введите почту") })
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Пароль")
        TextField(
            value = uiState.password,
            onValueChange = { newPassword ->
                RegistrationViewModel.updateState(
                    uiState.copy(password = newPassword)
                )
            },
            label = { Text("Введите пароль") })

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = uiState.confirmPassword,
            onValueChange = { newconfirmPassword->
                RegistrationViewModel.updateState(
                    uiState.copy(confirmPassword = newconfirmPassword)
                )
            },
            label = { Text("Подтвердите пароль") })

        Spacer(modifier = Modifier.height(10.dp))

        when (resultState) {
            is ResultState.Error -> {
                Button(onClick = {
                    RegistrationViewModel.signUp()
                }) {
                    Text(text = "Зарегестрироваться")
                }
                Text((resultState as ResultState.Error).message)
            }
            is ResultState.Initialized -> {
                Button(onClick = {
                    RegistrationViewModel.signUp()
                }) {
                    Text(text = "Зарегестрироваться")
                }
            }
            ResultState.Loading -> {
                CircularProgressIndicator()
            }
            is ResultState.Success -> {
                navController.navigate("login")
                {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Text(
                text = "Есть аккаунт? ",
                color = Color.Black
            )

            Text(
                text = "Войдите! ",
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }


    }
}
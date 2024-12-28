package cc.sovellus.vrcaa.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cc.sovellus.vrcaa.R
import cc.sovellus.vrcaa.api.vrchat.http.interfaces.IAuth
import cc.sovellus.vrcaa.ui.components.input.PasswordInput
import cc.sovellus.vrcaa.ui.components.input.TextInput
import cc.sovellus.vrcaa.ui.screen.navigation.NavigationScreen

class LoginScreen : Screen {

    override val key = uniqueScreenKey

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val screenModel = LoginScreenModel()

        var passwordVisibility by remember { mutableStateOf(false) }

        Scaffold { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 520.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.login_text))

                    TextInput(
                        title = stringResource(R.string.login_label_username),
                        input = screenModel.username
                    )

                    PasswordInput(
                        title = stringResource(R.string.login_label_password),
                        input = screenModel.password,
                        visible = passwordVisibility,
                        onVisibilityChange = {
                            passwordVisibility = !passwordVisibility
                        }
                    )

                    Button(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp),
                        onClick = {
                            screenModel.doLogin { result, type ->
                                if (result) {
                                    if (type == IAuth.AuthType.AUTH_NONE)
                                        navigator.replace(NavigationScreen())
                                    else
                                        navigator.replace(MfaScreen(type))
                                }
                            }
                        }
                    ) {
                        Text(text = stringResource(R.string.login_button_text))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding(), start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.legal_disclaimer),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 12.sp
                )
            }
        }
    }
}

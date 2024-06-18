package com.thomas200593.mini_retail_app.features.user_profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.App.app
import timber.log.Timber

private const val TAG = "UserProfileScreen"

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel()
){
    Timber.d("Called: %s", TAG)

    ScreenContent()
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToolBox()
        ProfileCard()
        ProfileMenu()
    }
}

@Composable
private fun ToolBox(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(1.0f)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                modifier = Modifier.size(30.dp),
                color = MaterialTheme.colorScheme.errorContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
private fun ProfileCard() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(
            bottomEnd = 30.dp,
            bottomStart = 30.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1.0f)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    color = MaterialTheme.colorScheme.errorContainer,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = app),
                        contentDescription = null,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Thomas Richard Stif Wijaya is a long name",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Visible
                )
                Text(
                    text = "thomas200593@gmail.com",
                    modifier = Modifier,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}

@Composable
private fun ProfileMenu() {}
package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


object Searching{
    @Composable
    fun SearchToolBar(
        searchQuery: String,
        modifier: Modifier = Modifier,
        placeholder: @Composable () -> Unit = { Text(text = "Type to search...") },
        onSearchQueryChanged: (String) -> Unit,
        onSearchTriggered: (String) -> Unit
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth(),
        ) {
            SearchTextField(
                searchQuery = searchQuery,
                placeholder = placeholder,
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchTriggered = onSearchTriggered,
            )
        }
    }

    @Composable
    private fun SearchTextField(
        searchQuery: String,
        onSearchQueryChanged: (String) -> Unit,
        onSearchTriggered:(String) -> Unit,
        placeholder: @Composable () -> Unit
    ){
        val focusRequester = remember{ FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current
        val onSearchExplicitlyTriggered = {
            keyboardController?.hide()
            onSearchTriggered(searchQuery)
        }
        TextField(
            value = searchQuery,
            onValueChange = { if("\n" !in it) onSearchQueryChanged(it) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if(searchQuery.isNotEmpty()){
                    IconButton(
                        onClick = { onSearchQueryChanged(String()) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester = focusRequester)
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        onSearchExplicitlyTriggered()
                        true
                    } else {
                        false
                    }
                },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchExplicitlyTriggered()
                },
            ),
            maxLines = 1,
            singleLine = true,
            placeholder = placeholder
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Data.master_data
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.Searching.SearchToolBar
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SupplierDataOrdering

@Composable
fun SupplierListScreen(
    viewModel: SupplierListViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    val orderBy by viewModel.orderBy.collectAsStateWithLifecycle()
    val searchQuery by viewModel.query.collectAsStateWithLifecycle()
    val supplierList = viewModel.supplierPagingDataFlow.collectAsLazyPagingItems()

    TopAppBar(
        onNavigateBack = appState::onNavigateUp,
        //TODO: REMOVE LATER
        testGen = viewModel::testGen
    )
    ScreenContent(
        supplierList = supplierList,
        onSearchQueryChanged = viewModel::performSearch,
        onDataOrderingChanged = viewModel::updateOrderBy,
        orderBy = orderBy,
        searchQuery = searchQuery
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit,
    testGen: () -> Unit
) {
    AppBar.ProvideTopAppBarNavigationIcon {
        Surface(
            onClick = onNavigateBack,
            modifier = Modifier
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
    }
    AppBar.ProvideTopAppBarTitle {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = ImageVector.vectorResource(id = master_data),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.str_biz_master_data),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    AppBar.ProvideTopAppBarAction {
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize)
                    .clickable(
                        onClick = { testGen() }
                    ),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    supplierList: LazyPagingItems<Supplier>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onDataOrderingChanged: (SupplierDataOrdering) -> Unit,
    orderBy: SupplierDataOrdering
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Search TextField with debounce
        var visible by remember { mutableStateOf(false) }
        var expanded by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(1.0f)
        ){
            Row(modifier = Modifier.weight(0.9f)) {
                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(text = orderBy.label)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        SupplierDataOrdering.entries.forEach { order ->
                            DropdownMenuItem(
                                onClick = {
                                    onDataOrderingChanged(order)
                                    expanded = false
                                },
                                text = { Text(order.label) }
                            )
                        }
                    }
                }
            }
            Row(modifier = Modifier.weight(0.1f)) {
                Icon(
                    modifier = Modifier.clickable(
                        onClick = {
                            visible = !visible
                        }
                    ),
                    imageVector = if(visible){
                        Icons.Outlined.KeyboardArrowUp
                    }
                    else{
                        Icons.Outlined.KeyboardArrowDown
                    },
                    contentDescription = null
                )
            }
        }
        if(visible){
            SearchToolBar(
                modifier = Modifier.fillMaxWidth(),
                searchQuery = searchQuery,
                placeholder = { Text(text = "Search Supplier...") },
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchTriggered = onSearchQueryChanged
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {
            items(
                count = supplierList.itemCount,
                key = supplierList.itemKey{ it.genId }
            ){ index ->
                supplierList[index]?.let { supplier ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Supplier ID: ${supplier.genId}")
                            Text(text = "Supplier Name: ${supplier.sprLegalName}")
                        }
                    }
                }
            }
        }
    }
}
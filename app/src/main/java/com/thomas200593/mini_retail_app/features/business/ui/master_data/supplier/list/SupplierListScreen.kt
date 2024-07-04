package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier

@Composable
fun SupplierListScreen(
    viewModel: SupplierListViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    val sessionState by appState.isSessionValid.collectAsStateWithLifecycle()
    val supplierList = viewModel.supplierPagingDataFlow.collectAsLazyPagingItems()

    TopAppBar(
        onNavigateBack = appState::onNavigateUp
    )
    ScreenContent(
        supplierList = supplierList,
        testGen = viewModel::testGen
    )
}

@Composable
private fun TopAppBar(
    onNavigateBack: () -> Unit
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
                modifier = Modifier
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
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
                    .sizeIn(maxHeight = ButtonDefaults.IconSize),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ScreenContent(
    supplierList: LazyPagingItems<Supplier>,
    testGen: () -> Unit
) {
    // -> onLoad(): Display the paged list
    // if the search query changed apply filter
    // apply additional filter?
    // reset filter
    // onItemClick, go to detail
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Row {
            Button(onClick = { testGen() }) {
                Text(text = "Test Gen")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                count = supplierList.itemCount,
                key = supplierList.itemKey{ it.seqId }
            ){ index ->
                supplierList[index]?.let { supplier ->
                    Text(text = "Supplier Id: ${supplier.genId}")
                    HorizontalDivider(thickness = 3.dp, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
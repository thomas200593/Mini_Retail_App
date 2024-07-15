package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.ui.AppState
import com.thomas200593.mini_retail_app.app.ui.LocalAppState
import com.thomas200593.mini_retail_app.core.ui.common.Icons.App.app
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Data.master_data
import com.thomas200593.mini_retail_app.core.ui.component.AppBar
import com.thomas200593.mini_retail_app.core.ui.component.Searching.SearchToolBar
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SortSupplier

@Composable
fun SupplierListScreen(
    viewModel: SupplierListViewModel = hiltViewModel(),
    appState: AppState = LocalAppState.current
){
    val searchQuery = viewModel.searchQuery
    val sortBy = viewModel.sortBy
    val pagedListData = viewModel.pagedDataFlow.collectAsLazyPagingItems()

    //TODO search by ID not work
    TopAppBar(
        onNavigateBack = appState::onNavigateUp,
        //TODO: REMOVE LATER
        testGen = viewModel::testGen
    )
    ScreenContent(
        pagedListData = pagedListData,
        onSearchQueryChanged = viewModel::performSearch,
        onSortByChanged = viewModel::updateSortBy,
        sortBy = sortBy,
        searchQuery = searchQuery,
        //TODO
        onClick = { _: Supplier -> },
        onThumbnailClick = { _: Supplier -> }
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
                        onClick = {
                            testGen()
                        }
                    ),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    pagedListData: LazyPagingItems<Supplier>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSortByChanged: (SortSupplier) -> Unit,
    sortBy: SortSupplier,
    onClick: (Supplier) -> Unit,
    onThumbnailClick: (Supplier) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Search TextField with debounce
        var visible by remember { mutableStateOf(false) }
        var expanded by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth(1.0f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(modifier = Modifier.weight(0.5f), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        value = sortBy.title,
                        onValueChange = {},
                        leadingIcon = { Icon(imageVector = Icons.AutoMirrored.Default.List, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        SortSupplier.entries.forEach { dropdownItem ->
                            DropdownMenuItem(
                                onClick = { onSortByChanged(dropdownItem); expanded = false },
                                text =  { Text(text = dropdownItem.title) },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.weight(0.5f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface {
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
        }
        HorizontalDivider(thickness = 2.dp)
        if(visible){
            SearchToolBar(
                modifier = Modifier.fillMaxWidth(),
                searchQuery = searchQuery,
                placeholder = { Text(text = "Search Supplier...") },
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchTriggered = onSearchQueryChanged
            )
            HorizontalDivider(thickness = 2.dp)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {
            items(
                count = pagedListData.itemCount,
                key = pagedListData.itemKey{ it.genId }
            ){ index ->
                pagedListData[index]?.let { supplier ->
                    SupplierListItem(
                        onClick = onClick,
                        onThumbnailClick = onThumbnailClick,
                        supplier = supplier
                    )
                }
            }
        }
    }
}

@Composable
private fun SupplierListItem(
    modifier: Modifier = Modifier,
    supplier: Supplier,
    onThumbnailClick: (Supplier) -> Unit,
    onClick: (Supplier) -> Unit
){
    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
    Surface(
        onClick = { onClick(supplier) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = 2.dp, color = Color.DarkGray)
    ) {
        Row(
            modifier = commonModifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = { onThumbnailClick(supplier) },
                modifier = Modifier
                    .weight(0.2f)
                    .padding(4.dp),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, Color.DarkGray)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = app),
                    contentDescription = null,
                    modifier = commonModifier
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
            ) {
                Text(
                    text = supplier.genId,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                supplier.sprLegalName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
                Text(
                    text = "Updated: ${supplier.auditTrail.modifiedAt}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}
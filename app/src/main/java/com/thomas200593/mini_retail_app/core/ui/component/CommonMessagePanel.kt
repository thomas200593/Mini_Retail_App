package com.thomas200593.mini_retail_app.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons
import com.thomas200593.mini_retail_app.core.ui.common.Shapes
import kotlin.Int.Companion.MAX_VALUE

object CommonMessagePanel{

    @Composable
    fun LoadingPanelCircularIndicator(
        modifier: Modifier = Modifier
    ){
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CircularProgressIndicator()
                Text(
                    text = stringResource(id = R.string.str_loading),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )
            }
        }
    }

    @Composable
    fun LoadingScreen(
        modifier: Modifier = Modifier
    ){
        Surface(modifier = modifier.fillMaxSize()) {
            ConstraintLayout {
                val topGuideline = createGuidelineFromTop(16.dp)
                val bottomGuideline = createGuidelineFromBottom(16.dp)
                val startGuideline = createGuidelineFromStart(16.dp)
                val endGuideline = createGuidelineFromEnd(16.dp)
                val centralGuideline = createGuidelineFromTop(0.5f)
                val (logoBox, appNameBox ) = createRefs()
                Surface(
                    modifier = Modifier.constrainAs(logoBox) {
                        top.linkTo(topGuideline)
                        bottom.linkTo(centralGuideline)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            modifier = Modifier,
                            imageVector = ImageVector.vectorResource(id = CustomIcons.App.app),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "${BuildConfig.VERSION_NAME} - ${BuildConfig.BUILD_TYPE}",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                        )
                    }
                }

                Surface(
                    modifier = Modifier.constrainAs(appNameBox){
                        top.linkTo(centralGuideline)
                        bottom.linkTo(bottomGuideline)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Shapes.DotsLoadingAnimation()
                        Text(
                            text = stringResource(id = R.string.str_loading),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ErrorScreen(
        modifier: Modifier = Modifier,
        showIcon: Boolean = false,
        @DrawableRes iconRes: Int? = null,
        title: String? = null,
        errorMessage: String? = null
    ){
        Surface(
            modifier = modifier.fillMaxSize().padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ) {
            Column(
                modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(showIcon){
                    Surface(
                        modifier = modifier.fillMaxWidth().height(60.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ) {
                        Icon(
                            imageVector = if(iconRes == null) { Default.Warning }
                            else { ImageVector.vectorResource(id = iconRes) },
                            contentDescription = null,
                        )
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                if(title != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                if(errorMessage != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                    )
                }
            }
        }
    }

    @Composable
    fun ErrorPanel(
        modifier: Modifier = Modifier,
        showIcon: Boolean= false,
        @DrawableRes iconRes: Int? = null,
        title: String?= null,
        errorMessage: String? = null
    ){
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, Color.DarkGray),
            color = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ) {
            Column(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(showIcon){
                    Surface(
                        modifier = modifier.fillMaxWidth().height(60.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ) {
                        Icon(
                            imageVector = if(iconRes == null) { Default.Warning }
                            else { ImageVector.vectorResource(id = iconRes) },
                            contentDescription = null
                        )
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                if(title != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                if(errorMessage != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                    )
                }
            }
        }
    }

    @Composable
    fun EmptyScreen(
        modifier: Modifier = Modifier,
        showIcon: Boolean = false,
        @DrawableRes iconRes: Int? = null,
        title: String? = null,
        emptyMessage: String? = null
    ) {
        Surface(
            modifier = modifier.fillMaxSize().padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ){
            Column(
                modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(showIcon){
                    Surface(
                        modifier = modifier.fillMaxWidth().height(60.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ) {
                        Icon(
                            imageVector = if(iconRes == null) { ImageVector.vectorResource(id = CustomIcons.Content.empty) }
                            else { ImageVector.vectorResource(id = iconRes) },
                            contentDescription = null,
                        )
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                if(title != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                if(emptyMessage != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = emptyMessage,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                    )
                }
            }
        }
    }

    @Composable
    fun EmptyPanel(
        modifier: Modifier = Modifier,
        showIcon: Boolean = false,
        @DrawableRes iconRes: Int? = null,
        title: String? = null,
        emptyMessage: String? = null
    ){
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, Color.DarkGray),
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Column(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(showIcon){
                    Surface(
                        modifier = modifier.fillMaxWidth().padding(16.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ) {
                        Icon(
                            imageVector = if(iconRes == null){ ImageVector.vectorResource(id = CustomIcons.Content.empty) }
                            else { ImageVector.vectorResource(id = iconRes) },
                            contentDescription = null
                        )
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                if(title != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                if(emptyMessage != null){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = emptyMessage,
                        textAlign = TextAlign.Start,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                    )
                }
            }
        }
    }

    @Composable
    fun TextContentWithIcon(
        icon: ImageVector,
        text: String,
        iconShape: Shape = MaterialTheme.shapes.extraSmall,
        iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        iconSize: Dp = ButtonDefaults.IconSize,
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        textMaxLines: Int = 1,
        textOverflow: TextOverflow = TextOverflow.Ellipsis,
        textAlign: TextAlign = TextAlign.Start,
        iconWidthRatio: Float = 0.1f,
        textWidthRatio: Float = 0.9f
    ){
        Row(
            modifier = Modifier.fillMaxWidth(1.0f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.weight(iconWidthRatio).size(iconSize),
                shape = iconShape,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )
            }
            Text(
                modifier = Modifier.weight(textWidthRatio),
                text = text,
                color = textColor,
                maxLines = textMaxLines,
                overflow = textOverflow,
                textAlign = textAlign
            )
        }
    }

    @Composable
    fun ClickableCardItem(
        onClick: () -> Unit,
        icon: ImageVector,
        title: String,
        subtitle: String,
        cardShape: Shape = MaterialTheme.shapes.medium,
        cardBorder: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFF747775)),
        iconWidthRatio: Float = 0.2f,
        textWidthRatio: Float = 0.8f,
        titleTextMaxLine: Int? = 1,
        subtitleTextMaxLine: Int? = 1,
        titleFontWeight: FontWeight = FontWeight.Bold,
        titleTextAlign: TextAlign = TextAlign.Start,
        subtitleTextAlign: TextAlign = TextAlign.Start,
        titleTextOverflow: TextOverflow = TextOverflow.Ellipsis,
        subtitleTextOverflow: TextOverflow = TextOverflow.Ellipsis
    ){
        Surface(
            modifier = Modifier.fillMaxWidth(1.0f),
            onClick = onClick,
            shape = cardShape,
            border = cardBorder
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1.0f).padding(8.dp).height(intrinsicSize = IntrinsicSize.Max),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.weight(iconWidthRatio)) {
                    Icon(imageVector = icon, contentDescription = null)
                }
                Column(
                    modifier = Modifier.weight(textWidthRatio),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = titleTextAlign,
                        fontWeight = titleFontWeight,
                        maxLines = titleTextMaxLine?: MAX_VALUE,
                        overflow = titleTextOverflow
                    )
                    Text(
                        text = subtitle,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = subtitleTextAlign,
                        maxLines = subtitleTextMaxLine?: MAX_VALUE,
                        overflow = subtitleTextOverflow
                    )
                }
            }
        }
    }

    @Composable
    fun ThreeRowCardItem(
        cardShape: Shape = MaterialTheme.shapes.medium,
        cardBorder: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFF747775)),
        firstRowContent: @Composable ColumnScope.() -> Unit,
        secondRowContent: @Composable ColumnScope.() -> Unit,
        thirdRowContent: @Composable ColumnScope.() -> Unit
    ){
        Surface(
            modifier = Modifier.fillMaxWidth(1.0f),
            shape = cardShape,
            border = cardBorder
        ){
            Row(
                modifier = Modifier.fillMaxWidth(1.0f).padding(8.dp).height(intrinsicSize = IntrinsicSize.Max),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                //First Row
                Column(
                    modifier = Modifier.weight(0.2f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { firstRowContent() }
                //Second Row
                Column(
                    modifier = Modifier.weight(0.6f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { secondRowContent() }
                //Third Row
                Column(
                    modifier = Modifier.weight(0.2f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { thirdRowContent() }
            }
        }
    }
}
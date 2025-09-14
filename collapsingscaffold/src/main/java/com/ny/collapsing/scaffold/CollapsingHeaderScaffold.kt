package com.ny.collapsing.scaffold

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CollapsingHeaderScaffold(
    modifier: Modifier = Modifier,
    title: String = "",
    header: @Composable (modifier: Modifier, alpha: Float) -> Unit = { _, _ -> },
    toolbarMinHeight: Dp = 64.dp,
    toolbarMaxHeight: Dp = 200.dp,
    collapsedColor: Color = Color.Black,
    expandedColor: Color = Color.Magenta,
    leftActions: @Composable ((color: Color) -> Unit) = {},
    rightActions: @Composable ((color: Color) -> Unit) = {},
    listContent: LazyListScope.() -> Unit,
    ) {

    val rememberState = rememberCollapsingToolbarState()
    val overlayAlpha = rememberState.progress()
    val nestedScrollConnection = collapsingToolbarConnection(
        listState = rememberState.listState,
        toolbarOffsetPx = rememberState.toolbarOffsetPx,
        toolbarHeightPx = rememberState.toolbarHeightPx,
        minHeightPx = rememberState.minHeightPx
    )
    val currentToolbarHeightDp = rememberState.currentToolbarHeightDp()
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val menuCategoryHeight = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = rememberState.toolbarMinHeight + statusBarHeight)
                .offset {
                    IntOffset(
                        0,
                        currentToolbarHeightDp.value
                            .toPx()
                            .roundToInt() + statusBarHeight
                            .toPx()
                            .roundToInt()
                    )
                },
            state = rememberState.listState,
            content = listContent,
        )
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                )
            },
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .zIndex(2f),
            navigationIcon = {
                leftActions(lerp(expandedColor, collapsedColor, overlayAlpha.value))
            },
            actions = { rightActions(lerp(expandedColor, collapsedColor, overlayAlpha.value)) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = collapsedColor.copy(alpha = overlayAlpha.value),
            )
        )
        header(
            Modifier
                .fillMaxWidth()
                .height(rememberState.toolbarMaxHeight)
                .offset { IntOffset(0, rememberState.toolbarOffsetPx.floatValue.roundToInt()) },
            1 - overlayAlpha.value
        )
    }
}

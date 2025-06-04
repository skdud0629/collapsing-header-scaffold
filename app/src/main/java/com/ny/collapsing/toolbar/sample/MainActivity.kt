package com.ny.collapsing.toolbar.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ny.collapsing.scaffold.CollapsingHeaderScaffold
import com.ny.collapsing.toolbar.R
import com.ny.collapsing.toolbar.ui.theme.CollapsingToolbarTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollapsingToolbarTheme {
                CollapsingHeaderScaffold(
                    modifier = Modifier.fillMaxSize(),
                    toolbarMinHeight = 50.dp,
                    toolbarMaxHeight = 400.dp,
                    collapsedColor = Color.Black,
                    expandedColor = Color.Magenta,
                    listContent = {
                        item {
                            Text(
                                text = "Item",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        stickyHeader {
                            Text(
                                text = "Sticky Header",
                                modifier = Modifier.fillMaxWidth().background(Color.LightGray)
                            )
                        }
                        items(100) { index ->
                            Text(
                                text = "Item #$index",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    header = { modifier, overlayAlpha ->
                       Image(
                            modifier = modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            alpha = overlayAlpha
                        )
                    },
                    title = "title",
                    rightActions = { color ->
                        Text(
                            text = "Action",
                            color = color,
                        )
                    },
                    leftActions = { color ->
                        Icon(
                            modifier = Modifier
                                .size(36.dp),
                            imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowLeft,
                            contentDescription = "top bar left icon",
                            tint = color,
                        )
                    },
                )
            }
        }
    }
}

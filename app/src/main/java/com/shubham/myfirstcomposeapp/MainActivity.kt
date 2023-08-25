package com.shubham.myfirstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubham.myfirstcomposeapp.ui.theme.MyFirstComposeAppTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageCard()
        }
    }
}


@Composable
fun ImageCard(modifier: Modifier = Modifier) {

    val color = remember { mutableStateOf(Color.Black) }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Blue)
            .padding(20.dp)
            .clickable {
                color.value = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(), 1f
                )
            },
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = "Random text",
            color = color.value,
            fontSize = 50.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}
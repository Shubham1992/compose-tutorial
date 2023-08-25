package com.shubham.myfirstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubham.myfirstcomposeapp.ui.theme.MyFirstComposeAppTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //ImageCard()
            //MyScaffold()
            MyList()
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

@Composable
fun MyScaffold() {
    //Default state given by system
    val scaffoldState = rememberScaffoldState()
    var textfieldState by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) { paddingValue ->
        print(paddingValue)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = textfieldState,
                label = {
                    Text(text = "Enter your name")
                },
                onValueChange = {
                    textfieldState = it
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Hello $textfieldState")
                }
            }) {
                Text(text = "Click me")
            }

        }

    }
}

@Composable
fun MyList() {

    //Need to create scrollState so that system remembers where scroll is, and can modify it if required
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        for (i in 1..50) {
            Text(
                text = "Index $i",
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}
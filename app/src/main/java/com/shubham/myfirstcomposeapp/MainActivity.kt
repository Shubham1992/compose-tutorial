package com.shubham.myfirstcomposeapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import androidx.lifecycle.ViewModelProvider
import com.shubham.myfirstcomposeapp.launched_effect.LaunchedEffectViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    lateinit var viewModel: LaunchedEffectViewModel
    lateinit var context: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContent {
            //ImageCard()
            //MyScaffold()
            //MyList()
            //MyLazyList1()
            //MyLazyList2()
            //MyconstraintLayoutView()
            viewModel =
                ViewModelProvider(this@MainActivity).get(LaunchedEffectViewModel::class.java)
            //MyLaunchedEffect(viewModel, context)
            MyLaunchedEffect2(viewModel, context)
        }
    }
}


@Composable
fun ImageCard(modifier: Modifier = Modifier) {

    val color = remember { mutableStateOf(Color.Black) }

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.Blue).padding(20.dp).clickable {
            color.value = Color(
                Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f
            )
        }, contentAlignment = Alignment.Center

    ) {
        Text(
            text = "Random text", color = color.value, fontSize = 50.sp
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
        modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
    ) { paddingValue ->
        print(paddingValue)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = textfieldState, label = {
                Text(text = "Enter your name")
            }, onValueChange = {
                textfieldState = it
            }, singleLine = true
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

@Composable
fun MyLazyList1() {
    LazyColumn {
        items(50) {

            Text(
                text = "Index $it",
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun MyLazyList2() {

    LazyColumn {
        itemsIndexed(listOf("My", "Name", "is", "Shubham")) { index, item ->

            Text(
                text = item,
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

        }

    }
}

@Composable
fun MyconstraintLayoutView() {

    val constraintSet = ConstraintSet() {
        val greenBox = createRefFor("greenBox")
        val redBox = createRefFor("redBox")

        //Can use this guideline also to constraint stuff
        val guideline = createGuidelineFromTop(0.5f)

        constrain(greenBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        constrain(redBox) {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Spread)
    }


    ConstraintLayout(constraintSet = constraintSet, modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.background(Color.Green).layoutId("greenBox"))
        Box(modifier = Modifier.background(Color.Red).layoutId("redBox"))
    }

}

@Composable
fun MyLaunchedEffect(viewModel: LaunchedEffectViewModel, context: MainActivity) {

    var counter by remember {
        mutableStateOf(0)
    }

    //Whenever counter value updates, this launch effect will be executed
    LaunchedEffect(key1 = counter) {
        viewModel.liveData.observe(context) {
            Log.e("Launched effect", counter.toString())
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Button(modifier = Modifier.width(200.dp).align(Alignment.Center),
            onClick = {
                counter++
            }) {
            Text(text = "Increase counter")
        }
    }


}

@Composable
fun MyLaunchedEffect2(viewModel: LaunchedEffectViewModel, context: MainActivity) {

    var counter by remember {
        mutableStateOf(0)
    }

    /**
     * Whenever counter is changed, the LaunchedEffect will not be called again
     * because it is not observing counter in its Key1
     **/
    LaunchedEffect(key1 = true) {
        Log.e("Launched effect", counter.toString())

        viewModel.liveData.observe(context) {
            Log.e("livedata changed", counter.toString())
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Button(modifier = Modifier.width(200.dp).align(Alignment.Center),
            onClick = {
                counter++
                viewModel.updateData()
            }) {
            Text(text = "Increase counter")
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}
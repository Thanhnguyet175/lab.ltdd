package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.lab5.data.Datasource
import com.example.lab5.data.Dog
import com.example.lab5.ui.theme.Lab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab5Theme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    DogList(
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun DogList(modifier: Modifier = Modifier) {

    val dogs = Datasource().loadDogs()

    LazyColumn(modifier = modifier) {

        items(dogs) { dog ->

            DogItem(dog)

        }

    }
}

@Composable
fun DogItem(dog: Dog) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            Image(
                painter = painterResource(dog.imageResourceId),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {

                Text(
                    text = stringResource(dog.name),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${dog.age} years old",
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    Lab5Theme {
        DogList()
    }
}
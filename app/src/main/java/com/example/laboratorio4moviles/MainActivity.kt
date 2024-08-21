package com.example.laboratorio4moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.laboratorio4moviles.ui.theme.Laboratorio4MovilesTheme

data class RecipeItem(val name: String, val imageUrl: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio4MovilesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RecipeScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RecipeScreen(modifier: Modifier) {
    val recipeList = remember { mutableStateListOf<RecipeItem>() }
    val nameState = remember { mutableStateOf("") }
    val imageUrlState = remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )
        TextField(
            value = imageUrlState.value,
            onValueChange = { imageUrlState.value = it },
            label = { Text("URL") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )
        Button(
            onClick = {
                val name = nameState.value.trim()
                val imageUrl = imageUrlState.value.trim()

                // Validation
                if (name.isNotEmpty() && imageUrl.isNotEmpty() && !recipeList.any { it.name == name }) {
                    val newRecipe = RecipeItem(name, imageUrl)
                    recipeList.add(newRecipe)
                    nameState.value = ""
                    imageUrlState.value = ""
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Send")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(recipeList) { recipe ->
                RecipeCard(recipe, recipeList)
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: RecipeItem, recipeList: MutableList<RecipeItem>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                recipeList.remove(recipe)
            },
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF3885B4)) // Celeste color
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Center text
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Text(
                text = recipe.name,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black // Text color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeScreen() {
    Laboratorio4MovilesTheme {
        RecipeScreen(modifier = Modifier.fillMaxSize())
    }
}

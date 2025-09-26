package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

// Modelo de datos para las obras de arte
data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtGalleryApp()
                }
            }
        }
    }
}

@Composable
fun ArtGalleryApp() {
    // Lista de obras de arte
    val artworks = remember {
        listOf(
            Artwork(
                imageRes = R.drawable.blueroses,
                title = "Still Life of Blue Rose and Other Flowers",
                artist = "Owen Scott",
                year = "2021"
            ),
            Artwork(
                imageRes = R.drawable.bridge,
                title = "Sailing Under the Bridge",
                artist = "Kat Kuan",
                year = "2017"
            )
        )
    }

    // Estado para el índice de la obra actual
    var currentArtworkIndex by remember { mutableIntStateOf(0) }
    val artwork=artworks[currentArtworkIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        // Imagen de la obra de arte
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(1.dp)
                ),
            shape = RoundedCornerShape(1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = artwork.imageRes),
                    contentDescription = artwork.title,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Información de la obra
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(Color(0xFFAFAFAF)),
            horizontalAlignment = Alignment.Start,
        ) {
            // Título de la obra
            Text(
                text = artwork.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                lineHeight = 24.sp,
                modifier = Modifier.padding(
                    horizontal = 8.dp
                    )
            )

            // Información del artista y año
            Row(
                //modifier = Modifier.padding(horizontal = 8.dp)
            ){
                Text(
                    text = artwork.artist,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier=Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = "(${artwork.year})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botones de navegación
        NavigationButtons(
            onPreviousClick = {
                currentArtworkIndex = when (currentArtworkIndex) {
                    0 -> artworks.size - 1
                    else -> currentArtworkIndex - 1
                }
            },
            onNextClick = {
                currentArtworkIndex = when (currentArtworkIndex) {
                    artworks.size - 1 -> 0
                    else -> currentArtworkIndex + 1
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}


@Composable
fun NavigationButtons(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Botón Previous
        Button(
            onClick = onPreviousClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A6B8C)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Previous",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        // Botón Next
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A6B8C)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtGalleryPreview() {
    ArtSpaceTheme {
        ArtGalleryApp()
    }
}
package com.example.lab9

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.lab9.workers.Blur
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BlurScreen()
                }
            }
        }
    }

    @Composable
    fun BlurScreen() {

        var selectedLevel by remember {
            mutableStateOf(1)
        }

        var isLoading by remember {
            mutableStateOf(false)
        }

        var blurDone by remember {
            mutableStateOf(false)
        }

        val workManager = WorkManager.getInstance(this)

        var blurredBitmap by remember {
            mutableStateOf<android.graphics.Bitmap?>(null)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Blur-O-Matic",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (blurDone && blurredBitmap != null) {

                Image(
                    bitmap = blurredBitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

            } else {

                Image(
                    painter = painterResource(id = R.drawable.cupcake),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Blur Level",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            for (i in 1..3) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = selectedLevel == i,
                        onClick = {
                            selectedLevel = i
                        }
                    )

                    Text(
                        text = "Level $i"
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (isLoading) {

                CircularProgressIndicator()

                Spacer(modifier = Modifier.height(20.dp))
            }

            Button(
                onClick = {

                    isLoading = true
                    blurDone = false

                    val request =
                        OneTimeWorkRequestBuilder<Blur>()
                            .build()

                    workManager.enqueue(request)

                    workManager
                        .getWorkInfoByIdLiveData(request.id)
                        .observe(this@MainActivity) { workInfo ->

                            if (workInfo != null &&
                                workInfo.state == WorkInfo.State.SUCCEEDED
                            ) {

                                isLoading = false
                                blurDone = true

                                val file = File(
                                    filesDir,
                                    "blurred_image.png"
                                )

                                if (file.exists()) {

                                    blurredBitmap =
                                        BitmapFactory.decodeFile(
                                            file.absolutePath
                                        )
                                }

                                Toast.makeText(
                                    this@MainActivity,
                                    "Blur thanh cong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Text(
                    text = "Start Blur",
                    fontSize = 18.sp
                )
            }
        }
    }
}
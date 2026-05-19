package com.example.lab9.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab9.R
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream

class Blur(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        return try {

            delay(3000)

            val bitmap = BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.cupcake
            )

            // blur fake
            val smallBitmap = bitmap.scale(
                bitmap.width / 10,
                bitmap.height / 10
            )

            val blurBitmap = smallBitmap.scale(
                bitmap.width,
                bitmap.height
            )

            val outputFile = File(
                applicationContext.filesDir,
                "blurred_image.png"
            )

            val out = FileOutputStream(outputFile)

            blurBitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                out
            )

            out.flush()
            out.close()

            Result.success()

        } catch (e: Exception) {

            e.printStackTrace()

            Result.failure()
        }
    }
}
package com.jordansilva.bluebank.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream

object ScreenshotComparator {

    @RequiresApi(Build.VERSION_CODES.O)
    fun assertScreenshot(goldenName: String, node: SemanticsNodeInteraction) {
        val bitmap = node.captureToImage().asAndroidBitmap()
        val filename = goldenName.lowercase().trim()

        saveScreenshot(filename, bitmap)

        val golden = InstrumentationRegistry.getInstrumentation()
            .context.resources.assets.open("screenshots/$filename.png")
            .use { BitmapFactory.decodeStream(it) }

        golden.compare(bitmap)
    }

    private fun saveScreenshot(filename: String, bmp: Bitmap) {
        val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath
        val pngFilename = "$path/screenshots/$filename.png"
        val file = File(pngFilename).parentFile
        if (file != null && !file.exists()) {
            file.mkdirs()
        }

        FileOutputStream(pngFilename).use { out ->
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    }

    private fun Bitmap.compare(other: Bitmap) {
        if (this.width != other.width || this.height != other.height) {
            throw AssertionError("Size of screenshot does not match golden file " +
                    "(check device density - $width != ${other.width} or $height != ${other.height})")
        }

        // Compare row by row to save memory on device
        val row1 = IntArray(width)
        val row2 = IntArray(width)
        for (row in 0 until height) {
            // Read one row per bitmap and compare
            this.getRow(row1, row)
            other.getRow(row2, row)

            if (!row1.contentEquals(row2)) {
                throw AssertionError("Sizes match but bitmap content has differences.")
            }
        }
    }

    private fun Bitmap.getRow(pixels: IntArray, y: Int) {
        this.getPixels(pixels, 0, width, 0, y, width, 1)
    }
}

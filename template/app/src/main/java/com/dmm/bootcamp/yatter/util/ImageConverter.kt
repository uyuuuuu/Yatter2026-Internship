package com.dmm.bootcamp.yatter.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File

object ImageConverter {
  fun uriToFile(context: Context, uri: Uri): File? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
      val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(
        context.contentResolver.getType(uri),
      ) ?: "jpg"
      val file = File.createTempFile("image", ".$extension")
      file.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
      }
      file
    }
  }
}

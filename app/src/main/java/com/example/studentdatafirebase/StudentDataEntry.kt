package com.example.studentdatafirebase

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import coil.compose.rememberImagePainter
import com.google.firebase.storage.FirebaseStorage
import java.io.File


@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DataEntryForm(){
    val context = LocalContext.current
    val viewModel: StudentViewModel = viewModel()
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()) {

        Text(
            text = "Add Student Details",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = {
                Text(
                    text = "Student Name",
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = viewModel.rollNo,
            onValueChange = { viewModel.rollNo = it },
            label = {
                Text(
                    text = "Student Roll Number",
                )
            },
           // isError = viewModel.rollNo.isEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = viewModel.major,
            onValueChange = { viewModel.major = it },
            label = {
                Text(
                    text = "Student Major",
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = viewModel.phoneNumber,
            onValueChange = { viewModel.phoneNumber = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    text = "Student Phone",
                )
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )


        val context = LocalContext.current
        val imageUri by remember { mutableStateOf(viewModel.imageUri) }
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        val imageCapture = remember { ImageCapture.Builder().build() }
        Button(onClick = {
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageCapture
                    )
                    takePicture(context, imageCapture) { uri ->
                        viewModel.imageUri = uri
                    }
                } catch (e: Exception) {
                    Log.e("CameraCaptureButton", "Error binding camera use cases", e)
                }
            }, ContextCompat.getMainExecutor(context))
        }) {
            Text("Capture Image")
        }

        imageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(300.dp)
            )
        }







    Button(
            onClick = {
                viewModel.onSubmitForm(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Data Submitted Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        viewModel.sendNotification(context, "New Student","Name: ${viewModel.name}")
                    },
                    onError = { errorMessage ->
                        Toast.makeText(
                            context,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                      },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Save Record")
        }
    }
}

@Preview
@Composable
fun EnterDataPreview(){
    EnterDataPreview()
}


fun takePicture(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (Uri) -> Unit // Callback for when the image is captured
) {
    val photoFile = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri) // Call the callback with the URI
                uploadImageToFirebase(savedUri)
            }

            @androidx.annotation.OptIn(UnstableApi::class)
            override fun onError(exception: ImageCaptureException) {
                Log.e("takePicture", "Error capturing image", exception)
            }
        }
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
fun uploadImageToFirebase(imageUri: Uri) {
    val storageRef = FirebaseStorage.getInstance().reference
    val imagesRef = storageRef.child("images/${imageUri.lastPathSegment}")
    val uploadTask = imagesRef.putFile(imageUri)

    uploadTask.addOnSuccessListener {
        Log.d("Firebase", "Image uploaded successfully")
    }.addOnFailureListener {
        Log.e("Firebase", "Image upload failed", it)
    }
}
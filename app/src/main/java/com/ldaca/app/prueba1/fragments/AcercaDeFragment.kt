package com.ldaca.app.prueba1.fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.databinding.FragmentAcercaDeBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AcercaDeFragment : Fragment() {

    lateinit var binding : FragmentAcercaDeBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentAcercaDeBinding.inflate(layoutInflater, container, false)

        binding.foto.setOnClickListener {
            tomar_fotografia()
        }

        return binding.root
    }


    private lateinit var currentPhotoPath: String

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyy_MM_dd").format(Date())
        val storageDir: File? = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    val REQUEST_TAKE_PHOTO = 1

    private fun tomar_fotografia() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        activity!!.applicationContext,
                        "com.ldaca.app.prueba1",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                val imageBitmap = data.extras!!.get("data") as Bitmap
                binding.foto.setImageBitmap(imageBitmap)
            }else{
                Toast.makeText(activity, "Intentelo de nuevo más tarde", Toast.LENGTH_SHORT).show()
            }
        }
    }



    /*@RequiresApi(Build.VERSION_CODES.R)
    private fun tomar_fotografia(){
        //Abrira la imagen correspondiente
        val fileImagen = File(Environment.getStorageDirectory(), RUTA_IMAGEN)

        //Indicara si existe o no la imagen
        var IsCreada:Boolean = fileImagen.exists()

        if (!IsCreada){
            IsCreada = fileImagen.mkdirs()
        }

        if(IsCreada){
            nombre_Imagen = "${System.currentTimeMillis()/1000}.jpg"
        }

        //La ruta de nuestra imagen
        path = "${Environment.getStorageDirectory()}${File.separator}$RUTA_IMAGEN${File.separator}$nombre_Imagen"

        var imagen = File(path as String)

        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen))
        startActivityForResult(intent, 20)
    }*/


    companion object {


    }

}


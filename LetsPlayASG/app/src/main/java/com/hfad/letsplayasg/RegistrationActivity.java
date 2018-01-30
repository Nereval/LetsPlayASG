/*
Autor: pchor. Piotr Czyżak
 */
package com.hfad.letsplayasg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;
    static boolean registerStatus = false;
    ImageView mImageView;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mImageView = (ImageView) findViewById(R.id.obraz);
        Button photoButton = (Button) findViewById(R.id.camera);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        Button galleryImageButton = (Button) findViewById(R.id.gallery);
        galleryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_LOAD_IMAGE);
            }
        });
    }
    /*
    Obsługa przycisku "Zatwierdź"
    Uruchamia MainActivity i przekazuje wpisaną nazwę użytkownika i ścieżkę do zrobionego zdjęcia za pomocą aparatu
     */
    public void submitRegistration(View view){
        EditText name = (EditText)findViewById(R.id.name);
        EditText pass = (EditText)findViewById(R.id.password);
        String username = name.getText().toString();
        String password = pass.getText().toString();

        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
        AsyncTask<String, Void, String> task = new RegisterConnectionActivity(this).execute(username, password);


//            Intent submitIntent = new Intent(this, MainActivity.class);
//            submitIntent.putExtra("imagePath", mCurrentPhotoPath);
//            submitIntent.putExtra("username", username);
//            startActivity(submitIntent);
    }

    public static void setStatus(boolean status){
        registerStatus = status;
    }
    /*
    metoda wywołana po zakończeniu aktywności rozpoczętej przez startActivityForResult:
    1) uruchamia metodę setPic(), która zajmuje się zwróconym obrazem po zrobieniu zdjęcia
    2) ustawia wybrany obraz z galerii w ImageView
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mCurrentPhotoPath != null) {
                setPic();
               // mCurrentPhotoPath = null;
            }
        }
        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            //TODO: Wyświetlanie zdjęcia wybranego z galerii.
            //mCurrentPhotoPath = selectedImage.getPath();
            mImageView.setImageURI(selectedImage);
        }
    }

    /*
takePicture() - metoda wywołana po wciśnięciu przycisku "Kamera"
uruchamia domyślną aplikację aparatu w celu zrobienia zdjęcia profilowego
 */
    Uri photoURI;
    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.hfad.letsplayasg.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    /*
    createImageFile - tworzy plik w formacie jpg do zapisu zdjęcia
    o unikalnej nazwię przy wykorzystaniu obecnej daty i czasu
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /*
    setPic zostaje uruchomione po zwróceniu obrazu przez aktywność aparatu,
     tworzy bitmapę i dokonuje skalowania zdjęcia
    do rozmiaru ImageView zdefiniowanego w tej aktywności i
    umieszcza obraz w ImageView
     */
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        try {
            bitmap = rotateImageIfRequired(this, bitmap, photoURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageView.setImageBitmap(bitmap);
        mImageView.setVisibility(View.VISIBLE);
    }

    /*
    Dla wersji androida powyżej API 23, obraz zostaje domyślnie obrócony poziomo, przez co po wyświetleniu w ImageView,
    pomimo zrobienia zdjęcia pionowo, jest ono obrócone poziomo.
    rotateImageIfReguired obraca obraz z powrotem do oryginalnej orientacji.
     */
    public static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateBitmap(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateBitmap(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateBitmap(img, 270);
            default:
                return img;
        }
    }

    /*
    Obraca bitmape.
    source - bitmapa
    angle - kąt o jaki obraca
     */
    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}

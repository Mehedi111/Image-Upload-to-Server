package android.cse.diu.mehedi.serverimageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   private EditText imageTitleED;
   private ImageView imageView;
   private Button uploadButton, chooseButton;
   private Bitmap bitmap;
   private static final int IMAGE_REQUEST_KEY = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageTitleED = findViewById(R.id.imageTitle);
        imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.uploadButton);
        chooseButton = findViewById(R.id.chooseButton);

        uploadButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.uploadButton:
                uploadImageToServer();
                break;

            case R.id.chooseButton:
                selectImage();
                break;
        }
    }

    public void selectImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_KEY);
    }


    private String bitmapToString(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);
        byte[] imgByte = outputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void uploadImageToServer(){
        String image = bitmapToString();
        String title = imageTitleED.getText().toString().trim();
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ImageClass> call = apiInterface.uploadsImage(title, image);
        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {

                if (response.code()==200){
                    ImageClass imageClass = response.body();
                    Toast.makeText(MainActivity.this, ""+imageClass.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    imageView.setVisibility(View.GONE);
                    imageTitleED.setText("");
                    imageTitleED.setVisibility(View.GONE);
                    chooseButton.setEnabled(true);
                    uploadButton.setEnabled(false);
                }

            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {

                Log.e("MainActivity", t.getMessage());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_REQUEST_KEY && resultCode==RESULT_OK && data!=null){

            Uri uri = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                chooseButton.setEnabled(false);
                uploadButton.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

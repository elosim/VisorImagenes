package com.example.ivan.visorimagenes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.example.ivan.visorimagenes.utiles.Tratamiento;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private Bitmap bitmap, bitmaplast;
    public static final int REQUEST_IMAGE_GET = 1;
    Intent picMessageIntent;
    private View v;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Obtenemos el Intent del que venimos, este es el que nos puede ofrecer la imagen
//        seleccionada en el explorador. Obtenemos sus datos.
        picMessageIntent = getIntent();
        uri = picMessageIntent.getData();
        iv = (ImageView)findViewById(R.id.iV);

//        Si la uri es distinta de null, utilizamos la imagen obtenida del explorador,

        if(uri!=null){

            iv.setImageURI(uri);

//        si no, utilizamos la imagen por defecto.

        }else{

            Drawable drawaux = getResources().getDrawable(R.drawable.a);
            iv.setImageDrawable(drawaux);
        }
        bitmap=((BitmapDrawable) iv.getDrawable()).getBitmap();
        bitmaplast = bitmap;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQUEST_IMAGE_GET){

            Uri uri = data.getData();
            if(uri!=null){

                iv.setImageURI ( uri );
                bitmap=((BitmapDrawable) iv.getDrawable()).getBitmap();
                bitmaplast = bitmap;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ByN) {
            bitmaplast=bitmap;
            iv.setImageBitmap(Tratamiento.toEscalaDeGris(((BitmapDrawable) iv.getDrawable()).getBitmap()));
        }
        if (id == R.id.RotarI){
            bitmaplast=bitmap;
            rotarImgI();
        }
        if (id == R.id.RotarD){
            bitmaplast=bitmap;
            rotarImgD();
        }

        return super.onOptionsItemSelected(item);
    }

    public void buscarFoto(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), 1);

    }
    public void deshacer (View v){
        bitmap=bitmaplast;
        iv.setImageBitmap(bitmap);
    }
    public Bitmap getBmp() {

        BitmapDrawable bmpDraw = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        return bitmap;
    }
    public void rotarImgI(){

        bitmap = getBmp();
        iv.setImageBitmap(Tratamiento.rotar(bitmap, -90));
    }
    public void rotarImgD(){

        bitmap = getBmp();
        iv.setImageBitmap(Tratamiento.rotar(bitmap, 90));
    }

}

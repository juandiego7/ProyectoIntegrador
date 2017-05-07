package projects.juandiego.com.draw;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;

    ImageButton imgNew;
    ImageButton imgPaint;
    ImageButton imgDelete;
    ImageButton imgSave;
    ImageButton imgColorBlack;
    ImageButton imgColorRed;
    ImageButton imgColorGreen;
    ImageButton imgColorBlue;
    ImageButton imgColorOrange;
    Painting painting;
    float small;
    float medium;
    float large;
    float d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgNew = (ImageButton)findViewById(R.id.imgNew);
        imgPaint = (ImageButton)findViewById(R.id.imgPaint);
        imgDelete = (ImageButton)findViewById(R.id.imgDelete);
        imgSave = (ImageButton)findViewById(R.id.imgSave);
        imgColorBlack = (ImageButton)findViewById(R.id.imgColorBlack);
        imgColorRed = (ImageButton)findViewById(R.id.imgColorRed);
        imgColorGreen = (ImageButton)findViewById(R.id.imgColorGreen);
        imgColorBlue = (ImageButton)findViewById(R.id.imgColorBlue);
        imgColorOrange = (ImageButton)findViewById(R.id.imgColorOrange);

        painting = (Painting)findViewById(R.id.painting);

        small = 10;
        medium = 20;
        large = 30;
        d = medium;

        imgNew.setOnClickListener(this);
        imgPaint.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        imgSave.setOnClickListener(this);
        imgColorBlack.setOnClickListener(this);
        imgColorRed.setOnClickListener(this);
        imgColorGreen.setOnClickListener(this);
        imgColorBlue.setOnClickListener(this);
        imgColorOrange.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (view.getId()){
            case R.id.imgColorBlack:
                painting.setColor(view.getTag().toString());
                break;
            case R.id.imgColorRed:
                painting.setColor(view.getTag().toString());
                break;
            case R.id.imgColorGreen:
                painting.setColor(view.getTag().toString());
                break;
            case R.id.imgColorBlue:
                painting.setColor(view.getTag().toString());
                break;
            case R.id.imgColorOrange:
                painting.setColor(view.getTag().toString());
                break;
            case R.id.imgNew:
                builder.setTitle(R.string.new_draw)
                       .setMessage(R.string.exit)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                painting.newDraw();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create();
                builder.show();
                break;
            case R.id.imgPaint:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.pick_size)
                        .setItems(R.array.sizes_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                painting.setErase(false);
                                painting.setSizePoint(painting.getSizePoint(which));
                            }
                        });
                builder.create();
                builder.show();
                break;
            case R.id.imgDelete:
                builder.setTitle(R.string.pick_size)
                        .setItems(R.array.sizes_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                painting.setErase(true);
                                painting.setSizePoint(painting.getSizePoint(which));
                            }
                        });
                builder.create();
                builder.show();
                break;
            case R.id.imgSave:
                builder.setTitle(R.string.save_draw)
                        .setMessage(R.string.save)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                painting.saveDraw();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create();
                builder.show();
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }
}

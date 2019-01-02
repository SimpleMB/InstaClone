package com.example.hp.instaclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hp.instaclone.Activity.StartActivity;
import com.example.hp.instaclone.Fragment.ProfileTabFragment;
import com.example.hp.instaclone.Fragment.SharePhotoTabFragment;
import com.example.hp.instaclone.Fragment.UsersTabFragment;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private SecondAdapter tabAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Super Duper");

        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.myToolbar);
        tabLayout = findViewById(R.id.tabLayout);
        tabAdapter = new SecondAdapter(getSupportFragmentManager());

        tabAdapter.addFragment(new ProfileTabFragment(), "Profile");
        tabAdapter.addFragment(new UsersTabFragment(), "Users");
        tabAdapter.addFragment(new SharePhotoTabFragment(), "Photos");

        setSupportActionBar(toolbar);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_group);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.postMenuImage:
                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(MainActivity.this ,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 3000 );
                } else {

                    getImage();

                }
                break;


            case R.id.logoutMenu:

                ParseUser.logOut();
                startActivity(new Intent(this, StartActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void getImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3000) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getImage();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4000 && resultCode == Activity.RESULT_OK && data != null) {

                try {
                    Uri selectedImage = data.getData();
                    Bitmap receivedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("pic.png", bytes );
                    ParseObject parseObject = new ParseObject("Photos");
                    parseObject.put("picture", parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setMessage("Uploading");
                    dialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Toast.makeText(MainActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();

                        }
                    });



                } catch (Exception e) {

                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        }
    }


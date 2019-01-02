package com.example.hp.instaclone.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.instaclone.Activity.StartActivity;
import com.example.hp.instaclone.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class SharePhotoTabFragment extends Fragment implements View.OnClickListener {


    ImageView imgPhotoView;
    EditText edtPhotoName;
    Button btnPhotoUpload;
    Bitmap receivedImageBitmap;


    public SharePhotoTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_photo_tab, container, false);

        // Initializing layout fields

        imgPhotoView    = view.findViewById(R.id.imgPhotoView);
        edtPhotoName    = view.findViewById(R.id.edtPhotoName);
        btnPhotoUpload  = view.findViewById(R.id.btnPhotoUpload);

        imgPhotoView.setOnClickListener(SharePhotoTabFragment.this);
        btnPhotoUpload.setOnClickListener(SharePhotoTabFragment.this);

        //Checking if we have logged user.

        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser == null) {
            startActivity(new Intent(getContext(), StartActivity.class));
        }






        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgPhotoView:
                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000 );
                } else {
                    getImage();
                }


            case R.id.btnPhotoUpload:

                // Check if we have image selected in gallery
                if (receivedImageBitmap != null) {

                    // Check if we have description of the image in EditText field. If NO then:

                    if (edtPhotoName.getText().toString().equals("")) {

                        Toast.makeText(getContext(), "Please provide image description", Toast.LENGTH_LONG).show();

                    } else {
                        //If yes then:
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png", bytes );
                        ParseObject parseObject = new ParseObject("Photos");
                        parseObject.put("picture", parseFile);
                        parseObject.put("description", edtPhotoName.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Uploading");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                    Toast.makeText(getContext(), "Upload Complete", Toast.LENGTH_LONG).show();
                                } else {

                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();

                            }
                        });



                    }

                } else {

                    Toast.makeText(getContext(), "Image not selected", Toast.LENGTH_LONG);
                }


        }

    }

    private void getImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgPhotoView.setImageBitmap(receivedImageBitmap);


                } catch (Exception e) {

                 e.printStackTrace();

                }
            }
        }
    }
}

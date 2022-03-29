package com.example.maintanenceapp;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.google.android.gms.tasks.Continuation;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.StorageTask;
        import com.google.firebase.storage.UploadTask;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    LinearLayout mprofilebtn, mappointmentbtn, mfeedbackbtn, mappthistorybtn, mserviceinfobtn;
    CircleImageView mprofileImage;
    TextView mFullName;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ImagesRecyclerAdapter imagesRecyclerAdapter;
    List<ImagesList> imagesList;
    private static final int IMAGE_REQUEST = 1;
    private StorageTask storageTask;
    private Uri imageUri;
    private StorageReference storageReference;
    private UsersData usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("ICARSERVE");
        imagesList = new ArrayList<>();

        mFullName = findViewById(R.id.FullName);
        mprofileImage = findViewById(R.id.profileImage);
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        mprofilebtn = (LinearLayout) findViewById(R.id.profilebtn);
        mprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserActivity();
            }
        });

        mappthistorybtn = (LinearLayout) findViewById(R.id.appthistorybtn);
        mappthistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApptHistory();
            }
        });

        mappointmentbtn = (LinearLayout) findViewById(R.id.appointmentbtn);
        mappointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointment();
            }
        });

        mfeedbackbtn = (LinearLayout) findViewById(R.id.feedbackadminbtn);
        mfeedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback();
            }
        });

        mserviceinfobtn = (LinearLayout) findViewById(R.id.serviceinfobtn);
        mserviceinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarServiceInformation();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                usersData = datasnapshot.getValue(UsersData.class);
                assert usersData != null;
                mFullName.setText(usersData.getFullname());
                if(usersData.getImageURL().equals("default")){
                    mprofileImage.setImageResource(R.drawable.ic_launcher_background);
                }else{
                    Glide.with(getApplicationContext()).load(usersData.getImageURL()).into(mprofileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                View mView = LayoutInflater.from(MainActivity.this).inflate(R.layout.select_image_layout,null);
                RecyclerView recyclerView = mView.findViewById(R.id.recyclerView);
                collectOldImages();
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                recyclerView.setHasFixedSize(true);
                imagesRecyclerAdapter = new ImagesRecyclerAdapter(imagesList,MainActivity.this);
                recyclerView.setAdapter(imagesRecyclerAdapter);
                imagesRecyclerAdapter.notifyDataSetChanged();
                Button openImage = mView.findViewById(R.id.openImage);
                openImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openImage();
                    }
                });
                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            if (storageTask != null && storageTask.isInProgress()){
                Toast.makeText(this,"Uploading in progress", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image");
        progressDialog.show();
        if(imageUri != null){
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,25,byteArrayOutputStream);
            byte[] imageFileToByte = byteArrayOutputStream.toByteArray();
            final StorageReference imageReference = storageReference.child(usersData.getFullname()+System.currentTimeMillis()+".jpg");
            storageTask = imageReference.putBytes(imageFileToByte);
            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String sDownloadUri = downloadUri.toString();
                        Map<String,Object> hashMap = new HashMap<>();
                        hashMap.put("imageUrl",sDownloadUri);
                        databaseReference.updateChildren(hashMap);
                        final DatabaseReference profileImagesReference = FirebaseDatabase.getInstance().getReference("profile_images").child(firebaseUser.getUid());
                        profileImagesReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.changepassword){
            startActivity(new Intent(MainActivity.this,ChangePasswordActivity.class));
        }
        else if(id == R.id.Logout){
            fAuth.signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }
        return true;
    }

    private void collectOldImages() {
        DatabaseReference imageListReference = FirebaseDatabase.getInstance().getReference("profile_images").child(firebaseUser.getUid());
        imageListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imagesList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    imagesList.add(snapshot.getValue(ImagesList.class));
                }
                imagesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void UpdateUserActivity(){
        Intent intent = new Intent(this, UpdateUserActivity.class);
        startActivity(intent);
    }
    public void Appointment(){
        Intent intent = new Intent(this, CarService.class);
        startActivity(intent);
    }
    public void Feedback(){
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }
    public void ApptHistory(){
        Intent intent = new Intent(this, ListAppointmentMainActivity.class);
        startActivity(intent);
    }
    public void CarServiceInformation(){
        Intent intent = new Intent(this, CarServiceInfo.class);
        startActivity(intent);
    }
}


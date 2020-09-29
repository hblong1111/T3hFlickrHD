package com.longhb.flickrhd.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.longhb.flickrhd.App;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.CommentAdapter;
import com.longhb.flickrhd.adpater.ImageDetailAdapterViewPager;
import com.longhb.flickrhd.databinding.ActivityImageDetailBinding;
import com.longhb.flickrhd.model.Comment;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.viewmodel.DetailViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private ActivityImageDetailBinding binding;
    private AlertDialog alertDialog;

    private List<Image> images;
    private List<Comment> comments;
    private int pos = -1;
    private ImageDetailAdapterViewPager adapterViewPager;
    private CommentAdapter commentAdapter;

    public DetailViewModel viewModel;

    private Image imageCur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClick();
        createData();
    }


    private void setOnClick() {
        binding.btnActionAddFavourite.setOnClickListener(this);
        binding.btnActionDownload.setOnClickListener(this);
        binding.btnActionSetWall.setOnClickListener(this);
        binding.multipleActionsLeft.setOnClickListener(this);
        binding.btnMore.setOnClickListener(this);
        binding.btnComment.setOnClickListener(this);
    }

    private void createData() {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication(), this, "")).get(DetailViewModel.class);
        images = new ArrayList<>();
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);

        viewModel.getmListImage().observe(this, images1 -> settingViewPager(images1));
        viewModel.getComments().observe(this, comments1 -> {
            comments.clear();
            comments.addAll(comments1);
            commentAdapter.notifyDataSetChanged();
        });
    }

    private void settingViewPager(List<Image> images1) {
        images.addAll(images1);
        adapterViewPager = new ImageDetailAdapterViewPager(getSupportFragmentManager(), images, viewModel);
        binding.viewPager.setAdapter(adapterViewPager);
        viewModel.getCurPage().observe(this, integer -> {
            if (pos == -1) {
                pos = integer;
                binding.viewPager.setCurrentItem(pos);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
        finish();
    }


    @Override
    public void onClick(View view) {
        Image image = images.get(binding.viewPager.getCurrentItem());
        imageCur = image;
        switch (view.getId()) {
            case R.id.btn_action_add_favourite:
                addImageToFavourite(image);
                break;

            case R.id.btn_action_set_wall:
                new SetWallPaperTask().execute(image.getUrl_o());
                break;
            case R.id.btn_action_download:
                downloadImage(image);
                break;
            case R.id.btn_more:
                openPopupMenu();
                break;
            case R.id.btn_comment:
                showDialogComment(image.getId());
                break;
        }
    }

    private void showDialogComment(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.CustomDialog1);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_comment, null);

        ImageButton btnColse;
        RecyclerView rvComment;

        btnColse = view.findViewById(R.id.btn_colse);
        rvComment = view.findViewById(R.id.rv_comment);

        btnColse.setOnClickListener(view1 -> alertDialog.dismiss());

        rvComment.setAdapter(commentAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(this));

        dialog.setView(view);
        dialog.create();
        alertDialog = dialog.show();
        viewModel.getAllCommentNetwork(id);
    }

    private void openPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, binding.btnMore);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            Intent intent = null;
            switch (menuItem.getItemId()) {
                case R.id.popup_item_home:
                    intent = new Intent(ImageDetailActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_left, R.anim.out_right);
                    break;
                case R.id.popup_item_favourite:
                    intent = new Intent(ImageDetailActivity.this, ImagesFavouriteActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_left, R.anim.out_right);
                    break;
                case R.id.popup_item_about:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/longhb132"));
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_left, R.anim.out_right);
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void addImageToFavourite(Image image) {
        long insert = viewModel.insertImageFavourite(image);
        if (insert >= 0) {
            Toast.makeText(this, "Thêm ảnh vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gặp lỗi khi thêm ảnh vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadImage(Image image) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        } else {
            new DownloadImageTask().execute(new String[]{image.getUrl_o(), image.getId()});
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            Log.e("longhbs", grantResults[0] + "|" + grantResults[1]);
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                new DownloadImageTask().execute(new String[]{imageCur.getUrl_o(), imageCur.getId()});
            }
        }
    }

    class DownloadImageTask extends AsyncTask<String, Integer, Void> {
        NotificationManagerCompat notificationManager;
        NotificationCompat.Builder builder;
        private int notificationId = 99;
        String fileName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notificationManager = NotificationManagerCompat.from(getApplicationContext());
            builder = new NotificationCompat.Builder(getApplicationContext(), App.CHANNEL_ID);
            builder.setContentTitle("Chuẩn bị ...")
                    .setContentText("Đang chờ")
                    .setSmallIcon(R.drawable.ic_download)
                    .setPriority(NotificationCompat.PRIORITY_LOW);
            int PROGRESS_MAX = 100;
            int PROGRESS_CURRENT = 0;
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
            notificationManager.notify(notificationId, builder.build());


        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                //and connect!
                urlConnection.connect();
                //set the path where we want to save the file in this case, going to save it on the root directory of the sd card.
                File SDCardRoot = Environment.getExternalStorageDirectory();
                Log.i("longhbs", "SDCard: " + SDCardRoot.exists());
                //create a new file, specifying the path, and the filename which we want to save the file as.
                File file = new File(SDCardRoot, "Flickr HD-" + strings[1] + ".jpg");
                fileName = file.getName();
                if (file.exists()) {
                    file.mkdirs();
                    file.createNewFile();
                }
                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);
                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();
                //this is the total size of the file
                int totalSize = urlConnection.getContentLength();
                //variable to store total downloaded bytes
                int downloadedSize = 0;
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer
                //now, read through the input buffer and write the contents to the file
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    //this is where you would do something to report the prgress, like this maybe
                    //updateProgress(downloadedSize, totalSize);
                    publishProgress((downloadedSize * 100) / totalSize);
                }

                fileOutput.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("longhbs", e.getMessage());
            } catch (ProtocolException e) {
                e.printStackTrace();
                Log.e("longhbs", e.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("longhbs", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            builder.setContentTitle("Đang tải ...")
                    .setContentText(values[0] + "%")
                    .setProgress(100, values[0], false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            builder.setContentTitle("Tải xuống hoàn tất!")
                    .setContentText(fileName)
                    .setProgress(0, 0, false);
            notificationManager.notify(notificationId, builder.build());
        }
    }

    class SetWallPaperTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap result = null;
            try {
                result = Picasso.get()
                        .load(strings[0])
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(ImageDetailActivity.this);
            try {
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(ImageDetailActivity.this, "Ok", Toast.LENGTH_SHORT).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
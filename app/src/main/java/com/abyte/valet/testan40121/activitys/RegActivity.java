package com.abyte.valet.testan40121.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.abyte.valet.testan40121.R;
import com.abyte.valet.testan40121.adapters.SectionsPagerAdapter;
import com.abyte.valet.testan40121.db.PersonDB;
import com.abyte.valet.testan40121.fragments.PlaceholderFragment;
import com.abyte.valet.testan40121.loading.LoadingDialog;
import com.abyte.valet.testan40121.model.person.Person;
import com.abyte.valet.testan40121.rest.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegActivity extends AppCompatActivity {

    public static final int REQUEST_ICON = 5;
    private ImageView imageViewIcon;
    private LoadingDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            PlaceholderFragment.setIcon(uri, this);
            Log.i(AddActivity.TAG, "onActivityResult: " + uri.toString());
            imageViewIcon.setImageURI(uri);
        }
        else if (resultCode != 2) finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Войти");
                    break;
                case 1:
                    tab.setText("Регистрация");
                    break;
            }
        });
        mediator.attach();

        dialog = new LoadingDialog(this);

    }

    public void setIcon(View v){
        imageViewIcon = (ImageView) v;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_ICON);
    }

    public static void registration(Activity activity, PersonDB db, RequestBody body,
                                    MultipartBody.Part part, String name, String password, View view){
        RetrofitClient.registrationUser(new Callback<Person>() {
            @Override
            public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                Person person = response.body();
                activity.runOnUiThread(() -> {
                    if (person != null) {
                        db.addPerson(person, person.getPhotoName());
                        Intent i = new Intent(activity, MainActivity.class);
                        MainActivity.setPerson(person);
                        RetrofitClient.startDownload(activity);
                        RetrofitClient.startDownloadByUserID(person.getId(), activity);
                        ((RegActivity) activity).getDialog().stopDialog();
                        activity.startActivityForResult(i, MainActivity.R_CODE);

                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
                ((RegActivity) activity).getDialog().stopDialog();
                Snackbar.make(view, "Произошла неизвестная ошибка", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }, name, password, body, part);

    }

    public LoadingDialog getDialog() {
        return dialog;
    }
}

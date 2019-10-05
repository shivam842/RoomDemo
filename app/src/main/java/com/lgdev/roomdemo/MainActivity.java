package com.lgdev.roomdemo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lagger on 16-08-2019.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private Button btnSubmit, btnClearDb;
    private TextInputEditText edtFName, edtLName, edtContact;
    private AppDatabase mDb;
    private RecyclerView rvUserRecord;
    private AdapterUser adapterUser;
    private List<User> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mDb = AppDatabase.getAppDatabase(mContext);
        initView();
    }

    private void initView() {
        edtFName = findViewById(R.id.edt_f_name);
        edtLName = findViewById(R.id.edt_l_name);
        edtContact = findViewById(R.id.edt_contact);
        btnSubmit = findViewById(R.id.btn_submit);
        btnClearDb = findViewById(R.id.btn_clear_db);

        rvUserRecord = findViewById(R.id.rv_user_record);
        rvUserRecord.setLayoutManager(new LinearLayoutManager(mContext));
        rvUserRecord.setHasFixedSize(true);
        rvUserRecord.setItemAnimator(new DefaultItemAnimator());
        getUserData();

        btnClearDb.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

       /* userList = AppDatabase.userDao().getAll();
        if (userList != null) {
            for (int i = 0; i < userList.size(); i++) {
                User model = userList.get(i);
                txtDBMessage.setText("First Name : " + model.getFirstName() + "\n" +
                        "Last Name : " + model.getLastName() + "\n" +
                        "Mobile : " + model.getContact() + "\n");
            }
        }*/
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(edtFName.getText().toString().trim())) {
            Toast.makeText(mContext, "all fields are compulsory", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtLName.getText().toString().trim())) {
            Toast.makeText(mContext, "all fields are compulsory", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtContact.getText().toString().trim())) {
            Toast.makeText(mContext, "all fields are compulsory", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_db:
                mDb.userDao().deleteAll();
                getUserData();
                break;
            case R.id.btn_submit:
                if (validateForm()) {
                    if (AppDatabase.getAppDatabase(mContext) == null) {
                        InitDatabase.populateAsync(AppDatabase.getAppDatabase(mContext));
                    } else {
                        User user = new User();
                        user.setFirstName(edtFName.getText().toString().trim());
                        user.setLastName(edtLName.getText().toString().trim());
                        user.setContact(edtContact.getText().toString().trim());
                        InitDatabase.addUser(mContext, user);
                        edtFName.setText("");
                        edtLName.setText("");
                        edtContact.setText("");
                        getUserData();
                    }
                }
                break;
        }
    }

    private void getUserData() {
        mList.clear();
        mList = mDb.userDao().getAll();
        adapterUser = new AdapterUser(mContext, mList);
        rvUserRecord.setAdapter(adapterUser);
        adapterUser.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}

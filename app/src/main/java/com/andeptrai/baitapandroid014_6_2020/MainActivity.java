package com.andeptrai.baitapandroid014_6_2020;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.andeptrai.baitapandroid014_6_2020.student_manage.Database;
import com.andeptrai.baitapandroid014_6_2020.student_manage.OnItemEditClickListener;
import com.andeptrai.baitapandroid014_6_2020.student_manage.Student;
import com.andeptrai.baitapandroid014_6_2020.student_manage.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.andeptrai.baitapandroid014_6_2020.R.drawable.background_actionbar;

public class MainActivity extends AppCompatActivity implements OnItemEditClickListener {

    Database database;
    ListView listView;
    List<Student> studentList;
    List<Student> studentSearchList;
    StudentAdapter adapter, adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bài tập 4/6");

        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_actionbar));


        //tao database
        database = new Database(this, "ghichu.sqlite", null, 1);

        //tao bang cong viec
        database.QueryData("CREATE TABLE IF NOT EXISTS Student(mssv INTEGER PRIMARY KEY" +
                ", Name VARCHAR(200), NgaySinh VARCHAR(50), Email VARCHAR(100), HomeTown VARCHAR(200))");

        //insert data
//        database.QueryData("INSERT INTO CongViec VALUES(null, 'Lam bai tap android 2')");

        listView = findViewById(R.id.listMode);
        studentList = new ArrayList<>();
        adapter = new StudentAdapter(MainActivity.this, studentList, this);
        listView.setAdapter(adapter);

        getDataStudent();



    }

    private void getDataStudent(){
        studentList.clear();
        Cursor dataStudent = database.getData("SELECT * FROM Student");
        while (dataStudent.moveToNext()){
            int mssv = dataStudent.getInt(0);
            String ten = dataStudent.getString(1);
            String ngaysinh = dataStudent.getString(2);
            String email = dataStudent.getString(3);
            String hometown = dataStudent.getString(4);
            Toast.makeText(MainActivity.this, mssv+","+ten+","+ngaysinh+","+email+","+hometown, Toast.LENGTH_LONG).show();
            studentList.add(new Student(mssv, ten, ngaysinh, email, hometown));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu); return super.onCreateOptionsMenu(menu);
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            DialogAdd();
        }
        else if(id == R.id.action_update) {
            Update();
        }
        else if(id == R.id.action_delete) {
        }
        else if(id == R.id.action_search) {
            search();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Update() {
        listView.setAdapter(adapter);
        getDataStudent();
    }

    private void search() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search);

        final EditText edtMssvSearch = dialog.findViewById(R.id.edtMssvSearch);
        final EditText edtNameSearch = dialog.findViewById(R.id.edtNameSearch);
        Button btnSearchWork = dialog.findViewById(R.id.btnSearchWork);
        Button btnCancelSearch = dialog.findViewById(R.id.btnCancelSearch);

        btnSearchWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mssv = edtMssvSearch.getText().toString();
                final String name = edtNameSearch.getText().toString();
                if (mssv.equalsIgnoreCase("") && name.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập mssv hoặc tên!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Cursor dataStudent = database.getData("SELECT * FROM Student");
                    studentSearchList = new ArrayList<>();
                    while (dataStudent.moveToNext()){
                        int mssv1 = dataStudent.getInt(0);
                        String ten = dataStudent.getString(1);
                        String ngaysinh = dataStudent.getString(2);
                        String email = dataStudent.getString(3);
                        String hometown = dataStudent.getString(4);
                        Toast.makeText(MainActivity.this, mssv+","+ten+","+ngaysinh+","+email+","+hometown, Toast.LENGTH_LONG).show();
                        if (ten.equalsIgnoreCase(name) || mssv.equalsIgnoreCase(mssv1+"")){
                            studentSearchList.add(new Student(mssv1, ten, ngaysinh, email, hometown));
                        }
                    }
                    adapter2 = new StudentAdapter(MainActivity.this, studentSearchList, MainActivity.this);
                    listView.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                }
            }
        });

        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_work);

        final EditText edtMssvAdd = dialog.findViewById(R.id.edtMssvAdd);
        final EditText edtNameAdd = dialog.findViewById(R.id.edtNameAdd);
        final EditText edtEmailAdd = dialog.findViewById(R.id.edtEmailAdd);
        final EditText edtBirthAdd = dialog.findViewById(R.id.edtBirthAdd);
        final EditText edtHomeTownAdd = dialog.findViewById(R.id.edtHomeTownAdd);
        Button btnAddWork = dialog.findViewById(R.id.btnAddWork);
        Button btnCancelAdd = dialog.findViewById(R.id.btnCancelAdd);

        btnAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mssv = edtMssvAdd.getText().toString();
                final String name = edtNameAdd.getText().toString();
                final String email = edtEmailAdd.getText().toString();
                final String birth = edtBirthAdd.getText().toString();
                final String homeTown = edtHomeTownAdd.getText().toString();
                if (mssv.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập mssv!", Toast.LENGTH_SHORT).show();
                }
                else if (name.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập tên!", Toast.LENGTH_SHORT).show();
                }
                else if (email.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập email!", Toast.LENGTH_SHORT).show();
                }
                else if (birth.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập ngay sinh!", Toast.LENGTH_SHORT).show();
                }
                else if (homeTown.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập quê quán!", Toast.LENGTH_SHORT).show();
                }
                else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Add")
                            .setMessage("Are you sure you want to add?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.QueryData("INSERT INTO Student VALUES('"+mssv+"', '" + name + "', '" + birth + "', '" + email + "', '" + homeTown + "')");
                                    Toast.makeText(MainActivity.this, "Đã thêm!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    getDataStudent();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void OnItemEdit(int position, int id) {
        DialogEdit(position, id);
    }

    private void DialogEdit(int position, final int mssv) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);

        final EditText edtNameEdit = dialog.findViewById(R.id.edtNameEdit);
        final EditText edtEmailEdit = dialog.findViewById(R.id.edtEmailEdit);
        final EditText edtHomeTownEdit = dialog.findViewById(R.id.edtHomeTownEdit);
        final EditText edtBirthEdit = dialog.findViewById(R.id.edtBirthEdit);
        Button btnEditWork = dialog.findViewById(R.id.btnEditWork);
        Button btnCancelEdit = dialog.findViewById(R.id.btnCancelEdit);

        edtNameEdit.setText(studentList.get(position).getName());
        edtEmailEdit.setText(studentList.get(position).getEmail());
        edtHomeTownEdit.setText(studentList.get(position).getHomeTown());
        edtBirthEdit.setText(studentList.get(position).getBirth());

        btnEditWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameNew = edtNameEdit.getText().toString();
                final String emailNew = edtEmailEdit.getText().toString();
                final String birthNew = edtBirthEdit.getText().toString();
                final String homeTownNew = edtHomeTownEdit.getText().toString();
                if (nameNew.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập tên!", Toast.LENGTH_SHORT).show();
                }
                else if (emailNew.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập email!", Toast.LENGTH_SHORT).show();
                }
                else if (birthNew.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập ngay sinh!", Toast.LENGTH_SHORT).show();
                }
                else if (homeTownNew.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập quê quán!", Toast.LENGTH_SHORT).show();
                }
                else{

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Edit")
                            .setMessage("Are you sure you want to edit this student?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.QueryData("UPDATE Student SET "+" Name = '" + nameNew + "',"+
                                            " Email = '" + emailNew + "',"+" NgaySinh = '" + birthNew + "',"+" HomeTown = '" + homeTownNew + "'"+" WHERE mssv = '"+ (mssv) +"'");
                                    Toast.makeText(MainActivity.this, "Đã sửa!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    getDataStudent();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void OnItemDelete(final int mssv) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        database.QueryData("DELETE from Student WHERE mssv = '"+ (mssv) +"'");
                        Toast.makeText(MainActivity.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                        getDataStudent();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}

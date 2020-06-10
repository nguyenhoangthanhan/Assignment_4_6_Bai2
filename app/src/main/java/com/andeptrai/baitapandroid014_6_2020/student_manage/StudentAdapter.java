package com.andeptrai.baitapandroid014_6_2020.student_manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.andeptrai.baitapandroid014_6_2020.R;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    private Context context;
    private List<Student> studentList;
    private OnItemEditClickListener editClickListener;

    public StudentAdapter(Context context, List<Student> studentList, OnItemEditClickListener onItemEditClickListener) {
        this.context = context;
        this.studentList = studentList;
        this.editClickListener = onItemEditClickListener;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =layoutInflater.inflate(R.layout.layout_student_manage, null);
            holder.txtMssv = convertView.findViewById(R.id.txtMssv);
            holder.txtEmail = convertView.findViewById(R.id.txtEmail);
            holder.txtBirth = convertView.findViewById(R.id.txtBirth);
            holder.txtName = convertView.findViewById(R.id.txtName);
            holder.txtHomeTown = convertView.findViewById(R.id.txtHomeTown);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Student student = studentList.get(position);

        holder.txtMssv.setText(student.getMssv()+"");
        holder.txtName.setText(student.getName());
        holder.txtEmail.setText(student.getEmail());
        holder.txtBirth.setText(student.getBirth());
        holder.txtHomeTown.setText(student.getHomeTown());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClickListener.OnItemEdit(position, studentList.get(position).getMssv());
                Toast.makeText(context, "Sửa " + student.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClickListener.OnItemDelete(studentList.get(position).getMssv());
            }
        });


        return convertView;

    }

    private class ViewHolder{
        TextView txtMssv, txtName, txtEmail, txtHomeTown, txtBirth;
        Button btnEdit, btnDelete;
    }
}

package com.example.roomdb;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public List<MyData> myDataList;
    Activity context;
    RoomDbClass roomDB;

    public MainAdapter(Activity context,List<MyData> myDataList)
    {
        this.context=context;
        this.myDataList=myDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //initialize the MyData class
        MyData data=myDataList.get(position);
        //initialize the database
        roomDB= RoomDbClass.getInstance(context);

        holder.textView.setText(data.getText());

        holder.btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyData data1=myDataList.get(holder.getAdapterPosition());
                //get the value from db
                int id=data1.getId();
                String text=data1.getText();

                //Create dialogBox

                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_design);
                //initialize width
                int width= WindowManager.LayoutParams.MATCH_PARENT;

                //initialize height
                int height=WindowManager.LayoutParams.WRAP_CONTENT;

                //setLayout
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText editText=dialog.findViewById(R.id.aedit);
                Button abtn=dialog.findViewById(R.id.abtn);

                editText.setText(text);

                abtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        String uText=editText.getText().toString().trim();
                        //Update text in database
                        roomDB.mainDao().update(id,uText);
                        //Notify when data is updated
                        myDataList.clear();
                        myDataList.addAll(roomDB.mainDao().getAll());
                        notifyDataSetChanged();


                    }
                });



            }
        });

        holder.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyData d=myDataList.get(holder.getAdapterPosition());

                roomDB.mainDao().delete(d);

                //Notify when data is changed
                int position=holder.getAdapterPosition();
                myDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,myDataList.size());

            }
        });


    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView btedit,btdelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.designtext);
            btedit=itemView.findViewById(R.id.designedit);
            btdelete=itemView.findViewById(R.id.designdelete);

        }
    }
}

package com.example.myacccounts;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccounts.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

   private ArrayList<Users> users;
   private OnNoteListener mOnNoteListener;



    public UserAdapter(ArrayList<Users> users,OnNoteListener onNoteListener) {
        this.users = users;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.userslayout, parent, false);
        return new UserHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder, final int position) {
        final Users user = users.get(position);
        holder.tv.setText(user.name);
        holder.tv2.setText(user.place);
        if (user.closing>0){
           holder.debi.setVisibility(View.INVISIBLE); }
        else if (user.closing==0){
            holder.credi.setVisibility(View.GONE);
            holder.debi.setVisibility(View.GONE);
        }
        else{
            holder.credi.setVisibility(View.INVISIBLE);
        }
        holder.tv6.setText(String.valueOf(Math.abs(user.closing)));

       /* if (user.closing==0){
            holder.tv6.setText(user.openingBalance);
        }
        else {
            holder.tv6.setText(String.valueOf(Math.abs(user.closing)));
        }*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onNoteListener.OnNoteClick(user);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Toast.makeText(v.getContext(), "longclick", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setCancelable(true);
                builder.setTitle("Delete!!!");
                builder.setMessage("Do you want to delete?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(), "not deleted", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(), "yes deleted", Toast.LENGTH_SHORT).show();
                        DbHelper dbHelper= new DbHelper(v.getContext());
                        dbHelper.deleteRow(user.id);

                        users.remove(position);//hugyugujg
//                        notifyDataSetChanged();
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, users.size());

                    }
                });
                builder.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tv2;
        TextView tv6;
        ImageView credi;
        ImageView debi;


        OnNoteListener onNoteListener;

        public UserHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            tv=itemView.findViewById(R.id.textView4);
            tv2=itemView.findViewById(R.id.textView5);
            tv6=itemView.findViewById(R.id.textView12);
            credi=itemView.findViewById(R.id.imageView2);
            debi=itemView.findViewById(R.id.imageView3);
            this.onNoteListener=onNoteListener;

        }

    }

    public interface OnNoteListener{

        void OnNoteClick(Users user);

    }

}


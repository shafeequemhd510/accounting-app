package com.example.myacccounts;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccounts.R;

import java.util.ArrayList;

public class UserAdapter2 extends RecyclerView.Adapter<UserAdapter2.UserHolder> {

   private ArrayList<Trasaction> trasaction;
//   private OnNoteListener mOnNoteListener;



    public UserAdapter2(ArrayList<Trasaction> trasaction) {
        this.trasaction = trasaction;
//        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.userslayout2, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder,final int position) {
        final Trasaction transaction = trasaction.get(position);

        holder.tv6.setText(transaction.discription);
        if(transaction.credit==null){
            holder.itemView.setBackgroundColor(Color.rgb(254,206,198));
            holder.tv18.setText(String.format("%.2f",Double.parseDouble(transaction.debit)));
        }else {
            holder.itemView.setBackgroundColor(Color.rgb(177,251,153));
            holder.tv5.setText(String.format("%.2f",Double.parseDouble(transaction.credit)));
        }
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View  v) {
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
                            dbHelper.deleteRowTransaction(transaction.id);
                            trasaction.remove(position);//hugyugujg
//                        notifyDataSetChanged();
                            notifyItemChanged(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, trasaction.size());

                        }
                    });
                    builder.show();

                    return true;
                }
            });
    }

    @Override
    public int getItemCount() {
        return trasaction.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {
        TextView tv5;
        TextView tv6;
        TextView tv18;
//        OnNoteListener onNoteListener;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tv5=itemView.findViewById(R.id.textView16);
            tv6=itemView.findViewById(R.id.textView26);
            tv18=itemView.findViewById(R.id.textView18);
//            itemView.setOnClickListener(this);
//            this.onNoteListener=onNoteListener;

        }


     /*   @Override
        public void onClick(View v) {
        onNoteListener.OnNoteClick(getAdapterPosition());
        }*/
    }

    public interface OnNoteListener{

        void OnNoteClick(int position);

    }

}


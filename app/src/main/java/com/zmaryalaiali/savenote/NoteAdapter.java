package com.zmaryalaiali.savenote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends FirestoreRecyclerAdapter<NoteModel, NoteAdapter.MyViewHolder> {

    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<NoteModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull NoteModel model) {
        // generate new color for layout
        int color = getColor();
        // set the background color
        holder.cardNote.setBackgroundColor(context.getResources().getColor(color));
        //
     String docId =   this.getSnapshots().getSnapshot(position).getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(context,UpdateNoteActivity.class);
              intent.putExtra("title",model.getTitle());
              intent.putExtra("description",model.getDescription());
              intent.putExtra("createDate",model.getCreateDate());
              intent.putExtra("docId",docId);
              context.startActivity(intent);
            }
        });

        // imageView more click
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenu().add("Delete");
                popupMenu.getMenu().add("Share");
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Delete")) {
                            getSnapshots().getSnapshot(position).getReference().delete();
                            Toast.makeText(context, "Delete clicked here", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if (menuItem.getTitle().equals("Share")) {
                            Toast.makeText(context, "Share clicked here", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return true;
                    }
                });


            }
        });

        holder.tvTitle.setText(model.getTitle());
        holder.tvDescription.setText(model.getDescription());
        holder.tvCreateDate.setText(model.getCreateDate());
        holder.tvUpdateDate.setText(model.getUpdateDate());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize our note layout here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_note, parent, false);
        //pass this view to MyViewHolder class
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvCreateDate, tvUpdateDate;
        ImageView ivMore;
        CardView cardNote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreateDate = itemView.findViewById(R.id.tv_create_date);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvUpdateDate = itemView.findViewById(R.id.tv_update_date);
            ivMore = itemView.findViewById(R.id.iv_more);
            cardNote = itemView.findViewById(R.id.card_note);
        }
    }

    private int getColor() {

        List<Integer> color = new ArrayList<>();
        // add all color to this list
        color.add(R.color.color2);
        color.add(R.color.color1);
        color.add(R.color.color3);
        color.add(R.color.color4);
        color.add(R.color.color5);
        color.add(R.color.color6);
        color.add(R.color.blue);
        color.add(R.color.skyblue);
        color.add(R.color.lightGreen);
        color.add(R.color.green);
        // this is random number
        Random random = new Random();

        int number = random.nextInt(color.size());
        // get random color from list
        return color.get(number);
    }
}

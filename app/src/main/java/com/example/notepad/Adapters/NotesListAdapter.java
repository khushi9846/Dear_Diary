package com.example.notepad.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.Modles.Notes;
import com.example.notepad.NotesClickListener;
import com.example.notepad.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter  extends RecyclerView.Adapter<NotesViewHolder>{
   Context context;
   List<Notes> list;
  NotesClickListener listener;


    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list , parent , false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
      holder.textview_title.setText(list.get(position).getTitle());
      holder.textview_title.setSelected(true);

      holder.textView_notes.setText(list.get(position).getNotes());

      holder.textview_date.setText(list.get(position).getDate());
      holder.textview_date.setSelected(true);

      if (list.get(position).getPinned()){
          holder.imageView_pin.setImageResource(R.drawable.ic_baseline_push_pin_24);
      }
      else{
          holder.imageView_pin.setImageResource(0);

      }
      int color_code = getRandomColor();
      holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

      holder.notes_container.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              listener.onClick(list.get(holder.getAdapterPosition()));
          }
      });
      holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View view) {
              listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_container);
              return true;
          }
      });

    }
private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.yellow);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.light_green);
        colorCode.add(R.color.aqua);
        colorCode.add(R.color.orange);
        colorCode.add(R.color.white);

        Random random= new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color );
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
        
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container;
    TextView textview_title;
    ImageView imageView_pin;
    TextView textView_notes , textview_date;
    public  NotesViewHolder(@NonNull View itemView){
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        textview_title = itemView.findViewById(R.id.textview_title);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textview_date = itemView.findViewById(R.id.textview_date);

    }
}
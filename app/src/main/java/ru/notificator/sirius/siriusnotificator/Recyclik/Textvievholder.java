package ru.notificator.sirius.siriusnotificator.Recyclik;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.notificator.sirius.siriusnotificator.R;

public class Textvievholder extends RecyclerView.ViewHolder {
    private final AdaptClickList mclicklist;
    private TextView mText;
    private ImageView imageView;
    public Textvievholder(@NonNull View itemView,AdaptClickList listen) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        mText = itemView.findViewById(R.id.textView2_item);
        mclicklist = listen;
    }

    public void bind(Boi boi){
       mText.setText(boi.getName()+" "+boi.getSurname());
    itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mclicklist.onActionClick(1,getAdapterPosition());
            return true;
        }
    });

}}

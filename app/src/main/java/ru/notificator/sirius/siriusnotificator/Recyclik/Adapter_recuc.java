package ru.notificator.sirius.siriusnotificator.Recyclik;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ru.notificator.sirius.siriusnotificator.Boiz_saver;
import ru.notificator.sirius.siriusnotificator.R;

import static ru.notificator.sirius.siriusnotificator.AdminMain.boiz;
import static ru.notificator.sirius.siriusnotificator.AdminMain.file;

public class Adapter_recuc extends RecyclerView.Adapter<Textvievholder> implements AdaptClickList {
    private List<Boi> mlist;
    private AlertDialog.Builder ad;

    public Adapter_recuc(List<Boi> mlist) {
        this.mlist = mlist;
    }
    public View view;
    @NonNull
    @Override
    public Textvievholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_viev, viewGroup, false);
        return new Textvievholder(view,this) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Textvievholder textvievholder, int i) {
        textvievholder.bind(mlist.get(i));
    }

    @Override
    public int getItemCount() {
        return mlist!=null? mlist.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onActionClick(int action, final int position) {
        if (action==1){
            String title = "Предупреждение";
            String message = "Удалить пользователя?";
            String button1String = "Да";
            String button2String = "Нет";

            ad = new AlertDialog.Builder(view.getContext());
            ad.setTitle(title);
            ad.setMessage(message);
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    boiz.remove(position);
                    Boiz_saver.save_boiz(boiz, file);
                    Toast.makeText(view.getContext(), "Пользователь удален",
                            Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    Toast.makeText(view.getContext(), "Отменено", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            ad.show();
        }
        }

    }
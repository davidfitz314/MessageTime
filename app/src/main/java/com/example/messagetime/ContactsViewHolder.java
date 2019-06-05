package com.example.messagetime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView idView;
    private TextView nameView;
    private TextView numberView;
    private ContactItemClickListener listener;

    public ContactsViewHolder(@NonNull View itemView, ContactItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);

        idView = itemView.findViewById(R.id.idView);
        nameView = itemView.findViewById(R.id.nameView);
        numberView = itemView.findViewById(R.id.numberView);
    }

    public TextView getIdView() {
        return idView;
    }

    public TextView getNameView() {
        return nameView;
    }

    public TextView getNumberView() {
        return numberView;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, getAdapterPosition());
    }
}

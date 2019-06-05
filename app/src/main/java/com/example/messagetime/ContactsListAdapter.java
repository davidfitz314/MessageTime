package com.example.messagetime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter {
    LayoutInflater inflater;
    List<ContactsInformation> peopleList;
    private ContactItemClickListener itemClickListener;

    ContactsListAdapter(Context context, ContactItemClickListener clickListener){
        inflater = LayoutInflater.from(context);
        //peopleList = listIn;
        itemClickListener = clickListener;
    }


    public void setPeopleList(List<ContactsInformation> peopleIn){
        this.peopleList = peopleIn;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.contact_each, viewGroup, false);
//        return new ContactsViewHolder(itemView);
        return new ContactsViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ContactsViewHolder contactsViewHolder = (ContactsViewHolder) viewHolder;
        TextView idView = contactsViewHolder.getIdView();
        TextView nameView = contactsViewHolder.getNameView();
        TextView numberView = contactsViewHolder.getNumberView();
        idView.setText(peopleList.get(i).getId());
        nameView.setText(peopleList.get(i).getName());
        numberView.setText(peopleList.get(i).getNumber());

    }

    @Override
    public int getItemCount() {
        if (peopleList != null){
            return peopleList.size();
        }
        return 0;
    }
}

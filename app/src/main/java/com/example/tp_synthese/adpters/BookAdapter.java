package com.example.tp_synthese.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tp_synthese.R;
import com.example.tp_synthese.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
           convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_book_item,parent,false);
        }

        Book book = getItem(position);
        ImageView imgView = convertView.findViewById(R.id.book_image);
        TextView txtTitle = convertView.findViewById(R.id.textViewTitle);
        TextView txtAuthor = convertView.findViewById(R.id.textViewAuthor);
        TextView txtDescription = convertView.findViewById(R.id.textViewDiscription);
        txtTitle.setText(book.getVolumeInfo().getTitle());
        txtAuthor.setText(book.getVolumeInfo().getAuthors().toString());
        txtDescription.setText(book.getVolumeInfo().getDescription());
        // afficher l'url dans l'image view
        Picasso.get().load(book.getVolumeInfo().getImageLinks().getThumbnail()).into(imgView);
        return convertView;
    }
}

package com.example.tp_synthese.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.tp_synthese.R;
import com.example.tp_synthese.models.Book;
import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");

        TextView textViewTitle = findViewById(R.id.textViewTitle1);
        TextView textViewAuthors = findViewById(R.id.textViewAuthors1);
        TextView textViewDescription = findViewById(R.id.textViewDescription1);
        ImageView imageViewBook = findViewById(R.id.imageViewBook1);

        Button buttonShare = findViewById(R.id.buttonShare);

        textViewTitle.setText(book.getVolumeInfo().getTitle());
        textViewAuthors.setText(book.getVolumeInfo().getAuthors().toString());
        textViewDescription.setText(book.getVolumeInfo().getDescription());
        Picasso.get().load(book.getVolumeInfo().getImageLinks().getThumbnail().replace("http://", "https://")).into(imageViewBook);

        buttonShare.setOnClickListener(view -> {
            String shareMsg = "Découvrez ce livre : \nTitre : " + book.getVolumeInfo().getTitle() + "\n"
                    + "Auteurs : " + book.getVolumeInfo().getAuthors().toString() + "\n"
                    + "Description : " + book.getVolumeInfo().getDescription();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
            startActivity(Intent.createChooser(shareIntent, "Partager via"));
        });
    }
}
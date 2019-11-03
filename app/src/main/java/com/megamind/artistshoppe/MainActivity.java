package com.megamind.artistshoppe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.megamind.artistshoppe.Database.DBHelper;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {
    EditText id, firstName, lastName, albumName, noOfAlbums, price;
    AppCompatButton add, update, remove, read, clear;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.artistID);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        albumName = findViewById(R.id.album);
        noOfAlbums = findViewById(R.id.noOfTracks);
        price = findViewById(R.id.price);
        add = findViewById(R.id.add);
        update = findViewById(R.id.update);
        remove = findViewById(R.id.remove);
        read = findViewById(R.id.read);
        clear = findViewById(R.id.clear);

        helper = new DBHelper(this, "", null, 1);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                firstName.setText("");
                lastName.setText("");
                albumName.setText("");
                noOfAlbums.setText("");
                price.setText("");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long added = helper.addArtist(firstName.getText().toString(), lastName.getText().toString(), albumName.getText().toString(), Integer.parseInt(noOfAlbums.getText().toString()), Double.parseDouble(price.getText().toString()));

                if (added >= 0) {
                    Toast.makeText(MainActivity.this, "Records were added successfully", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    firstName.setText("");
                    lastName.setText("");
                    albumName.setText("");
                    noOfAlbums.setText("");
                    price.setText("");
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long updated = helper.updateArtist(id.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), albumName.getText().toString(), Integer.parseInt(noOfAlbums.getText().toString()), Double.parseDouble(price.getText().toString()));

                if (updated >= 0) {
                    Toast.makeText(MainActivity.this, "Records were updated successfully", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    firstName.setText("");
                    lastName.setText("");
                    albumName.setText("");
                    noOfAlbums.setText("");
                    price.setText("");
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long deleted = helper.removeArtist(id.getText().toString());

                if (deleted >= 0) {
                    Toast.makeText(MainActivity.this, "Record was removed successfully", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    firstName.setText("");
                    lastName.setText("");
                    albumName.setText("");
                    noOfAlbums.setText("");
                    price.setText("");
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = helper.viewAllArtists();

                if (cursor.getCount() == 0) {
                    viewRecords("No Data", "No records found");
                    return;
                }

                if (id.getText().toString().equals("")) {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext()) {
                        buffer.append("Artist ID: " + cursor.getInt(0) + "\n");
                        buffer.append("Artist First Name: " + cursor.getString(1) + "\n");
                        buffer.append("Artist Last Name: " + cursor.getString(2) + "\n");
                        buffer.append("Album Name: " + cursor.getString(3) + "\n");
                        buffer.append("Number of Albums: " + cursor.getInt(4) + "\n");
                        buffer.append("Price: " + cursor.getDouble(4) + "\n\n");
                    }

                    viewRecords("Retrieve data", buffer.toString());
                } else {
                    cursor = helper.viewSingleArtist(id.getText().toString());
                    while (cursor.moveToNext()) {
                        Toast.makeText(MainActivity.this, "Record found", Toast.LENGTH_SHORT).show();

                        firstName.setText(String.valueOf(cursor.getString(1)));
                        lastName.setText(String.valueOf(cursor.getString(2)));
                        albumName.setText(String.valueOf(cursor.getString(3)));
                        noOfAlbums.setText(String.valueOf(cursor.getString(4)));
                        price.setText(String.valueOf(cursor.getDouble(5)));
                    }
                }
            }
        });
    }

    private void viewRecords(String title, String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).create().show();
    }
}

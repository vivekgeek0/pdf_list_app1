package com.example.pdf_list;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listView;
    private final ArrayList<String> pdfList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        retrievePdfFiles();
    }

    private void retrievePdfFiles() {
        // Get URI for all PDF files
        Uri uri = MediaStore.Files.getContentUri("external");

        // Set up the projection (columns to retrieve)
        String[] projection = {MediaStore.Files.FileColumns.DATA};

        // Filter only PDF files
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";
        String[] selectionArgs = new String[]{"application/pdf"};

        // Perform the query
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);

        // Check if cursor is not null and has data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                //pdfList.add(filePath);

                //

                // Extract the file name from the file path
                File file = new File(filePath);
                String fileName = file.getName();

                // Add the file name to the list
                pdfList.add(fileName);

                //
            } while (cursor.moveToNext());
            cursor.close();

            // Display the PDF file names in ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pdfList);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No PDF files found", Toast.LENGTH_SHORT).show();
        }
    }
}

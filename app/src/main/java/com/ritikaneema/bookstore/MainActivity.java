package com.ritikaneema.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.ritikaneema.bookstore.BookContract.BookEntry;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    TextView mTextView;

    private BookDbHelper mDbHelper;


    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        mTextView = findViewById(R.id.text_view);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BookEditorActivity.class);
                startActivity(i);
            }
        });

        mDbHelper = new BookDbHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_dummy_data:

                insertData();
                displayDbInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDbInfo();
    }


    private void insertData() {
        //SQLiteDatabase Instance
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_PRODUCT_NAME, getString(R.string.dummy_product_name));
        values.put(BookEntry.COLUMN_BOOK_PRICE, getString(R.string.dummy_price));
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, getString(R.string.dummy_quantity));
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, getString(R.string.dummy_supplier_name));
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NO, getString(R.string.dummy_supplier_phone));


        db.insert(BookEntry.TABLE_NAME, null, values);
        Log.i(LOG_TAG, getString(R.string.insertData));
    }


    private void displayDbInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        String[] projection = {BookEntry._ID,
                BookEntry.COLUMN_BOOK_PRODUCT_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER_NAME,
                BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NO};

        Cursor cursor = db.query(BookEntry.TABLE_NAME, projection, null,
                null, null, null, null);
        Log.i(LOG_TAG, getString(R.string.displayDatabaseInfo));
        try {

            mTextView.setText("No. of Book Entries are : " + cursor.getCount()
                    + " " + getString(R.string.books) + "\n \n \n");


            mTextView.append(BookEntry._ID + " | " + BookEntry.COLUMN_BOOK_PRODUCT_NAME + " | "
                    + BookEntry.COLUMN_BOOK_PRICE + " | " + BookEntry.COLUMN_BOOK_QUANTITY + " | "
                    + BookEntry.COLUMN_BOOK_SUPPLIER_NAME + " | " + BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NO
                    + "\n \n");


            int idIndex = cursor.getColumnIndex(BookEntry._ID);
            int productIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRODUCT_NAME);
            int priceIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_NAME);
            int supplierPhoneIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NO);

            while (cursor.moveToNext()) {
                int idInt = cursor.getInt(idIndex);
                String productName = cursor.getString(productIndex);
                int priceInt = cursor.getInt(priceIndex);
                int quantityInt = cursor.getInt(quantityIndex);
                String supplierName = cursor.getString(supplierIndex);
                String supplierPhone = cursor.getString(supplierPhoneIndex);

                //Populating the TextView with Fetched Data
                mTextView.append(idInt + " | " + productName + " | "
                        + priceInt + " | " + quantityInt + " | "
                        + supplierName + " | " + supplierPhone + "\n");
                Log.i(LOG_TAG, getString(R.string.data_updated));
            }

        } finally {
            Log.i(LOG_TAG, getString(R.string.cursor_closed));
            //Closing the cursor Object
            cursor.close();
        }
    }
}

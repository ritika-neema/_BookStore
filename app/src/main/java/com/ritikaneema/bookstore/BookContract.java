package com.ritikaneema.bookstore;

import android.provider.BaseColumns;

public class BookContract {
    private BookContract() {
    }

    public static final class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_PRODUCT_NAME = "product_name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_BOOK_SUPPLIER_PHONE_NO = "supplier_phone";
    }
}

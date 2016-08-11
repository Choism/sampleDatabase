package com.example.tacademy.sampledatabase;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2016-08-11.
 */
//AddressContract 이란 클래스를 생성해 줌으로써  나중 onCreate() 에서 테이블을 생성해 줄 때 번거롭게 일일이 쳐줄 필요 없이 쉽게 사용 가능하다 .

public class AddressContract {
    public interface Address extends BaseColumns {
        public static final String TABLE = "adrresstbl";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_ADDRESS = "address";

    }
}

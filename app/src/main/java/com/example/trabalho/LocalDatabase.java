package com.example.trabalho;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Empresa.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public static LocalDatabase getDatabase(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "Calculadora de Recisões").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract EmpresaDao empresaModel();
}

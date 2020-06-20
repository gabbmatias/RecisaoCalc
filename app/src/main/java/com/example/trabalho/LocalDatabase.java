package com.example.trabalho;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Empresa.class, Funcionario.class, Usuario.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public static LocalDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "Calculadora de Recisões").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract FuncionarioDao funcionarioModel();

    public abstract EmpresaDao empresaModel();

    public abstract UsuarioDao usuarioModel();
}

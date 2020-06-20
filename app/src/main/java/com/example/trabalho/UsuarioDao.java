package com.example.trabalho;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario WHERE usuarioId = :id LIMIT 1")
    Usuario get(int id);

    @Query("SELECT * FROM Usuario Where usuario = :usuario LIMIT 1")
    Usuario get(String usuario);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    //@Query("SELECT senha FROM Usuario WHERE usuario = :usuario")
    //void getSenha(String usuario);

    @Insert
    void insertAll(Usuario... usuario);

    @Update
    void update(Usuario usuarios);

    @Delete
    void delete(Usuario usuarios);

}

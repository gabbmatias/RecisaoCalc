package com.example.trabalho;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmpresaDao {

    @Query("SELECT * FROM Empresa WHERE empresaID = :id LIMIT 1")
    Empresa get(int id);

    @Query("SELECT * FROM Empresa")
    List<Empresa> getAll();

    @Insert
    void insertAll(Empresa... empresa);

    @Update
    void update(Empresa empresas);

    @Delete
    void delete(Empresa empresas);
}

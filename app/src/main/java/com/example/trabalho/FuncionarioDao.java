package com.example.trabalho;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FuncionarioDao {

    @Query("SELECT * FROM Funcionario WHERE funcionarioID = :id LIMIT 1")
    Funcionario get(int id);

    @Query("SELECT * FROM Funcionario")
    List<Funcionario> getAll();

    @Query("SELECT * FROM Funcionario WHERE funcionarioID IN (:funcionarioID)")
    List<Funcionario> loadAllByIds(int[] funcionarioID);

    @Query("SELECT * FROM Funcionario WHERE nome LIKE :name LIMIT 1")
    Funcionario findByName(String name);

    @Query("UPDATE Funcionario SET nome =:model WHERE funcionarioID == :funcionarioID")
    void updateName(String model, int funcionarioID);

    @Insert
    void insertAll(Funcionario... funcionario);

    @Update
    void update(Funcionario funcionario);

    @Delete
    void delete(Funcionario funcionario);
}

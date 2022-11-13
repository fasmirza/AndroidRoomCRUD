package com.temotion.mirzas.roomcrud.Models

import androidx.room.*

@Dao
interface SdudentDao {

    @Query("Select * From student_table")
    fun getAll(): List<Students>

    @Query("Select * From student_table where roll_no LIKE :roll LIMIT 1")
    suspend fun findByroll(roll : Int): List<Students>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(students: Students)

    @Delete
    suspend fun delete(students: Students)

    @Query( "DELETE FROM student_table")
    suspend fun deleteAll()

}
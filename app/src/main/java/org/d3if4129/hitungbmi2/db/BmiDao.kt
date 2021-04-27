package org.d3if4129.hitungbmi2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BmiDao {
    @Insert
    fun insert(bmi: BmiEntity)

//    @Query("SELECT * FROM bmi ORDER BY id DESC LIMIT 1")
//    fun getLastBmi(): LiveData<BmiEntity?>

    @Query("SELECT * FROM bmi ORDER BY id DESC")
    fun getLastBmi(): LiveData<List<BmiEntity>>

    @Query("DELETE FROM bmi")
    fun clearData()
}
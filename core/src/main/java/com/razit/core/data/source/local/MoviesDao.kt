package com.razit.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies:List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(detailFilmEntity: FavoriteFilmEntity)

    @Delete
    fun deleteFavorite(filmEntity: FavoriteFilmEntity)

    @Query("SELECT * FROM TBL_MOVIES_FAVORITE  WHERE id = :id AND type = :type")
    fun getDetail(id:Int,type: String) : LiveData<FavoriteFilmEntity>

    @Query("SELECT EXISTS(SELECT * FROM TBL_MOVIES_FAVORITE WHERE id = :id  AND type= :type)")
    fun checkFilmExist(id : Int,type: String) : Boolean

    @Query("SELECT * FROM TBL_MOVIES  WHERE type = :type")
    fun getAllFilmByType(type: String) :Flow<List<FilmEntity>>

    @Query("SELECT * FROM tbl_movies_favorite  WHERE type = :type")
    fun getFavorite(type: String) : Flow<List<FavoriteFilmEntity>>


}
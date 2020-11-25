package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    @Delete
    void delete(MainData mainData);

    @Delete
    void reset(List<MainData> mainData);

    @Query("UPDATE table_name SET currentStream = :sNum+currentStream WHERE ID = :sID")
    void updateStream(int sID, int sNum);

    @Query("UPDATE table_name SET currentVoting = :vNum+currentVoting WHERE ID = :sID")
    void updateVote(int sID, int vNum);

    @Query("UPDATE table_name SET currentMention = :mNum+currentMention WHERE ID = :sID")
    void updateMention(int sID, int mNum);

    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}

package RecipeManagerEntities;

import DatabaseAPI.LevelsOfDifficultyDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class LevelOfDifficulty {
    private int id;
    public String name;

    public LevelOfDifficulty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "LevelOfDifficulty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private static ResultSet getAllLevelsOfDifficultyResultSet(){
        LevelsOfDifficultyDB levels_of_difficulty_db = new LevelsOfDifficultyDB();
        return levels_of_difficulty_db.readAll();
    }

    public static List<LevelOfDifficulty> getAllLevelsOfDifficulty() throws SQLException {
        List<LevelOfDifficulty> list = new LinkedList<>();
        ResultSet rs = LevelOfDifficulty.getAllLevelsOfDifficultyResultSet();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            list.add(new LevelOfDifficulty(id, name));
        }
        return list;
    }
}

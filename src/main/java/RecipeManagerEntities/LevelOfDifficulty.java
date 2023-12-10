package RecipeManagerEntities;

import DatabaseAPI.LevelsOfDifficultyDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class LevelOfDifficulty {
    private final int id;
    private final String name;

    private LevelOfDifficulty(int id, String name) {
        this.id = id;
        this.name = name;
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

    private static ResultSet getLevelOfDifficultyByIdResultSet(int id){
        LevelsOfDifficultyDB levels_of_difficulty_db = new LevelsOfDifficultyDB();
        return levels_of_difficulty_db.read(id);
    }

    private static List<LevelOfDifficulty> getLevelsOfDifficultyByResultSet(ResultSet rs) {
        List<LevelOfDifficulty> list = new LinkedList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                list.add(new LevelOfDifficulty(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<LevelOfDifficulty> getAllLevelsOfDifficulty() {
        return getLevelsOfDifficultyByResultSet(getAllLevelsOfDifficultyResultSet());
    }

    public static LevelOfDifficulty getLevelOfDifficultyById(int id) {
        List<LevelOfDifficulty> l1 = getLevelsOfDifficultyByResultSet(getLevelOfDifficultyByIdResultSet(id));
        if (l1.isEmpty()) return null;
        return l1.getFirst();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}

package RecipeManagerEntities;

import DatabaseAPI.LevelsOfDifficultyDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class LevelOfDifficulty {
    private final int id;
    private final String name;

    public LevelOfDifficulty(int id, String name) {
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

    private static List<LevelOfDifficulty> getLevelsOfDifficultyByResultSet(ResultSet rs) throws SQLException {
        List<LevelOfDifficulty> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            list.add(new LevelOfDifficulty(id, name));
        }
        return list;
    }

    public static List<LevelOfDifficulty> getAllLevelsOfDifficulty() throws SQLException {
        return getLevelsOfDifficultyByResultSet(getAllLevelsOfDifficultyResultSet());
    }

    public static LevelOfDifficulty getLevelOfDifficultyById(int id) throws SQLException {
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

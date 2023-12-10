package DatabaseAPI;

import java.sql.*;


abstract class RecipeDBAbstract extends DB_BaseAbstract {
    public RecipeDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract boolean create(String name, int category, String img, String cooking_time, int difficulty_level);

    public abstract boolean update(int id, String name, int category, String img, String cooking_time, int difficulty_level);

    public abstract ResultSet readByName(String name);

    public abstract ResultSet readByCategory(int category_id);

    public abstract ResultSet readByLevelOfDifficulty(int level_of_difficulty);
}

public class RecipeDB extends RecipeDBAbstract {
    public RecipeDB() {
        super("Recipe");
    }

    public void createTableIfNotExists() {
        try {
            Connection connection = this.getConnection();
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "name TEXT UNIQUE," +
                    "category INTEGER REFERENCES Categories (id) ON DELETE CASCADE," +
                    "img TEXT," +
                    "cooking_time TEXT," +
                    "difficulty_level INTEGER REFERENCES LevelsOfDifficulty (id) ON DELETE CASCADE" +
                    ");", this.tableName);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createTableQuery);

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    public void initTable() {
        CategoriesDB category_db =  new CategoriesDB();
        category_db.initTable();

        LevelsOfDifficultyDB difficulty_level_db =  new LevelsOfDifficultyDB();
        difficulty_level_db.initTable();

        this.createTableIfNotExists();
    }

    @Override
    public boolean create(String name, int category, String img, String cooking_time, int difficulty_level) {
        try {
            Connection connection = this.getConnection();
            String insertQuery = String.format(
                    "INSERT INTO %s (name, category, img, cooking_time, difficulty_level) VALUES (?, ?, ?, ?, ?);",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.setInt(2, category);
            pstmt.setString(3, img);
            pstmt.setString(4, cooking_time);
            pstmt.setInt(5, difficulty_level);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
            return false;
        }

    }

    public boolean update(int id, String name, int category, String img, String cooking_time, int difficulty_level) {
        try {
            Connection connection = this.getConnection();
            String updateQuery = String.format(
        "UPDATE %s SET name = ?, category = ?, img = ?, cooking_time = ?, difficulty_level = ? WHERE id = ?;",
        this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, name);
            pstmt.setInt(2, category);
            pstmt.setString(3, img);
            pstmt.setString(4, cooking_time);
            pstmt.setInt(5, difficulty_level);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResultSet readByName(String name) {
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s WHERE name = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setString(1, name);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet readByCategory(int category_id) {
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s WHERE category = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, category_id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet readByLevelOfDifficulty(int level_of_difficulty) {
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s WHERE difficulty_level = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, level_of_difficulty);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }
}

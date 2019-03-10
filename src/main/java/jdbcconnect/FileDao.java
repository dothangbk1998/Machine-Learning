package jdbcconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;

public class FileDao {
	private Connection connection;

	public FileDao() {
		connection = DbUtils.getConnection();
	}

	public void addToDB(String fileName, String newsgroup, SortedMap<String, Double> vector) {
		try {
			Statement stmt = null;
			stmt = connection.createStatement();
			String createTableSql = String.format("CREATE TABLE %s "
										+ "(id INT NOT NULL AUTO_INCREMENT, "
										+ "word VARCHAR(255) NOT NULL, "
										+ "tfidf DOUBLE NOT NULL, "
										+ "PRIMARY KEY (id))", fileName);
			stmt.executeUpdate(createTableSql);
			
			StringBuilder insertSql = new StringBuilder("INSERT INTO " + fileName + " VALUES ");
			for (String key : vector.keySet()) {
				insertSql.append(String.format("(\"%s\", %.1f),", key, vector.get(key)));
			}
			insertSql.deleteCharAt(-1);
			stmt.executeUpdate(insertSql.toString());
			
			
			String insertnewsGroup = String.format("INSERT INTO classify VALUES (\"%s\", \"%s\")", fileName, newsgroup);
			stmt.executeUpdate(insertnewsGroup);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

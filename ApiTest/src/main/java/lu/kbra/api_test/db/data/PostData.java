package lu.kbra.api_test.db.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.SQLBuilder;
import lu.pcy113.pclib.db.annotations.GeneratedKeyUpdate;
import lu.pcy113.pclib.db.annotations.Reload;
import lu.pcy113.pclib.db.impl.SQLEntry;
import lu.pcy113.pclib.db.impl.SQLEntry.SafeSQLEntry;
import lu.pcy113.pclib.db.impl.SQLQuery;
import lu.pcy113.pclib.db.impl.SQLQuery.SafeSQLQuery;

public class PostData implements SafeSQLEntry {

	private int id, author;
	private String title, content;
	private Timestamp uploadDate;

	public PostData() {
	}

	public PostData(int id, int author, String title, String content, Timestamp uploadDate) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.content = content;
		this.uploadDate = uploadDate;

		if (title.length() > 128) {
			throw new IllegalArgumentException("Title is too long.");
		}
	}

	public PostData(int author, String title, String content) {
		this(author, author, title, content, new Timestamp(System.currentTimeMillis()));
	}

	public PostData(UserData author, String title, String content) {
		this(author.getId(), title, content);
	}

	@GeneratedKeyUpdate
	public void updateGeneratedKeys(ResultSet rs) throws SQLException {
		this.id = rs.getInt(1);
	}

	@Reload
	public void reload(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.author = rs.getInt("author");
		this.title = rs.getString("title");
		this.content = rs.getString("content");
		this.uploadDate = rs.getTimestamp("upload_date");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public int getAuthor() {
		return author;
	}

	public Timestamp getUploadDate() {
		return uploadDate;
	}

	@Override
	public <T extends SQLEntry> String getPreparedInsertSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeInsert(table, new String[] { "author", "title", "content" });
	}

	@Override
	public <T extends SQLEntry> String getPreparedUpdateSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeUpdate(table, new String[] { "title", "content" });
	}

	@Override
	public <T extends SQLEntry> String getPreparedDeleteSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeDelete(table, new String[] { "id" });
	}

	@Override
	public <T extends SQLEntry> String getPreparedSelectSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeSelect(table, new String[] { "id" });
	}

	@Override
	public void prepareInsertSQL(PreparedStatement stmt) throws SQLException {
		stmt.setInt(1, author);
		stmt.setString(2, title);
		stmt.setString(3, content);
	}

	@Override
	public void prepareUpdateSQL(PreparedStatement stmt) throws SQLException {
		stmt.setString(1, title);
		stmt.setString(2, content);
	}

	@Override
	public void prepareDeleteSQL(PreparedStatement stmt) throws SQLException {
		stmt.setInt(1, id);
	}

	@Override
	public void prepareSelectSQL(PreparedStatement stmt) throws SQLException {
		stmt.setInt(1, id);
	}

	@Override
	public PostData clone() {
		return new PostData();
	}

	public static SafeSQLQuery<PostData> byAuthor(int author) {
		return new SafeSQLQuery<PostData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<PostData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "author" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, author);
			}

			@Override
			public PostData clone() {
				return new PostData();
			}

		};
	}

	public static SQLQuery<PostData> byId(int id) {
		return new SafeSQLQuery<PostData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<PostData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "id" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}

			@Override
			public PostData clone() {
				return new PostData();
			}

		};
	}

}

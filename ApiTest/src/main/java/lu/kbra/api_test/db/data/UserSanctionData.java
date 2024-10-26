package lu.kbra.api_test.db.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.SQLBuilder;
import lu.pcy113.pclib.db.annotations.GeneratedKey;
import lu.pcy113.pclib.db.annotations.GeneratedKeyUpdate;
import lu.pcy113.pclib.db.annotations.Reload;
import lu.pcy113.pclib.db.impl.SQLEntry;
import lu.pcy113.pclib.db.impl.SQLEntry.SafeSQLEntry;
import lu.pcy113.pclib.db.impl.SQLQuery;

import lu.kbra.api_test.db.tables.UserSanctionReasonTable;
import lu.kbra.api_test.db.tables.UserTable;

@GeneratedKey("id")
public class UserSanctionData implements SafeSQLEntry {

	private int id, userId, reasonId, authorId;
	private String description;
	private Timestamp issueDate;

	private UserSanctionReasonData reasonData;

	public UserSanctionData() {
	}

	public UserSanctionData(int user_id, int reason_id, String description, int author_id) {
		this.userId = user_id;
		this.reasonId = reason_id;
		this.description = description;
		this.authorId = author_id;
	}

	public UserSanctionData(int user_id, UserSanctionReasonData reason, String description, int author_id) {
		this.userId = user_id;
		this.reasonId = reason.getId();
		this.description = description;
		this.authorId = author_id;
	}

	public UserSanctionData(UserData user, UserSanctionReasonData reason, UserData author, String description) {
		this.userId = user.getId();
		this.reasonId = reason.getId();
		this.description = description;
		this.authorId = author.getId();
	}
	
	@GeneratedKeyUpdate
	public void updateKeys(ResultSet rs) throws SQLException {
		id = rs.getInt(1);
	}
	
	@Reload
	public void reload(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
		userId = rs.getInt("user_id");
		reasonId = rs.getInt("reason_id");
		authorId = rs.getInt("author_id");
		description = rs.getString("description");
		issueDate = rs.getTimestamp("issue_date");
	}

	public UserSanctionReasonData loadReasonData() {
		return reasonData = UserSanctionReasonTable.byId(reasonId);
	}

	public UserSanctionReasonData getReasonData() {
		return reasonData;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public int getReasonId() {
		return reasonId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public String getDescription() {
		return description;
	}

	public Timestamp getIssueDate() {
		return issueDate;
	}

	@Override
	public <T extends SQLEntry> String getPreparedInsertSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeInsert(table, new String[] { "user_id", "reason_id", "description", "author_id" });
	}

	@Override
	public <T extends SQLEntry> String getPreparedUpdateSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeUpdate(table, new String[] { "user_id", "reason_id", "description" });
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
		stmt.setInt(1, userId);
		stmt.setInt(2, reasonId);
		stmt.setString(3, description);
		stmt.setInt(4, authorId);
	}

	@Override
	public void prepareUpdateSQL(PreparedStatement stmt) throws SQLException {
		stmt.setInt(1, userId);
		stmt.setInt(2, reasonId);
		stmt.setString(3, description);
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
	public UserSanctionData clone() {
		return new UserSanctionData();
	}

	@Override
	public String toString() {
		return "UserSanctionData {id=" + id + ", userId=" + userId + ", reasonId=" + reasonId + ", authorId=" + authorId + ", description=" + description + ", issueDate=" + issueDate + ", reasonData=" + reasonData
				+ "}";
	}

	public static SQLQuery<UserSanctionData> byId(int id) {
		return new SQLQuery.SafeSQLQuery<UserSanctionData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserSanctionData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "id" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}

			@Override
			public UserSanctionData clone() {
				return new UserSanctionData();
			}

		};
	}

	public static SQLQuery<UserSanctionData> byUserId(int userId) {
		return new SQLQuery.SafeSQLQuery<UserSanctionData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserSanctionData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "user_id" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
			}

			@Override
			public UserSanctionData clone() {
				return new UserSanctionData();
			}

		};
	}
	
	public static SQLQuery<UserSanctionData> byAuthorId(int authorId) {
		return new SQLQuery.SafeSQLQuery<UserSanctionData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserSanctionData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "author_id" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, authorId);
			}

			@Override
			public UserSanctionData clone() {
				return new UserSanctionData();
			}

		};
	}

	public static SQLQuery<UserSanctionData> byUserToken(String token) {
		return new SQLQuery.SafeSQLQuery<UserSanctionData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserSanctionData> table) {
				return "SELECT * FROM `" + table.getTableName() + "` WHERE `user_id`=(SELECT `id` FROM `" + UserTable.TABLE.getTableName() + "` WHERE `token`=?);";
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, token);
			}

			@Override
			public UserSanctionData clone() {
				return new UserSanctionData();
			}

		};
	}

}

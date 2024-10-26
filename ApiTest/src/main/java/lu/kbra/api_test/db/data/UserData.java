package lu.kbra.api_test.db.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lu.pcy113.pclib.PCUtils;
import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.SQLBuilder;
import lu.pcy113.pclib.db.annotations.GeneratedKey;
import lu.pcy113.pclib.db.annotations.GeneratedKeyUpdate;
import lu.pcy113.pclib.db.annotations.Reload;
import lu.pcy113.pclib.db.impl.SQLEntry;
import lu.pcy113.pclib.db.impl.SQLEntry.SafeSQLEntry;
import lu.pcy113.pclib.db.impl.SQLQuery;
import lu.pcy113.pclib.db.impl.SQLQuery.SafeSQLQuery;

import lu.kbra.api_test.db.tables.UserSanctionTable;

@GeneratedKey("id")
public class UserData implements SafeSQLEntry {

	private int id;
	private String name, pass, token;
	private Timestamp joinDate, lastLoginDate;

	private List<UserSanctionData> sanctions;

	public UserData() {
	}

	public UserData(String name, String pass, String token, Timestamp joinDate, Timestamp lastLoginDate) {
		this.name = name;
		this.pass = pass;
		this.token = token;
		this.joinDate = joinDate;
		this.lastLoginDate = lastLoginDate;
	}

	public UserData(String name, String rawPass) {
		this.name = name;
		this.pass = hashPass(rawPass);
	}

	@GeneratedKeyUpdate
	public void updateGeneratedKeys(ResultSet rs) throws SQLException {
		id = rs.getInt(1);
	}

	@Reload
	public void reload(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
		name = rs.getString("name");
		pass = rs.getString("pass");
		token = rs.getString("token");
		joinDate = rs.getTimestamp("join_date");
		lastLoginDate = rs.getTimestamp("last_login");
	}

	public UserData updateLogin() {
		lastLoginDate = new Timestamp(System.currentTimeMillis());
		return this;
	}

	public List<UserSanctionData> loadSanction() {
		return sanctions = UserSanctionTable.byUser(this);
	}

	public List<UserSanctionData> getSanctions() {
		return sanctions;
	}

	@Override
	public <T extends SQLEntry> String getPreparedInsertSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeInsert(table, new String[] { "name", "pass" });
	}

	@Override
	public <T extends SQLEntry> String getPreparedUpdateSQL(DataBaseTable<T> table) {
		return SQLBuilder.safeUpdate(table, new String[] { "name", "pass", "token", "last_login" });
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
		stmt.setString(1, name);
		stmt.setString(2, pass);
	}

	@Override
	public void prepareUpdateSQL(PreparedStatement stmt) throws SQLException {
		stmt.setString(1, name);
		stmt.setString(2, pass);
		stmt.setString(3, token);
		stmt.setTimestamp(4, lastLoginDate);
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
	public UserData clone() {
		return new UserData();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Timestamp getJoinDate() {
		return joinDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPass() {
		return pass;
	}

	@Override
	public String toString() {
		return "UserData {id=" + id + ", name=" + name + ", pass=" + pass + ", joinDate=" + joinDate + ", lastLoginDate=" + lastLoginDate + ", sanctions=" + sanctions + "}";
	}

	public static String hashPass(String rawPass) {
		return PCUtils.hashString(rawPass, "SHA-256");
	}

	public static String genNewToken(String hashedPass) {
		return UserData.hashPass(hashedPass + System.currentTimeMillis());
	}

	/**
	 * @param token The unhashed token (client side)
	 * @return
	 */
	public static SQLQuery<UserData> byRawToken(String token) {
		return byToken(hashPass(token));
	}

	/**
	 * @param token The hashed token (srv side)
	 * @return
	 */
	public static SQLQuery<UserData> byToken(String token) {
		return new SafeSQLQuery<UserData>() {
			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "token" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, token);
			}

			@Override
			public UserData clone() {
				return new UserData();
			}
		};
	}

	/**
	 * @param pass The unhashed password (client side)
	 * @return
	 */
	public static SQLQuery<UserData> loginRaw(String name, String pass) {
		return login(name, hashPass(pass));
	}

	/**
	 * @param pass The hashed password (srv side)
	 * @return
	 */
	public static SQLQuery<UserData> login(String name, String pass) {
		return new SafeSQLQuery<UserData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "name", "pass" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, name);
				stmt.setString(2, pass);
			}

			@Override
			public UserData clone() {
				return new UserData(name, pass);
			}

		};
	}

	public static SQLQuery<UserData> byId(int id) {
		return new SafeSQLQuery<UserData>() {

			@Override
			public String getPreparedQuerySQL(DataBaseTable<UserData> table) {
				return SQLBuilder.safeSelect(table, new String[] { "id" });
			}

			@Override
			public void updateQuerySQL(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}

			@Override
			public UserData clone() {
				return new UserData();
			}

		};
	}

}

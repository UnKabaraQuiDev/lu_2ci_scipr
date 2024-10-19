package lu.kbra.api_test.db.tables;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import lu.pcy113.pclib.db.DataBase;
import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.ReturnData;
import lu.pcy113.pclib.db.annotations.Column;
import lu.pcy113.pclib.db.annotations.DB_Table;
import lu.pcy113.pclib.impl.ExceptionFunction;

import lu.kbra.api_test.ApiTestApplication;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.endpoints.UserEndPoints;
import lu.kbra.api_test.utils.SpringUtils;

@DB_Table(name = "users", columns = {
		@Column(name = "id", type = "int", autoIncrement = true, primaryKey = true),
		@Column(name = "name", type = "varchar(36)", unique = true),
		@Column(name = "pass", type = "varchar(128)"),
		@Column(name = "token", type = "varchar(128)", notNull = false),
		@Column(name = "join_date", type = "timestamp", default_ = "CURRENT_TIMESTAMP"),
		@Column(name = "last_login", type = "timestamp", default_ = "CURRENT_TIMESTAMP")
})
public class UserTable extends DataBaseTable<UserData> {

	private static final ExceptionFunction<ReturnData<List<UserData>>, UserData> MULTI_MAP = SpringUtils.listMultiMap();
	
	public static UserTable TABLE;

	public UserTable(DataBase dbTest) {
		super(dbTest);
	}

	@Cacheable(value = "userDataCache", key = "#user + ':' + #pass")
	public static UserData byLogin(String user, String pass) {
		UserData ud = UserTable.TABLE.query(UserData.loginRaw(user, pass)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching login data: ", e);
			return null;
		}).run();
		return ud;
	}
	
	@Cacheable(value = "userDataCache", key = "#token")
	public static UserData byToken(String token) {
		UserData ud = UserTable.TABLE.query(UserData.byRawToken(token)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching login data: ", e);
			return null;
		}).run();
		return ud;
	}
	
	@CacheEvict(value = "users", key = "#userData.id")
	public static void updateUserData(UserData userData) {
		UserTable.TABLE.update(userData).run();
	}

	@Cacheable(value = "userDataCache", key = "#id")
	public static UserData byId(int id) {
		UserData ud = UserTable.TABLE.query(UserData.byId(id)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching user data: ", e);
			return null;
		}).run();
		return ud;
	}
	
}

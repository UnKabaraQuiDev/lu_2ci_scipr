package lu.kbra.api_test.db.tables;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lu.pcy113.pclib.db.DataBase;
import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.ReturnData;
import lu.pcy113.pclib.db.annotations.Column;
import lu.pcy113.pclib.db.annotations.Constraint;
import lu.pcy113.pclib.db.annotations.DB_Table;
import lu.pcy113.pclib.impl.ExceptionFunction;

import lu.kbra.api_test.ApiTestApplication;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.db.data.UserSanctionData;
import lu.kbra.api_test.endpoints.UserEndPoints;
import lu.kbra.api_test.utils.SpringUtils;

@DB_Table(name = "user_sanctions", columns = {
		@Column(name = "id", type = "int", autoIncrement = true, primaryKey = true),
		@Column(name = "user_id", type = "int"),
		@Column(name = "reason_id", type = "int"),
		@Column(name = "description", type = "text", notNull = false),
		@Column(name = "author_id", type = "int"),
		@Column(name = "issue_date", type = "timestamp", default_ = "CURRENT_TIMESTAMP")
}, constraints = {
		@Constraint(name = "fk_user_id", foreignKey = "user_id", referenceTable = "users", referenceColumn = "id"),
		@Constraint(name = "fk_author_id", foreignKey = "author_id", referenceTable = "users", referenceColumn = "id"),
		@Constraint(name = "fk_reason_id", foreignKey = "reason_id", referenceTable = "user_sanction_reasons", referenceColumn = "id")
})
public class UserSanctionTable extends DataBaseTable<UserSanctionData> {

	private static final ExceptionFunction<ReturnData<List<UserSanctionData>>, List<UserSanctionData>> MULTI_MAP = SpringUtils.single2SingleMultiMap();
	
	public static UserSanctionTable TABLE;

	public UserSanctionTable(DataBase dbTest) {
		super(dbTest);
	}

	public static List<UserSanctionData> byToken(String token) {
		List<UserSanctionData> listUsd = UserSanctionTable.TABLE.query(UserSanctionData.byUserToken(token)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching user sanction data: ", e);
			return null;
		}).run();
		return listUsd;
	}
	
	public static void updateUserSanctionData(UserSanctionData userData) {
		UserSanctionTable.TABLE.update(userData).run();
	}

	public static List<UserSanctionData> byUserId(int id) {
		List<UserSanctionData> listUsd = UserSanctionTable.TABLE.query(UserSanctionData.byUserId(id)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching user sanction data: ", e);
			return null;
		}).run();
		return listUsd;
	}
	
	public static List<UserSanctionData> byAuthorId(int id) {
		List<UserSanctionData> listUsd = UserSanctionTable.TABLE.query(UserSanctionData.byAuthorId(id)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching user sanction data: ", e);
			return null;
		}).run();
		return listUsd;
	}

	public static List<UserSanctionData> byUser(UserData user) {
		return byUserId(user.getId());
	}
	
	public static List<UserSanctionData> byAuthor(UserData author) {
		return byAuthorId(author.getId());
	}
	
}

package lu.kbra.api_test.db.tables;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lu.pcy113.pclib.db.DataBase;
import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.ReturnData;
import lu.pcy113.pclib.db.annotations.Column;
import lu.pcy113.pclib.db.annotations.DB_Table;
import lu.pcy113.pclib.impl.ExceptionFunction;

import lu.kbra.api_test.ApiTestApplication;
import lu.kbra.api_test.db.data.UserSanctionReasonData;
import lu.kbra.api_test.endpoints.UserEndPoints;
import lu.kbra.api_test.utils.SpringUtils;

@DB_Table(name = "user_sanction_reasons", columns = {
		@Column(name = "id", type = "int", autoIncrement = true, primaryKey = true),
		@Column(name = "name", type = "varchar(35)"),
		@Column(name = "key", type = "varchar(35)", unique = true),
		@Column(name = "description", type = "text", notNull = false)
})
public class UserSanctionReasonTable extends DataBaseTable<UserSanctionReasonData> {

	private static final ExceptionFunction<ReturnData<List<UserSanctionReasonData>>, UserSanctionReasonData> MULTI_MAP = SpringUtils.list2FirstMultiMap();
	
	public static UserSanctionReasonTable TABLE;

	public UserSanctionReasonTable(DataBase dbTest) {
		super(dbTest);
	}

	public static void updateUserSanctioReasonnData(UserSanctionReasonData userData) {
		UserSanctionReasonTable.TABLE.update(userData).run();
	}

	public static UserSanctionReasonData byId(int id) {
		UserSanctionReasonData usrd = UserSanctionReasonTable.TABLE.query(UserSanctionReasonData.byId(id)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching user sanction reason data: ", e);
			return null;
		}).run();
		return usrd;
	}
	
	public static UserSanctionReasonData byKey(String key) {
		UserSanctionReasonData usrd = UserSanctionReasonTable.TABLE.query(UserSanctionReasonData.byKey(key)).thenApply(MULTI_MAP).catch_((e) -> {
			if (ApiTestApplication.DEBUG)
				Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching user sanction reason data: ", e);
			return null;
		}).run();
		return usrd;
	}
	
}

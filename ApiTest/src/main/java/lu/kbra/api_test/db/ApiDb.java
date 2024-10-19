package lu.kbra.api_test.db;

import lu.pcy113.pclib.db.DataBase;
import lu.pcy113.pclib.db.DataBaseConnector;
import lu.pcy113.pclib.db.annotations.DB_Base;

@DB_Base(name = "api_test")
public class ApiDb extends DataBase {

	public ApiDb(DataBaseConnector dbConfig) {
		super(dbConfig);
	}

}

package lu.kbra.api_test.db;

import java.sql.SQLException;
import java.util.logging.Logger;

import lu.pcy113.pclib.db.DataBase;
import lu.pcy113.pclib.db.DataBaseConnector;
import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.annotations.DB_Base;

@DB_Base(name = "api_test")
public class ApiDb extends DataBase {

	public ApiDb(DataBaseConnector dbConfig) {
		super(dbConfig);
	}

	public void create(DataBaseTable<?> table) {
		final String name = "`" + super.getDataBaseName() + "`.`" + table.getTableName() + "`";
		final Logger logger = Logger.getLogger(ApiDb.class.getName());

		table.create().thenConsume((e) -> {
			e.ifError((d) -> logger.severe("Error creating Table: " + name));
			e.ifOk((d) -> logger.info(d.created() ? "Table: " + name + " created" : "Table: " + name + " already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();
	}

	public void createDB() throws SQLException {
		final String name = "`" + super.getDataBaseName() + "`";
		final Logger logger = Logger.getLogger(ApiDb.class.getName());

		super.create().thenConsume((e) -> {
			e.ifError((d) -> logger.severe("Error creating Base: " + name));
			e.ifOk((d) -> logger.info(d.created() ? "Base: " + name + " created" : "Base: " + name + " already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();
		super.updateDataBaseConnector();
		super.getConnector().reset();
	}

}

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
			e.ifCreated((d) -> logger.info("Table: " + name + " created"));
			e.ifError((d) -> logger.severe("Error creating Table: " + name));
			e.ifExisted((d) -> logger.info("Table: " + name + " already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();
	}

	public void createDB() throws SQLException {
		final String name = "`" + super.getDataBaseName() + "`";
		final Logger logger = Logger.getLogger(ApiDb.class.getName());

		super.create().thenConsume((e) -> {
			e.ifCreated((d) -> logger.info("Base: " + name + " created"));
			e.ifError((d) -> logger.severe("Error creating Base: " + name));
			e.ifExisted((d) -> logger.info("Base: " + name + " already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();
		super.updateDataBaseConnector();
		super.getConnector().reset();
	}

}

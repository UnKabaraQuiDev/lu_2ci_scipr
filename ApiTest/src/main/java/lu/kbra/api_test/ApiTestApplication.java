package lu.kbra.api_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lu.pcy113.pclib.PCUtils;
import lu.pcy113.pclib.config.ConfigLoader;
import lu.pcy113.pclib.db.DataBaseConnector;

import lu.kbra.api_test.db.ApiDb;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.db.tables.PostTable;
import lu.kbra.api_test.db.tables.UserTable;

@SpringBootApplication
@EnableCaching
public class ApiTestApplication {

	public static final boolean DEBUG = true;

	public ApiTestApplication() throws FileNotFoundException, IOException, SQLException {

		DataBaseConnector dbConfig = ConfigLoader.loadFromPropertiesFile(new DataBaseConnector(), new File("src/main/resources/db.properties"));
		System.out.println(dbConfig);

		ApiDb db = new ApiDb(dbConfig);
		db.create().thenConsume((e) -> {
			e.ifCreated((d) -> System.out.println("Database created"));
			e.ifError((d) -> System.out.println("Error creating database"));
			e.ifExisted((d) -> System.out.println("Database already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();
		db.updateDataBaseConnector();
		db.getConnector().reset();

		UserTable.TABLE = new UserTable(db);
		UserTable.TABLE.create().thenConsume((e) -> {
			e.ifCreated((d) -> System.out.println("UserTable created"));
			e.ifError((d) -> System.out.println("Error creating UserTable"));
			e.ifExisted((d) -> System.out.println("UserTable already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();

		final UserData admin = new UserData("admin", "admin");
		UserTable.TABLE.insertAndReload(admin)
				.thenConsume(d ->
						d.ifError(Exception::printStackTrace)
						.ifExisted((u) -> System.out.println("Admin data already exists."))
						.ifCreated((u) -> System.out.println("Admin data inserted."))
				).run();

		UserTable.TABLE.query(UserData.loginRaw("admin", "admin"))
				.thenApply(d -> d.multiMap(
					u -> {
						if(u.size() == 0)
							throw new RuntimeException("Admin data not found.");
						return u.get(0);
					},
					e ->  PCUtils.throw_(e),
					null,
					null
				))
				.thenApply((d) -> d.updateLogin())
				.thenCompose((v) -> UserTable.TABLE.update(v))
				.thenConsume((v) -> v.ifError(Exception::printStackTrace).ifOk((e) -> System.out.println("Admin data updated.")))
		.run();
		
		
		PostTable.TABLE = new PostTable(db);
		PostTable.TABLE.create().thenConsume((e) -> {
			e.ifCreated((d) -> System.out.println("PostTable created"));
			e.ifError((d) -> System.out.println("Error creating PostTable"));
			e.ifExisted((d) -> System.out.println("PostTable already exists"));

			e.ifError(d -> d.printStackTrace());
		}).run();

	}

	public static void main(String[] args) {
		SpringApplication.run(ApiTestApplication.class, args);
	}

}

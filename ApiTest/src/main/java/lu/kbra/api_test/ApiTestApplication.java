package lu.kbra.api_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lu.pcy113.pclib.config.ConfigLoader;
import lu.pcy113.pclib.db.DataBaseConnector;

import lu.kbra.api_test.db.ApiDb;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.db.data.UserSanctionData;
import lu.kbra.api_test.db.data.UserSanctionReasonData;
import lu.kbra.api_test.db.tables.PostTable;
import lu.kbra.api_test.db.tables.UserSanctionReasonTable;
import lu.kbra.api_test.db.tables.UserSanctionTable;
import lu.kbra.api_test.db.tables.UserTable;
import lu.kbra.api_test.utils.SpringUtils;

@SpringBootApplication
@EnableCaching
public class ApiTestApplication {

	public static final boolean DEBUG = true;

	public ApiTestApplication() throws FileNotFoundException, IOException, SQLException {

		final DataBaseConnector dbConfig = ConfigLoader.loadFromPropertiesFile(new DataBaseConnector(), new File("src/main/resources/db.properties"));
		System.out.println(dbConfig);

		final ApiDb db = new ApiDb(dbConfig);
		db.createDB();

		db.create(UserTable.TABLE = new UserTable(db));
		
		UserData admin = new UserData("admin", "admin");
		UserTable.TABLE.insertAndReload(admin)
				.thenConsume(d ->
						d.ifError(Exception::printStackTrace) 
						.ifExisted((u) -> System.out.println("Admin data already exists."))
						.ifCreated((u) -> System.out.println("Admin data inserted."))
						// .ifExisted((u) -> UserTable.TABLE.load(u))
				).run(); 
		
		admin = UserTable.TABLE.query(UserData.loginRaw("admin", "admin"))
				.thenApply(SpringUtils.list2FirstMultiMap())
				.thenApply((d) -> d.updateLogin())
				.thenCompose((v) -> UserTable.TABLE.update(v))
				.thenApply((v) -> v.ifError(Exception::printStackTrace).ifOk((e) -> System.out.println("Admin data updated.")))
		.run().getData();
		
		
		db.create(PostTable.TABLE = new PostTable(db));
		db.create(UserSanctionReasonTable.TABLE = new UserSanctionReasonTable(db));

		UserSanctionReasonTable.TABLE.insertAndReload(new UserSanctionReasonData("warned", "key.warn", "You got warned.")).runAsync();
		UserSanctionReasonTable.TABLE.insertAndReload(new UserSanctionReasonData("diddy", "key.diddy", "You got diddyed.")).runAsync();
		UserSanctionReasonData banned = UserSanctionReasonTable.TABLE.insertAndReload(new UserSanctionReasonData("banned", "key.banned", "You got banned."))
				.thenApply(SpringUtils.existedMultiMap(u -> UserSanctionReasonTable.byKey("key.banned")))
		.run();

		System.out.println(banned);
		
		db.create(UserSanctionTable.TABLE = new UserSanctionTable(db));
		
		UserSanctionData usd = new UserSanctionData(admin, banned, admin, "default");
		if(UserSanctionTable.byUser(admin).size() != 0) {
			usd = UserSanctionTable.byUser(admin).get(0);
		}else {
			UserSanctionTable.TABLE.insertAndReload(usd).run();
		}
		System.out.println(usd);
		
		admin.loadSanction().forEach(e -> e.loadReasonData());
		System.out.println(admin);
		
		System.out.println(UserSanctionTable.byToken(admin.getToken()));
		
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiTestApplication.class, args);
	}

}

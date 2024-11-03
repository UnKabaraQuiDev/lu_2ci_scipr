package lu.kbra.api_test;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lu.pcy113.pclib.config.ConfigLoader;
import lu.pcy113.pclib.db.DataBaseConnector;
import lu.pcy113.pclib.db.TableHelper;

import lu.kbra.api_test.db.ApiDb;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.db.data.UserSanctionData;
import lu.kbra.api_test.db.data.UserSanctionReasonData;
import lu.kbra.api_test.db.tables.PostTable;
import lu.kbra.api_test.db.tables.UserSanctionReasonTable;
import lu.kbra.api_test.db.tables.UserSanctionTable;
import lu.kbra.api_test.db.tables.UserTable;

@SpringBootApplication
@EnableCaching
public class ApiTestApplication {

	public static final boolean DEBUG = true;

	public ApiTestApplication() throws Exception {

		final DataBaseConnector dbConfig = ConfigLoader.loadFromPropertiesFile(new DataBaseConnector(), new File("src/main/resources/db.properties"));
		System.out.println(dbConfig);

		final ApiDb db = new ApiDb(dbConfig);
		db.createDB();

		db.create(UserTable.TABLE = new UserTable(db));
		
		UserData admin = new UserData("admin", "admin");
		admin = TableHelper.insertOrLoad(UserTable.TABLE, admin, () -> UserData.loginRaw("admin", "admin"))
				.runThrow(); 
		
		db.create(PostTable.TABLE = new PostTable(db));
		db.create(UserSanctionReasonTable.TABLE = new UserSanctionReasonTable(db));

		UserSanctionReasonTable.TABLE.insertAndReload(new UserSanctionReasonData("warned", "key.warn", "You got warned.")).runAsync();
		UserSanctionReasonTable.TABLE.insertAndReload(new UserSanctionReasonData("diddy", "key.diddy", "You got diddyed.")).runAsync();
		UserSanctionReasonData banned = TableHelper.insertOrLoad(UserSanctionReasonTable.TABLE, new UserSanctionReasonData("banned", "key.banned", "You got banned."), () -> UserSanctionReasonData.byKey("key.banned"))
				.runThrow();

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

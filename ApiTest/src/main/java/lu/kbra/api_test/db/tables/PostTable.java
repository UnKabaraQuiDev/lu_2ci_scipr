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
import lu.kbra.api_test.db.data.PostData;
import lu.kbra.api_test.endpoints.UserEndPoints;
import lu.kbra.api_test.utils.SpringUtils;

@DB_Table(name = "posts", columns = {
		@Column(name = "id", type = "int", autoIncrement = true, primaryKey = true),
		@Column(name = "author", type = "int"),
		@Column(name = "title", type = "varchar(128)"),
		@Column(name = "content", type = "text", notNull = false),
		@Column(name = "upload_date", type = "timestamp", default_ = "CURRENT_TIMESTAMP")
}, constraints = {
	@Constraint(name = "fk_author", foreignKey = "author", referenceTable = "users", referenceColumn = "id")
})
public class PostTable extends DataBaseTable<PostData> {

	public static final int MAX_TITLE_LENGTH = 128;

	private static final ExceptionFunction<ReturnData<List<PostData>>, PostData> MULTI_MAP = SpringUtils.list2FirstMultiMap();

	public static PostTable TABLE;

	public PostTable(DataBase dbTest) {
		super(dbTest);
	}

	public static PostData byId(int id) {
		PostData ud = PostTable.TABLE.query(PostData.byId(id))
				.thenApply(MULTI_MAP)
				.catch_((e) -> {
					if (ApiTestApplication.DEBUG)
						Logger.getLogger(UserEndPoints.class.getName()).log(Level.SEVERE, "Error while fetching post data: ", e);
				})
				.run();
		return ud;
	}

}

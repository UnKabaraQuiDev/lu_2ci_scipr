package lu.kbra.api_test.endpoints;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lu.pcy113.pclib.PCUtils;
import lu.pcy113.pclib.db.ReturnData;
import lu.pcy113.pclib.impl.ExceptionFunction;

import jakarta.servlet.http.HttpServletResponse;
import lu.kbra.api_test.db.data.PostData;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.db.tables.PostTable;
import lu.kbra.api_test.db.tables.UserTable;
import lu.kbra.api_test.utils.SpringUtils;

@RestController
@RequestMapping("/post")
public class PostEndPoints {

	private static final ExceptionFunction<ReturnData<PostData>, PostData> MULTI_MAP_SINGLE = SpringUtils.singleMultiMap();

	@PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendResponse send(@CookieValue(value = "token", required = true) String token, @RequestBody SendRequest request, HttpServletResponse response) throws Exception {
		return send(token, request.title, request.content, response);
	}

	@GetMapping(value = "/send")
	public SendResponse send(@CookieValue(value = "token", required = true) String token, @RequestParam String title, @RequestParam String content, HttpServletResponse response) throws Exception {
		UserData ud = UserTable.byToken(token);

		if (ud == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		// ud.updateLogin();
		// UserTable.updateUserData(ud); // update last login
		response.addCookie(SpringUtils.newTokenCookie(token)); // extend cookie period

		if (title.length() > PostTable.MAX_TITLE_LENGTH) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title too long (max 128).");
		}

		PostData post = new PostData(ud, title, content);

		post = PostTable.TABLE.insert(post).run().<PostData>multiMap(u -> u, e -> PCUtils.throw_(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())), u -> u, u -> u);

		if (post == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while inserting post data.");
		}

		return new SendResponse(post.getId());
	}
 
	@PostMapping(value = "/load")
	public PostLoadResponse load(@CookieValue(value = "token", required = true) String token, @RequestBody PostLoadRequest id, HttpServletResponse response) {
		return load(token, id.id, response);
	}

	@GetMapping(value = "/load")
	public PostLoadResponse load(@CookieValue(value = "token", required = true) String token, @RequestParam int id, HttpServletResponse response) {
		UserData ud = UserTable.byToken(token);

		if (ud == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		PostData pd = PostTable.byId(id);

		UserData author = UserTable.byId(pd.getAuthor());

		// response.addCookie(SpringUtils.newCookie("token", token, true, 3600)); //
		// update cookie period

		return new PostLoadResponse(pd.getAuthor(), author == null ? null : new PostLoadAuthorData(author.getName(), author.getJoinDate()), id, pd.getTitle(), pd.getContent(), pd.getUploadDate());
	}

	private static record SendRequest(String title, String content) {
	}

	private static record SendResponse(int uniquePostId) {
	}

	private static record PostLoadRequest(int id) {
	}

	private static record PostLoadResponse(int author, PostLoadAuthorData authorData, int postId, String title, String content, Timestamp uploadDate) {
	}

	private static record PostLoadAuthorData(String name, Timestamp joinDate) {
	}

}

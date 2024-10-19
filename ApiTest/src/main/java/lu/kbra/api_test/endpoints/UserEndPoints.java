package lu.kbra.api_test.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lu.kbra.api_test.db.data.UserData;
import lu.kbra.api_test.db.tables.UserTable;
import lu.kbra.api_test.utils.SpringUtils;

@RestController
@RequestMapping("/user")
public class UserEndPoints {

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
		return login(request.user, request.pass, response);
	}

	@GetMapping(value = "/login")
	public LoginResponse login(@RequestParam String user, @RequestParam String pass, HttpServletResponse response) {
		UserData ud = UserTable.byLogin(user, pass);

		if (ud == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		final String newToken = UserData.hashPass(ud.getPass() + System.currentTimeMillis());

		ud.updateLogin();
		ud.setToken(UserData.hashPass(newToken));

		// UserTable.TABLE.update(ud).runAsync();
		UserTable.updateUserData(ud);

		response.addCookie(SpringUtils.newTokenCookie(newToken));

		return new LoginResponse(newToken);
	}

	@RequestMapping(value = "/tokenValid")
	public ResponseEntity<?> tokenValid(@CookieValue(value = "token", required = true) String token, HttpServletResponse response) {
		UserData ud = UserTable.byToken(token);

		if (ud == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}

		response.addCookie(SpringUtils.newCookie("token", token, true, 3600));

		return ResponseEntity.accepted().build();
	}

	private static record LoginRequest(String user, String pass) {
	}

	private static record LoginResponse(String token) {
	}

}

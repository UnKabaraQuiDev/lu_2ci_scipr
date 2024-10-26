package lu.kbra.api_test.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.MediaType;

import lu.pcy113.pclib.PCUtils;
import lu.pcy113.pclib.db.DataBaseTable;
import lu.pcy113.pclib.db.ReturnData;
import lu.pcy113.pclib.db.impl.SQLEntry;
import lu.pcy113.pclib.impl.ExceptionFunction;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class SpringUtils {

	private static final String TOKEN_COOKIE_NAME = "token";

	public static void json(HttpServletResponse response, JSONObject put) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		writer.write("hello\n");
		writer.close();
	}

	public static Cookie newCookie(String name, String value, boolean httpOnly, int maxAgeSec) {
		Cookie cookie = new Cookie(name, value.toString());
		/*
		 * cookie.setHttpOnly(httpOnly); cookie.setSecure(false);
		 * cookie.setDomain("localhost"); cookie.setPath("/");
		 */
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeSec);

		return cookie;
	}

	public static Cookie getTokenCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	public static Cookie newTokenCookie(String newToken) {
		return newCookie(TOKEN_COOKIE_NAME, newToken, false, 3600);
	}

	public static <T> ExceptionFunction<ReturnData<List<T>>, T> list2FirstMultiMap() {
		final ExceptionFunction<List<T>, T> listHandler = u -> {
			if (u.size() == 0)
				throw new RuntimeException("Data not found.");
			return u.get(0);
		};

		return d -> d.multiMap(listHandler, e -> PCUtils.throw_(e), listHandler, listHandler);
	}

	public static <T> ExceptionFunction<ReturnData<T>, T> single2SingleMultiMap() {
		return d -> d.multiMap(u -> u, e -> PCUtils.throw_(e), u -> u, u -> u);
	}

	public static <T extends SQLEntry> ExceptionFunction<ReturnData<T>, T> singleReloadMultiMap(DataBaseTable<T> table) {
		return d -> d.multiMap(u -> u, e -> PCUtils.throw_(e), u -> u, u -> table.load(u).run().getData());
	}

	public static <T> ExceptionFunction<ReturnData<T>, T> existedMultiMap(ExceptionFunction<T, T> existed) {
		return d -> d.multiMap(u -> u, e -> PCUtils.throw_(e), u -> u, existed::apply);
	}

}

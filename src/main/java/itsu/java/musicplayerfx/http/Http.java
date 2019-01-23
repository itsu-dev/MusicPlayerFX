package itsu.java.musicplayerfx.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Http {

	public static CookieManager cookie;
	static {
		if (CookieHandler.getDefault() == null) {
			cookie = new CookieManager();
			CookieHandler.setDefault(cookie);
		} else
			cookie = (CookieManager) CookieHandler.getDefault();

	}

	private Http() {

	}

	public static String get(String url, GetHeader header)
			throws IOException, URISyntaxException {
		String result = "";
		URI uri = new URI(url);
		HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("GET");
		if (!header.Host.equals(""))
			connection.setRequestProperty("Host", header.Host);
		if (!header.Connection)
			connection.setRequestProperty("Connection", "Keep-Alive");
		if (!header.Accept.equals(""))
			connection.setRequestProperty("Accept", header.Accept);
		if (!header.UserAgent.equals(""))
			connection.setRequestProperty("User-Agent", header.UserAgent);
		if (!header.Referer.equals(""))
			connection.setRequestProperty("Referer", header.Referer);
		if (!header.AcceptEncoding.equals(""))
			connection.setRequestProperty("Accept-Encoding", header.AcceptEncoding);
		if (!header.AcceptLanguage.equals(""))
			connection.setRequestProperty("Accept-Language", header.AcceptLanguage);

		String location = connection.getHeaderField("Location");
		if (location != null) {
			GetHeader redirect_header = new GetHeader();
			if (!header.Host.equals(""))
				redirect_header.setHost(header.Host);
			if (!header.Connection)
				redirect_header.setConnection(header.Connection);
			if (!header.Accept.equals(""))
				redirect_header.setAccept(header.Accept);
			if (!header.UserAgent.equals(""))
				redirect_header.setUserAgent(header.UserAgent);
			if (!header.Referer.equals(""))
				redirect_header.setReferer(header.Referer);
			if (!header.AcceptEncoding.equals(""))
				redirect_header.setAcceptEncoding(header.AcceptEncoding);
			if (!header.AcceptLanguage.equals(""))
				redirect_header.setAcceptLanguage(header.AcceptLanguage);
			result = get((location.indexOf("http") != 0 ? url.substring(0, url.lastIndexOf("/")) : "") + location,
					redirect_header);
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[8192];
			int r = 0;
			while (true) {
				r = reader.read(buffer);
				if (r <= 0)
					break;
				sb.append(buffer, 0, r);
			}
			result = sb.toString();
		}

		return result;
	}

	public static void resetCookie() {
		cookie = new CookieManager();
		CookieHandler.setDefault(cookie);
	}
}

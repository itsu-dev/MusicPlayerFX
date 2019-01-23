package itsu.java.musicplayerfx.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class iTunesAPI {
	
	private static Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public static String getArtwork(String album, String artist) {
        try {
            System.out.println(album + " " + artist);
            String keyword = URLEncoder.encode(album + " " + artist, "UTF-8");

            String request = "https://itunes.apple.com/search?term=" + keyword + "&media=music&entity=album&country=jp&lang=ja_jp&limit=1";

            GetHeader header = new GetHeader()
                    .setConnection(true)
                    .setAccept("application/json, text/javascript, */*; q=0.01")
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.90 Safari/537.36 Vivaldi/1.91.867.38");

            String got = Http.get(request, header);

            Map<String, Object> raw = new Gson().fromJson(got, new TypeToken<Map>(){}.getType());
            Map<String, Object> data = ((List<Map<String, Object>>) raw.get("results")).get(0);

            String result = null;
            if (data.size() != 0) {
                result = String.valueOf(data.get("artworkUrl100"));
                result = result.replaceAll("100x100", "600x600");
            }

            System.out.println(result);

            return result;
        } catch (IOException | URISyntaxException | NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
	}

}

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
            String keyword = URLEncoder.encode(album + " " + artist, "UTF-8");

            String request = "https://itunes.apple.com/search?term=" + keyword + "&media=music&entity=album&country=jp&lang=ja_jp&limit=5";

            GetHeader header = new GetHeader()
                    .setConnection(true)
                    .setAccept("application/json, text/javascript, */*; q=0.01")
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.90 Safari/537.36 Vivaldi/1.91.867.38");

            String got = Http.get(request, header);

            Map<String, Object> raw = new Gson().fromJson(got, new TypeToken<Map>(){}.getType());
            List<Map<String, Object>> temp = ((List<Map<String, Object>>) raw.get("results"));

            String result = null;

            for (Map<String, Object> stringObjectMap : temp) {
                if (String.valueOf(stringObjectMap.get("collectionName")).contains(album)) {
                    result = String.valueOf(stringObjectMap.get("artworkUrl100"));
                    break;
                }
            }

            if (result == null && temp.size() > 0) result = String.valueOf(temp.get(0).get("artworkUrl100"));
            else if (temp.size() < 0) return null;

            result = result.replaceAll("100x100", "600x600");

            return result;
        } catch (IOException | URISyntaxException | NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
	}

}

package tr.org.iys.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HeaderUtil {
	
	public HttpHeaders getDefaultHeader(String token) {
		HttpHeaders header = new HttpHeaders();
		List<MediaType> mediaType = new ArrayList<>();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setContentLanguage(Locale.UK);
		mediaType.add(MediaType.APPLICATION_JSON);
		header.setAccept(mediaType);
		if (token !=null)
			header.setBearerAuth(token);
		return header;
	}
}

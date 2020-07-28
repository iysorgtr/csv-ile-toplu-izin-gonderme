package tr.org.iys.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import tr.org.iys.config.Constants;
import tr.org.iys.config.GlobalMembers;

@Component
public class ConfigUtil {
	public void configureService(String[] args) {
		List<String> argumentList = Arrays.asList(args);
		if (!argumentList.isEmpty()) {
			for (String arg : argumentList) {
				if (arg.equals(Constants.Api.RESUME_PROPERTY))
					GlobalMembers.setResume(true);
			}
		}
	}
}

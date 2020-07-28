package tr.org.iys.util;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.org.iys.config.Constants;
import tr.org.iys.config.GlobalMembers;
import tr.org.iys.model.CSVFileContent;
import tr.org.iys.model.ConsentRequest;
import tr.org.iys.model.TokenRequest;

@Component
public class BusinessLogicUtil {
	
	@Autowired
	private RestUtil restService;
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public String getToken() {
		String userName = GlobalMembers.getUserName();
		String password = GlobalMembers.getPassword();
		String grandType = Constants.Api.GRAND_TYPE;
		TokenRequest tokenRequest = new TokenRequest(grandType, userName, password);
		
		return restService.getToken(tokenRequest);
	}
	
	public void executeBatchOperation(String token) {
		executeSyncBatch(token);
	}

	private void executeSyncBatch(String token) {
		List<CSVFileContent> records = GlobalMembers.getCsvFile();
		int batchCount = Integer.parseInt(GlobalMembers.getBatchCount());
		List<ConsentRequest> batchList = new ArrayList<>();
		int counter = 0;
		for (CSVFileContent content : records) {
			ConsentRequest request = new ConsentRequest();
			request.setConsentDate(content.getConsentDate());
			request.setRecipient(content.getRecipient());
			request.setRecipientType(content.getRecipientType());
			request.setSource(content.getSource());
			request.setType(content.getType());
			request.setStatus(content.getStatus());
			batchList.add(request);
			counter++;
			if (counter % batchCount == 0) {
				sendBatchRequest(token, batchList);
				batchList.clear();
			}
		}
		
		if (!batchList.isEmpty()) {
			sendBatchRequest(token, batchList);
		}
	}

	private void sendBatchRequest(String token, List<ConsentRequest> batchList) {
		String iysCode = GlobalMembers.getIysCode();
		String brandCode = GlobalMembers.getBrandCode();
		int dataSize = batchList.size();
		LOG.debug("{} Kayit icin izinler ekleniyor...", dataSize );
		restService.addConsentBatch(token, batchList, iysCode, brandCode);
		LOG.debug("{} Kayit icin izin ekleme tamamlandi...", dataSize);
	}
	
}
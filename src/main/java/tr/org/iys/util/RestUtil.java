package tr.org.iys.util;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.org.iys.config.Constants;
import tr.org.iys.config.GlobalMembers;
import tr.org.iys.exception.ApiException;
import tr.org.iys.model.BatchErrorResponse;
import tr.org.iys.model.BatchSuccessResponse;
import tr.org.iys.model.ConsentRequest;
import tr.org.iys.model.TokenRequest;

@Component
public class RestUtil {
	
	@Autowired
	@Qualifier("customRestTemplate")
	private RestTemplate template;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private HeaderUtil headerUtil;
	@Autowired
	private CSVWriterUtil writerUtil;
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public String getToken(TokenRequest tokenRequest) {
		String url = GlobalMembers.getTokenService();
		HttpHeaders header = headerUtil.getDefaultHeader(null);
		ResponseEntity<String> response = null;
		HttpEntity<TokenRequest> entityRequest = new HttpEntity<>(tokenRequest, header);
		try {
			LOG.debug(Constants.Message.TOKEN_IS_BEING_READING);
			response = template.exchange(url, HttpMethod.POST, entityRequest, String.class);
		} catch (HttpClientErrorException e) {
			throw new ApiException(Constants.Message.TOKEN_FAILED, Constants.ErrorCode.TOKEN_FAILED_CODE);
		} catch (HttpServerErrorException e) {
			throw new ApiException(Constants.ErrorMessage.TOKEN_SERVICE_FAILED,
					Constants.ErrorCode.TOKEN_SERVICE_FAILED_CODE);
		} catch (Exception e) {
			throw new ApiException(Constants.ErrorMessage.UNKNOWN_ERROR, Constants.ErrorCode.UNKNOWN_ERROR_CODE);
		}
		JSONObject jsonObject = new JSONObject(response.getBody());
		LOG.debug(Constants.Message.TOKEN_SUCCESS);
		
		return jsonObject.getString(Constants.Api.ACCESS_TOKEN);
	}
	
	public void addConsentBatch(String token, List<ConsentRequest> consentList, String iysCode, String brandCode) {
		String url = GlobalMembers.getBatchService();
		String urlIys = url.replace("#IYSCODE#", iysCode);
		String fullUrl =urlIys.replace("#BRANDCODE#", brandCode); 
		HttpHeaders header = headerUtil.getDefaultHeader(token);
		ResponseEntity<String> response = null;
		HttpEntity<List<ConsentRequest>> entityRequest = new HttpEntity<>(consentList, header);
		try {
			response = template.exchange(fullUrl, HttpMethod.POST, entityRequest, String.class);
		} catch (HttpClientErrorException e) { 
			try {
				if (e.getRawStatusCode() == 403) {
					throw new ApiException(Constants.ErrorMessage.FORBIDDEN, Constants.ErrorCode.UNKNOWN_ERROR_CODE);
				}
				BatchErrorResponse members = objectMapper.readValue(e.getResponseBodyAsString(), BatchErrorResponse.class);
				writerUtil.generateOutput4Error(members, consentList);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
				throw new ApiException(Constants.ErrorMessage.UNKNOWN_ERROR, Constants.ErrorCode.UNKNOWN_ERROR_CODE);
			}
		} catch (HttpServerErrorException e) {
			LOG.debug(Constants.ErrorMessage.API_ERROR, e.getStatusText());
		}catch (Exception e) {
			LOG.debug(Constants.ErrorMessage.UNKNOWN_ERROR);
		}
		
		if (response !=null) {
			try {
				BatchSuccessResponse members = objectMapper.readValue(response.getBody(), BatchSuccessResponse.class);
				writerUtil.generateOutput4Success(members);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new ApiException(Constants.ErrorMessage.UNKNOWN_ERROR, Constants.ErrorCode.UNKNOWN_ERROR_CODE);
			}
		}
	}

}

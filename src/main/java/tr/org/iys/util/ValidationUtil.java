package tr.org.iys.util;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.org.iys.config.ApiConfigProperties;
import tr.org.iys.config.Constants;
import tr.org.iys.config.GlobalMembers;
import tr.org.iys.config.enums.RecipientType;
import tr.org.iys.config.enums.Source;
import tr.org.iys.config.enums.Status;
import tr.org.iys.config.enums.Type;
import tr.org.iys.exception.ApiException;
import tr.org.iys.model.CSVFileContent;

@Component
public class ValidationUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static List<String> errorList = new ArrayList<>();
	@Autowired
	private ApiConfigProperties properties;
	@Autowired
	private CSVReaderUtil csvReader;

	public void validateProperties() {
		// validate and set properties. throw exception if it is empty or does not match with criteria
		validateUsernamePassword();
		validateIysAndBrandCode();
		validateEndPonts();
		validateLogLevel();
		validatebatchCount();
	}
	
	public void validateCSV() {
		List<CSVFileContent> records = csvReader.read();
		validate(records);
		GlobalMembers.setCsvFile(records);
	}

	private void validatebatchCount() {
		int batchCount = Integer.parseInt(properties.getBatchCount());
		if (batchCount > Constants.Api.BATCH_COUNT_MAX_LIMIT_SYNC || batchCount < 0 ) {
			throw new ApiException(Constants.ErrorMessage.BATCHCOUNT_VALIDATION_ERROR_MESSAGE, Constants.ErrorCode.BATCHCOUNT_VALIDATION_ERROR_CODE);
		}	
		GlobalMembers.setBatchCount(String.valueOf(batchCount));
		LOG.debug("batchCount :{}", batchCount);
		
	}

	private void validateLogLevel() {
		String logLevel = properties.getLogLevel();
		if (!("1".equals(logLevel) || "0".equals(logLevel)))
			throw new ApiException(Constants.ErrorMessage.LOG_VALIDATION_ERROR_MESSAGE, Constants.ErrorCode.LOG_VALIDATION_ERROR_CODE);
		GlobalMembers.setLogLevel(logLevel);
		LOG.debug("loglevel :{}", logLevel);
	}

	private void validateEndPonts() {
		String tokenService = properties.getTokenService();
		String batchService = properties.getBatchService();
		String queryService = properties.getQueryService();
		GlobalMembers.setTokenService(tokenService);
		GlobalMembers.setBatchService(batchService);
		LOG.debug("tokenservice :{}", tokenService);
		LOG.debug("batchservice :{}", batchService);
		LOG.debug("queryservice :{}", queryService);
	}

	private void validateIysAndBrandCode() {
		String iysCode = properties.getIysCode();
		String brandCode = properties.getBrandCode();
		if (iysCode == null || iysCode.equals("") || brandCode == null || brandCode.equals(""))
			throw new ApiException(Constants.ErrorMessage.IYS_AND_BRAND_CODE_VALIDATION_ERROR_MESSAGE, Constants.ErrorCode.IYS_AND_BRAND_CODE_VALIDATION_ERROR_CODE);
		GlobalMembers.setIysCode(iysCode);
		GlobalMembers.setBrandCode(brandCode);
		LOG.debug("iysCode :{}, brandCode :{}", iysCode, brandCode);
	}

	private void validateUsernamePassword() {
		String userName = properties.getUserName();
		String password = properties.getPassword();
		if (userName == null || userName.equals("") || password == null)
			throw new ApiException(Constants.ErrorMessage.USERNAME_PASSWORD_VALIDATION_ERROR_MESSAGE, Constants.ErrorCode.USERNAME_PASSWORD_VALIDATION_ERROR_CODE);
		GlobalMembers.setUserName(userName);
		GlobalMembers.setPassword(password);
		LOG.debug("kullanici adi :{}, sifre :{}", userName, password);
	}

	private void validate(List<CSVFileContent> records) {
		int counter = 1;
		for (CSVFileContent data : records) {
			validateReceipentType(data, counter);
			if(RecipientType.TACIR.name().equals(data.getRecipientType())) {
				valiadateType(data, counter);
				validateStatus(data, counter);
				validateRecipient(data, counter);
			} else {
				validateSource(data, counter);
				validateConsentDate(data, counter);
				valiadateType(data, counter);
				validateStatus(data, counter);
				validateRecipient(data, counter);
			}
			counter ++;
		}
		if(!errorList.isEmpty()) {
			errorList.forEach(LOG::debug);
			System.exit(1);
		}
	}

	private void validateRecipient(CSVFileContent data, int counter) {
		String recipient = data.getRecipient();
		String type = data.getType();
		if (type.equals(Type.EPOSTA.name())) {
			String regex = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(recipient);
			if (!matcher.matches())
				addError(generateError(counter, Constants.ErrorMessage.INVALID_RECIPIENT_EMAIL_MESSAGE, recipient));

		} else if (type.equals(Type.ARAMA.name()) || type.equals(Type.MESAJ.name())) {
			String regex = "([+]?\\d{1,2}?)?(\\d{11})";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(recipient);
			if (!matcher.matches())
				addError(generateError(counter, Constants.ErrorMessage.INVALID_RECIPIENT_PHONE_MESSAGE, recipient));
		}
	}

	private void validateReceipentType(CSVFileContent data, int counter) {
		String recipientType = data.getRecipientType();
		if (!(recipientType.equals(RecipientType.BIREYSEL.name()) || recipientType.equals(RecipientType.TACIR.name())))
			addError(generateError(counter, Constants.ErrorMessage.INVALID_RECIPIENT_MESSAGE, recipientType));
	}

	private void validateConsentDate(CSVFileContent data, int counter) {
		String date = data.getConsentDate();
		SimpleDateFormat d1 = new SimpleDateFormat(Constants.Api.CONSENT_DATE_FORMAT);
		Date current = null;
		Date now = new Date();
		d1.setLenient(false);
		boolean isValidFormat = true;
		try {
			current = d1.parse(date);
		} catch (ParseException e) {
			addError(generateError(counter, Constants.ErrorMessage.INVALID_CONSENT_DATE_MESSAGE, date));
			isValidFormat = false;
		}

		if (isValidFormat) {
			try {
				Date may = d1.parse(Constants.Api.MAY_1_2015);
				if (!current.after(may))
					addError(generateError(counter, Constants.ErrorMessage.INVALID_CONSENT_DATE_TIME_MESSAGE, date));
				if (current.after(now))
					addError(generateError(counter, Constants.ErrorMessage.INVALID_CONSENT_DATE_FUTURE_MESSAGE, date));
			} catch (ParseException e) {
				addError(generateError(counter, Constants.ErrorMessage.INVALID_DATE, date));
			}
		}
	}
		
	private void validateStatus(CSVFileContent data, int counter) {
		String status = data.getStatus();
		if (!(status.equals(Status.ONAY.name()) || status.equals(Status.RET.name())))
			addError(generateError(counter, Constants.ErrorMessage.INVALID_STATUS_MESSAGE, status));
	}

	private void validateSource(CSVFileContent data, int counter) {
		String source = data.getSource();
		if (!(source.equals(Source.HS_2015.name()) 
				|| source.equals(Source.HS_ATM.name()) 
				|| source.equals(Source.HS_CAGRI_MERKEZI.name())
				|| source.equals(Source.HS_EORTAM.name())
				|| source.equals(Source.HS_EPOSTA.name())
				|| source.equals(Source.HS_ETKINLIK.name())
				|| source.equals(Source.HS_FIZIKSEL_ORTAM.name())
				|| source.equals(Source.HS_ISLAK_IMZA.name())
				|| source.equals(Source.HS_MESAJ.name())
				|| source.equals(Source.HS_MOBIL.name())
				|| source.equals(Source.HS_SOSYAL_MEDYA.name())
				|| source.equals(Source.HS_WEB.name())))
			addError(generateError(counter, Constants.ErrorMessage.INVALID_SOURCE_MESSAGE, source));
	}

	private void valiadateType(CSVFileContent data, int counter) {
		String type = data.getType();
		if (!(type.equals(Type.ARAMA.name()) || type.equals(Type.EPOSTA.name()) || type.equals(Type.MESAJ.name())))
			addError(generateError(counter, Constants.ErrorMessage.INVALID_TYPE_MESSAGE, type));
	}

	private String generateError(int line, String errorMessage, String parameter) {
		return "SATIR:" + line + " " + errorMessage + " girilen deger:" + parameter;
	}

	public static synchronized List<String> getErrorlist() {
		return errorList;
	}

	public static synchronized void addError(String error) {
		errorList.add(error);
	}

}

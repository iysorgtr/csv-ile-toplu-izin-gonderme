package tr.org.iys.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:csv/config.yml", encoding = "UTF-8")
public class ApiConfigProperties {
	
	@Value("${user}") 
	private String userName;
	@Value("${password}")
	private String password;
	@Value("${iyscode}")
	private String iysCode;
	@Value("${brandcode}")
	private String brandCode;
	@Value("${batchcount:100}")
	private String batchCount;
	@Value("${tokenservice:https://api.sandbox.iys.org.tr/oauth2/token}")
	private String tokenService;
	@Value("${batchservice:https://api.sandbox.iys.org.tr/sps/#IYSCODE#/brands/#BRANDCODE#/consents/request}")
	private String batchService;
	@Value("${queryservice:https://api.sandbox.iys.org.tr/sps/#IYSCODE#/brands/#BRANDCODE#/consents/request/#REQUESTID}")
	private String queryService;
	@Value("${loglevel:0}")
	private String logLevel;
	@Value("${operationType:0}")
	private String operationType;
	@Value("${versionservice:https://api.sandbox.iys.org.tr/version}")
	private String versionService;
	
	public String getUserName() {
		return StringUtils.deleteWhitespace(userName);
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIysCode() {
		return StringUtils.deleteWhitespace(iysCode);
	}
	public void setIysCode(String iysCode) {
		this.iysCode = iysCode;
	}
	public String getBrandCode() {
		return StringUtils.deleteWhitespace(brandCode);
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBatchCount() {
		return StringUtils.deleteWhitespace(batchCount);
	}
	public void setBatchCount(String batchCount) {
		this.batchCount = batchCount;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getTokenService() {
		return tokenService;
	}
	public void setTokenService(String tokenService) {
		this.tokenService = tokenService;
	}
	public String getBatchService() {
		return batchService;
	}
	public void setBatchService(String batchService) {
		this.batchService = batchService;
	}
	public String getQueryService() {
		return queryService;
	}
	public void setQueryService(String queryService) {
		this.queryService = queryService;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getVersionService() {
		return versionService;
	}
	public void setVersionService(String versionService) {
		this.versionService = versionService;
	}
	
}

package tr.org.iys.config;

import java.util.ArrayList;
import java.util.List;

import tr.org.iys.model.CSVFileContent;

public final class GlobalMembers {
	private GlobalMembers() {
		throw new IllegalStateException(Constants.Api.DEFAULT);
	}
	private static String userName;
	private static String password;
	private static String batchCount;
	private static String iysCode;
	private static String brandCode;
	private static String async;
	private static String tokenService;
	private static String batchService;
	private static String queryService;
	private static String logLevel;
	private static List<CSVFileContent> csvFile;
	private static int index = 0;
	private static List<String[]> successOutput = new ArrayList<>();
	private static List<String[]> failOutput = new ArrayList<>();
	private static List<String[]> allOutput = new ArrayList<>();
	private static boolean resume = false;
	
	public static synchronized String getBatchCount() {
		return batchCount;
	}
	public static synchronized void setBatchCount(String batchCount) {
		GlobalMembers.batchCount = batchCount;
	}
	public static synchronized String getUserName() {
		return userName;
	}
	public static synchronized void setUserName(String userName) {
		GlobalMembers.userName = userName;
	}
	public static synchronized String getPassword() {
		return password;
	}
	public static synchronized void setPassword(String password) {
		GlobalMembers.password = password;
	}
	public static synchronized String getIysCode() {
		return iysCode;
	}
	public static synchronized void setIysCode(String iysCode) {
		GlobalMembers.iysCode = iysCode;
	}
	public static synchronized String getBrandCode() {
		return brandCode;
	}
	public static synchronized void setBrandCode(String brandCode) {
		GlobalMembers.brandCode = brandCode;
	}
	public static synchronized String getAsync() {
		return async;
	}
	public static synchronized void setAsync(String async) {
		GlobalMembers.async = async;
	}
	public static synchronized String getTokenService() {
		return tokenService;
	}
	public static synchronized void setTokenService(String tokenService) {
		GlobalMembers.tokenService = tokenService;
	}
	public static synchronized String getBatchService() {
		return batchService;
	}
	public static synchronized void setBatchService(String batchService) {
		GlobalMembers.batchService = batchService;
	}
	public static synchronized List<CSVFileContent> getCsvFile() {
		return csvFile;
	}
	public static synchronized void setCsvFile(List<CSVFileContent> csvFile) {
		GlobalMembers.csvFile = csvFile;
	}
	public static synchronized String getLogLevel() {
		return logLevel;
	}
	public static synchronized void setLogLevel(String logLevel) {
		GlobalMembers.logLevel = logLevel;
	}
	public static synchronized int getIndex() {
		return index;
	}
	public static synchronized void setIndex(int index) {
		GlobalMembers.index = index;
	}
	
	public static synchronized void increementIndex(int number) {
		GlobalMembers.index = index + number;
	}
	public static synchronized void addSuccessOutput(List<String[]> output) {
		GlobalMembers.successOutput.addAll(output);
	}
	public static synchronized void addFailOutput(List<String[]> output) {
		GlobalMembers.failOutput.addAll(output);
	}
	public static synchronized void addAllFailOutput(List<String[]> output) {
		GlobalMembers.allOutput.addAll(output);
	}
	public static synchronized List<String[]> getSuccessOutput() {
		return successOutput;
	}
	public static synchronized void setSuccessOutput(List<String[]> successOutput) {
		GlobalMembers.successOutput = successOutput;
	}
	public static synchronized List<String[]> getFailOutput() {
		return failOutput;
	}
	public static synchronized void setFailOutput(List<String[]> failOutput) {
		GlobalMembers.failOutput = failOutput;
	}
	public static synchronized List<String[]> getAllOutput() {
		return allOutput;
	}
	public static synchronized void setAllOutput(List<String[]> allOutput) {
		GlobalMembers.allOutput = allOutput;
	}
	public static synchronized String getQueryService() {
		return queryService;
	}
	public static synchronized void setQueryService(String queryService) {
		GlobalMembers.queryService = queryService;
	}
	public static synchronized boolean isResume() {
		return resume;
	}
	public static synchronized void setResume(boolean resume) {
		GlobalMembers.resume = resume;
	}
}

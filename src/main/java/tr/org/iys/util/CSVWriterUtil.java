package tr.org.iys.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

import tr.org.iys.config.Constants;
import tr.org.iys.config.GlobalMembers;
import tr.org.iys.exception.ApiException;
import tr.org.iys.model.BatchErrorResponse;
import tr.org.iys.model.BatchSuccessResponse;
import tr.org.iys.model.CSVFileContent;
import tr.org.iys.model.ConsentRequest;
import tr.org.iys.model.Err;
import tr.org.iys.model.SubRequest;

@Component
public class CSVWriterUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public void generateOutput4Error(BatchErrorResponse members, List<ConsentRequest> consentList) {
		List<String[]> output4All = new ArrayList<>();
		List<String[]> output4err = new ArrayList<>();
		int index = GlobalMembers.getIndex();
		List<Err> error = members.getErrors();
		
		for (int i=0; i < consentList.size(); i++) {
			output4All.add(new String[] {String.valueOf( index + i + 1), "Gonderilemedi"});
		}
		for (Err err : error) {
			int responseIndex = Integer.parseInt(err.getIndex());
			output4err.add(new String[] {String.valueOf(responseIndex + 1 + index), err.getValue(), err.getMessage()});
		}
		// add records which is cancelled
		GlobalMembers.increementIndex(Integer.parseInt(GlobalMembers.getBatchCount()));
		GlobalMembers.addFailOutput(output4All);
		GlobalMembers.addAllFailOutput(output4err);
	}

	public List<String[]> generateOutput4Success(BatchSuccessResponse members) {
		List<String[]> output = new ArrayList<>();
		String requestId = members.getRequestId();
		for (SubRequest sub : members.getSubRequests()) {
			output.add(new String[] {requestId, sub.getType(), sub.getSource(), sub.getRecipient(), sub.getStatus(), sub.getConsentDate(), sub.getRecipientType(), sub.getSubRequestId()});
		}
		GlobalMembers.increementIndex(Integer.parseInt(GlobalMembers.getBatchCount()));
		GlobalMembers.addSuccessOutput(output);
		return output;
	}

	@SuppressWarnings("deprecation")
	public void generateReport() {
		List<String[]> outputList4Fail = GlobalMembers.getFailOutput();
		List<String[]> outputList4Succ = GlobalMembers.getSuccessOutput();
		List<String[]> outputList4All = GlobalMembers.getAllOutput();
		List<String[]> outputList4Resume = new ArrayList<>();
		List<CSVFileContent> csvFile = GlobalMembers.getCsvFile();
		for (int i = 0; i < outputList4All.size(); i++) {
			String[] err = outputList4All.get(i);
			int index = Integer.parseInt(err[0]);
			String value = err[1];
			String message = err[2];
			for (int j = 0; j < outputList4Fail.size(); j++) {
				int subIndex = Integer.parseInt(outputList4Fail.get(j)[0]);
				if (index == subIndex) {
					outputList4Fail.set(j, new String[] {String.valueOf(index), value, message});
					break;
				}
			}
		}
		// generate resume
		for (int i = 0; i < outputList4Fail.size(); i++) {
			String[] err = outputList4Fail.get(i);
			int index = Integer.parseInt(err[0]);
				CSVFileContent newRecord = csvFile.get(index-1);
				outputList4Resume.add(new String[] {newRecord.getType(), newRecord.getSource(), newRecord.getRecipient(), newRecord.getStatus(), newRecord.getConsentDate(), newRecord.getRecipientType()});
			}
		
		String filePath4Fail = Constants.Api.ERROR_OUTPUT_FILE_PATH;
		String filePath4Succ = Constants.Api.SUCCEEDED_OUTPUT_FILE_PATH;
		String filePath4resume = Constants.Api.RESUME_OUTPUT_FILE_PATH;
		if(GlobalMembers.isResume()) {
			filePath4Fail = Constants.Api.ERROR_RESUME_OUTPUT_FILE_PATH;
			filePath4Succ = Constants.Api.SUCCEEDED_RESUME_OUTPUT_FILE_PATH;
		}
		
		File file4Fail = new File(filePath4Fail);
		File file4Succ = new File(filePath4Succ);
		CSVWriter writer4Fail = null;
		CSVWriter writer4Succ = null;
		CSVWriter writer4Res = null;
		try {
			FileWriter outputfile4Fail = new FileWriter(file4Fail);
			FileWriter outputfile4Succ = new FileWriter(file4Succ);
			FileWriter outputfile4Res = new FileWriter(filePath4resume);
			outputfile4Fail.write("");
			outputfile4Succ.write("");
			outputfile4Res.write("");
			writer4Fail = new CSVWriter(outputfile4Fail);
			writer4Succ = new CSVWriter(outputfile4Succ);
			writer4Res = new CSVWriter(outputfile4Res, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
			writer4Fail.writeAll(outputList4Fail);
			writer4Succ.writeAll(outputList4Succ);
			writer4Res.writeAll(outputList4Resume);

			writer4Fail.close();
			writer4Succ.close();
			writer4Res.close();

		} catch (IOException ex) {
			Throwable[] suppressed = ex.getSuppressed();
			for (int i = 0; i < suppressed.length; i++) {
				LOG.debug("Hata: {} ", suppressed[i].toString());
			}
			throw new ApiException(Constants.ErrorMessage.WRITE_TO_CSV_ERROR, Constants.ErrorCode.WRITE_TO_CSV_ERROR_CODE);
		}
	}
}

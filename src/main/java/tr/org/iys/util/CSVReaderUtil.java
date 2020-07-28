package tr.org.iys.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import tr.org.iys.config.Constants;
import tr.org.iys.config.GlobalMembers;
import tr.org.iys.model.CSVFileContent; 

@Component
public class CSVReaderUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public List<CSVFileContent> read() {
		LOG.debug(Constants.Message.DATA_IS_BEING_READING);
		BufferedReader reader = null;
		try {
			// check it is resume or not?
			String filePath = Constants.Api.FILE_PATH;
			if(GlobalMembers.isResume()) {
				filePath = Constants.Api.RESUME_OUTPUT_FILE_PATH;
			}
			reader = new BufferedReader(new FileReader(filePath));
		} catch (Exception e) {
			LOG.debug(Constants.ErrorMessage.FILE_NOT_FOUND);
			System.exit(1);
		}
		
	    return builder(reader, CSVFileContent.class);
	}
	

	public List<CSVFileContent> builder(BufferedReader reader, Class<CSVFileContent> clazz) {
		ColumnPositionMappingStrategy<CSVFileContent> ms = new ColumnPositionMappingStrategy<>();
		ms.setType(clazz);
		CsvToBean<CSVFileContent> cb = new CsvToBeanBuilder<CSVFileContent>(reader).withType(clazz).withMappingStrategy(ms).build();
		List<CSVFileContent> list = null;
		try {
			list = cb.parse();
		} catch (Exception e) {
			LOG.debug(Constants.ErrorMessage.FILE_IS_CORRUPTED);
			System.exit(1);
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug(Constants.Message.DATA_IS_OK);
	    return list;
	}
	
}

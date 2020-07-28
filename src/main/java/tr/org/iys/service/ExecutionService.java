package tr.org.iys.service;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import tr.org.iys.config.Constants;
import tr.org.iys.exception.ApiException;
import tr.org.iys.util.BusinessLogicUtil;
import tr.org.iys.util.CSVWriterUtil;
import tr.org.iys.util.ConfigUtil;
import tr.org.iys.util.ValidationUtil;

@Service
public class ExecutionService implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private BusinessLogicUtil businessLogic;
	@Autowired
	private ValidationUtil validation;
	@Autowired
	private CSVWriterUtil writerUtil;
	@Autowired
	private ConfigUtil config;
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	/**
	 * 1. step: validate config parameters
	 * 2. step: validate and load csv file
	 * 3. step: get token from token service by username&password
	 * 4. step: execute batch operation due to sync or async
	 * 5. step: generate response log
	 * 
	 */
	
	public void run(String[] args) {
		try {
			config.configureService(args);
			// validate properties
			validation.validateProperties();
			// do the basic validations over csv
			validation.validateCSV();
			// get token
			String token = businessLogic.getToken();
			// execute batch
			businessLogic.executeBatchOperation(token);
			// write report
			writerUtil.generateReport();
			LOG.debug("Operasyon tamamlandi, resume.csv, error.csv ve success.csv dosyalarindan raporlari goruntuleyebilirsiniz.");
			System.exit(1);
		} catch (ApiException e) {
			LOG.debug("{}, {}", e.getMessage(), e.getErrorCode());
			System.exit(1);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			run(event.getArgs());
		} catch (Exception e) {
			LOG.debug(Constants.ErrorMessage.SERVICE_ERROR);
			System.exit(1);
		}
	}
}

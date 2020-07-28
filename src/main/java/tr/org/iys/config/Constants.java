package tr.org.iys.config;

public final class Constants {
	
	private Constants() {
		throw new IllegalStateException(Api.DEFAULT);
	}
	
	public static class Api {
		private Api() {
			throw new IllegalStateException(Api.DEFAULT);
		}
		public static final String DEFAULT 						= "Utility Class";
		public static final String VERSION 						= "1.0.0";
		public static final String GRAND_TYPE 					= "password";
		public static final String RECEIPIENT_BIREYSEL 			= "BIREYSEL";
		public static final String RECEIPIENT_TACIR 			= "TACIR";
		public static final String TYPE_MESAJ 					= "MESAJ";
		public static final String TYPE_ARAMA 					= "ARAMA";
		public static final String TYPE_EPOSTA 					= "EPOSTA";
		public static final String FILE_PATH 					= "csv/data.csv";
		public static final String ACCESS_TOKEN 				= "access_token";
		public static final String TRANSACTION_ID				= "transactionId";
		public static final String REQUEST_ID 					= "requestId";
		public static final int BATCH_COUNT_MAX_LIMIT_SYNC 		= 50000;
		public static final String ERROR_OUTPUT_FILE_PATH 		= "csv/error.csv";
		public static final String SUCCEEDED_OUTPUT_FILE_PATH 	= "csv/success.csv";
		public static final String RESUME_OUTPUT_FILE_PATH 		= "csv/resume.csv";
		public static final String CONSENT_DATE_FORMAT 			= "yyyy-MM-dd HH:mm:ss";
		public static final String MAY_1_2015 							= "2015-05-01 00:00:00";
		public static final String ERROR_RESUME_OUTPUT_FILE_PATH 		= "csv/resume_error.csv";
		public static final String SUCCEEDED_RESUME_OUTPUT_FILE_PATH 	= "csv/resume_success.csv";
		public static final Object RESUME_PROPERTY 						= "-resume";
	}
	
	public static class ErrorMessage {
		private ErrorMessage() {
			throw new IllegalStateException(Api.DEFAULT);
		}
		public static final String UNKNOWN_ERROR 				= "Bilinmeyen Hata!";
		public static final String FILE_NOT_FOUND 				= "Data dosyasi bulunamadi!";
		public static final String FILE_IS_CORRUPTED 			= "Data dosyasi bozuk!";
		public static final String SERVICE_ERROR 				= "Service hatasi!";
		public static final String API_ERROR 					= "IYS'den cevap alinamadi, lutfen daha sonra tekrar deneyiniz. hata mesaji: {}";
		public static final String USERNAME_PASSWORD_VALIDATION_ERROR_MESSAGE 		= "config dosyasinda kullanici adi veya sifre tanimlanmadi!";
		public static final String IYS_AND_BRAND_CODE_VALIDATION_ERROR_MESSAGE 		= "IYSCode veya BrandCode tanimlanmadi";
		public static final String LOG_VALIDATION_ERROR_MESSAGE 					= "loglevel parametresi 0-1 degerleri alabilir! ";
		public static final String BATCHCOUNT_VALIDATION_ERROR_MESSAGE 				= "batchcount parametresi " + Api.BATCH_COUNT_MAX_LIMIT_SYNC + "degerinden buyuk olamaz";
		public static final String TOKEN_SERVICE_FAILED 				= "Jeton servisi cevap vermedi, daha sonra tekrar deneyiniz!";
		public static final String WRITE_TO_CSV_ERROR 					= "Dosya yazma hatasi, csv/result.csv dosyasinin kullanimda olmadigindan emin olun!";
		public static final String INVALID_TYPE_MESSAGE 				= "\"type\" EPOSTA, ARAMA veya MESAJ degeri alabilir!";
		public static final String INVALID_SOURCE_MESSAGE 				= "\"source\" HS_2015, HS_FIZIKSEL_ORTAM, HS_ISLAK_IMZA, HS_ETKINLIK, HS_ATM, HS_EORTAM, HS_WEB, HS_MOBIL, HS_MESAJ, HS_EPOSTA, HS_CAGRI_MERKEZI, HS_SOSYAL_MEDYA, EPOSTA, degeri alabilir!";
		public static final String INVALID_STATUS_MESSAGE 				= "\"status\" ONAY, RET degeri alabilir!";
		public static final String INVALID_RECIPIENT_MESSAGE 			= "\"recipientType\" BIREYSEL, TACIR degeri alabilir.";
		public static final String INVALID_CONSENT_DATE_MESSAGE 		= "\"date\" hatali, gecerli bir format girin (yyyy-mm-dd hh:mm:ss). orn: 2020-07-18 12:28:30";
		public static final String INVALID_CONSENT_DATE_TIME_MESSAGE 	= "\"date\" hatali, 1 mayıs 2015 ten önceki bir tarih girilemez.";
		public static final String INVALID_CONSENT_DATE_FUTURE_MESSAGE 	= "\"date\" hatali, ileri bir tarih girilemez.";
		public static final String INVALID_DATE 						= "\"date\" hatali.";
		public static final String INVALID_RECIPIENT_EMAIL_MESSAGE 		= "\"type\" EPOSTA oldugu durumda recipient email adresi girilmelidir";
		public static final String INVALID_RECIPIENT_PHONE_MESSAGE 		= "\"type\" ARAMA veya MESAJ oldugu durumda recipient gecerli bir telefon numarasi olmalidir, orn: +901111111111 adresi girilmelidir";
		public static final String FORBIDDEN 							= "iysCode, kullanıcı izinleriyle eslestirilemiyor!";
	}
	
	public static class Message {
		private Message() {
			throw new IllegalStateException(Api.DEFAULT);
		}
		public static final String USERNAME_PASSWORD_IS_MANDATORY 	= "Kullanici adi ve sifre zorunludur!";
		public static final String TOKEN_SUCCESS 					= "Jeton basarili bir sekilde alindi!";
		public static final String TOKEN_IS_BEING_READING 			= "Jeton aliniyor...";
		public static final String TOKEN_FAILED 					= "Jeton alinamadi, kullanici adi/sifre hatali, islem sonlanririliyor...";
		public static final String DATA_IS_BEING_READING 			= "CSV Dosyasi okunuyor...";
		public static final String DATA_IS_OK 						= "CSV Dosyası hazir.";
	}
	
	public static class ErrorCode {
		private ErrorCode() {
			throw new IllegalStateException(Api.DEFAULT);
		}
		public static final int USERNAME_PASSWORD_VALIDATION_ERROR_CODE 	= 10000;
		public static final int IYS_AND_BRAND_CODE_VALIDATION_ERROR_CODE 	= 10100;
		public static final int LOG_VALIDATION_ERROR_CODE 					= 10300;
		public static final int BATCHCOUNT_VALIDATION_ERROR_CODE 			= 10400;
		public static final int UNKNOWN_ERROR_CODE 							= 25000;
		public static final int TOKEN_FAILED_CODE 							= 10500;
		public static final int TOKEN_SERVICE_FAILED_CODE 					= 16000;
		public static final int WRITE_TO_CSV_ERROR_CODE 					= 17000;
		public static final int INVALID_TYPE_CODE 							= 20000;
		public static final int INVALID_SOURCE_CODE 						= 20100;
		public static final int INVALID_STATUS_CODE 						= 20200;
		public static final int INVALID_RECIPIENT_CODE 						= 20300;
	}
	
}

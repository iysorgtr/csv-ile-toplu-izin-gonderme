package tr.org.iys.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableAsync
public class BeanConfig {
	
	@Lazy
	@Autowired
	public ObjectMapper objectMapper;
	
	@PostConstruct
	public ObjectMapper configureMapper() {
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, true);
		objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		
	    return objectMapper;
	}
	
	@Bean(name = "customRestTemplate")
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
	}
	
}

package ac.at.acdh.cmdi.ccr;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CCRService implements ICCRService {

	private static Logger _logger = LoggerFactory.getLogger(CCRService.class);
	
	static {
		HttpsURLConnection
			.setDefaultHostnameVerifier((hostname, session) -> hostname.equals("openskos.meertens.knaw.nl"));
	}

	static final String CCR_SINGLE_CONCEPT = CCRServiceFactory.CCR_REST_API_URL
			+ "concept?fl=status,%20prefLabel@en&format=json&id=";
	static final String CCR_ALL_CONCEPTS = CCRServiceFactory.CCR_REST_API_URL
			+ "find-concepts?q=status:[*%20TO%20*]&fl=status,uri,prefLabel@en&format=json&rows=5000";

	private volatile Map<String, CCRConcept> cache;	

	CCRService() {	
		cache = new HashMap<>();
		try{
			init();
		}catch(IOException e){
			_logger.error("Unable to initialize CCRServise. Probably CCR is not available at the moment");
			throw new RuntimeException("Unable to initialize CCRServise. Probably CCR is not available at the moment", e);
		}
	};

	@Override
	public CCRConcept getConcept(String url){
		return cache.get(url);
	}

	@Override
	public Collection<CCRConcept> getAll(){
		return cache.values();
	}
	
	private void init() throws IOException{	
		_logger.debug("Fetching from {}", CCR_ALL_CONCEPTS);
		String json = IOUtils.toString(new URL(CCR_ALL_CONCEPTS).openStream());
		JsonElement jElem = new JsonParser().parse(json);
		JsonObject obj = jElem.getAsJsonObject();
		obj = obj.getAsJsonObject("response");
		JsonElement numberOfRecords = obj.get("numFound");
		int numFound = numberOfRecords.getAsInt();

		_logger.debug("Number of found concepts in CCR is {}", numFound);

		JsonArray conceptsArray = obj.getAsJsonArray("docs");
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CCRConcept.class, new ConceptTypeAdapter());
		final Gson gson = gsonBuilder.create();
		conceptsArray.forEach(c -> {
			JsonObject conceptJson = c.getAsJsonObject();
			CCRConcept concept = gson.fromJson(conceptJson, CCRConcept.class);
			cache.put(concept.uri, concept);

		});
	}
}

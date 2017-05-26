package ac.at.acdh.cmdi.ccr;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCRServiceFactory {
	private static Logger _logger = LoggerFactory.getLogger(CCRServiceFactory.class);
	static String CCR_REST_API_URL = "https://openskos.meertens.knaw.nl/ccr/api/";
	
	private static CCRServiceFactory fac = null;
	
	private CCRService crrService;
	
	private CCRServiceFactory(){//refresh concept cache
		try {
			crrService = new CCRService();
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					synchronized (crrService) {

							crrService = new CCRService();
						
					}
					
				}
			}, 60 * 60 * 1000 /* Once per day*/);
		} catch (Exception e) {
			_logger.error("", e);
		}
	}
	
	
	public static synchronized ICCRService getCCRService(){
		if(CCRServiceFactory.fac == null){
			CCRServiceFactory.fac = new CCRServiceFactory();
		}
		return CCRServiceFactory.fac.crrService;
	}
	
	public static void set_CCR_REST_API_URL(String newUrl){
		CCR_REST_API_URL = newUrl;
	}
}
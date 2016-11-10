package ac.at.acdh.cmdi.ccr;

import java.util.Timer;
import java.util.TimerTask;

public class CCRServiceFactory {
	
	static String CCR_REST_API_URL = "https://openskos.meertens.knaw.nl/ccr/api/";
	
	private static CCRService singlton = new CCRService();
	
	static{//refresh concept cache
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				synchronized (singlton) {
					singlton = new CCRService();
				}
				
			}
		}, 60 * 60 * 1000 /* Once per day*/);
	}
	
	
	public static synchronized ICCRService getCCRService(){
		return singlton;
	}
	
	public static void set_CCR_REST_API_URL(String newUrl){
		CCR_REST_API_URL = newUrl;
	}
}

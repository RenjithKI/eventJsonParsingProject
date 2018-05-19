package com.renjith.minimum.webProject;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.renjith.minimum.webProject.service.EventProcessService;
/**
 * @author Renjith Kachappilly Ittoop
 * http://localhost:8080/stats
 *
 */
@Controller
public class WebProjectController {

	private static Logger logger = Logger
			.getLogger(WebProjectController.class);

	@Autowired
	private EventProcessService eventProcessService;
	/**
	 *
	 * @return stats view
	 */
	@RequestMapping(value = "/stats", method=RequestMethod.GET)
	public String stats(Model model) {
		logger.info("inside stats method");
		Map<String, Integer> eventMap = eventProcessService.getEventMap();
		Map<String, Integer> dataMap = eventProcessService.getDataMap();
		if (eventMap != null) {	
			model.addAttribute("eventMap", eventMap);			
		}
		if (dataMap != null) {			
			model.addAttribute("dataMap", dataMap);				
		}		
		return "stats";
	}

		/**
		 *
		 * @return home view
		 */
	@RequestMapping("/")
	public String home() {
			return "redirect:/home";
		}
		@RequestMapping("/home")
		public String index() {
			return "home";
		}

	/**
	 *
	 * @return error view
	 */
	@ExceptionHandler(value=RuntimeException.class)
	@ResponseStatus(value=HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public String error() {
		return "error";
	}
}

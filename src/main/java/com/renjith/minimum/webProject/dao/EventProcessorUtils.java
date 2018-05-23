package com.renjith.minimum.webProject.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renjith.minimum.webProject.domain.EventObject;

/**
 * @author Renjith Kachappilly Ittoop
 *using scanner
 */

public class EventProcessorUtils {
	public static List<String> produceValidList(final String textFilePath) throws IOException {		
		List<String> stringList = new ArrayList<>(2100);
		try (Scanner s = new Scanner(new BufferedReader(new FileReader(textFilePath))) ) {
			while (s.hasNextLine() ) {
				String st = s.nextLine().trim();
				if (isValidEventJsonLine(st)) 
				{					
					stringList.add(st);					
				}
			}
		}
		return stringList;
	}

	public static boolean isValidEventJsonLine(String st) {		
		/*add a regular expression here in need of validation of text*/
		return !st.isEmpty() && !st.equals("") && st.startsWith("{") && st.endsWith("}") && st.contains("event_type");
	}

	public static List<EventObject> parseJsonObjFromString(List<String>  listring) {
		List<EventObject> list = new ArrayList<>(2100);		
		ObjectMapper mapper = new ObjectMapper();	
		
		list = listring.parallelStream().map(line -> {			
			EventObject obj = null;
			try {
				obj = mapper.readValue(line, EventObject.class);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
            
            return obj;
        }).collect(Collectors.toList());
		return list;
	}


	public static Map<String, Map<String, Long>> getStats(List<EventObject> objList) {
		
		Map<String, Map<String, Long> > map = new HashMap<>(2);

		Map<String, Long> eventStat =
				objList.parallelStream().collect(Collectors.groupingBy(EventObject::getEvent_type, (Collectors.counting()) ));		
		
		Map<String, Long> dataStat =
				objList.parallelStream().collect(Collectors.groupingBy(EventObject::getData, Collectors.counting() ));		
		
		map.put("event", eventStat);
		map.put("data", dataStat);
		return map;
	}
}
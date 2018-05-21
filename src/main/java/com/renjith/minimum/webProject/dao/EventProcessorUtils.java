package com.renjith.minimum.webProject.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

		for (String line : listring) {
			try {
				EventObject objJson = mapper.readValue(line, EventObject.class);
				//getStats(objJson);
				list.add(objJson);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return list;
	}


	public static Map<String, Map<String, Integer> > getStats(List<EventObject> objList) {		

		Map<String, Map<String, Integer> > map = new HashMap<String, Map<String, Integer> >(2);
		Map<String, Integer> eventMap = new HashMap<String, Integer>();
		Map<String, Integer> dataMap = new HashMap<String, Integer>();


		for (EventObject o : objList) {
			String eventType = o.getEvent_type();
			String data = o.getData();
			if (eventMap.containsKey(eventType)) {
				eventMap.put(eventType, eventMap.get(eventType) + 1);
			} else {
				eventMap.put(eventType, 1);
			}
			if (dataMap.containsKey(data)) {
				dataMap.put(data, dataMap.get(data) + 1);
			} else {
				dataMap.put(data, 1);
			} 
		}
		map.put("event", eventMap);
		map.put("data", dataMap);
		return map;
	}
}
package com.renjith.minimum.webProject.dao;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
/*import org.springframework.context.annotation.Primary;*/
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renjith.minimum.webProject.domain.EventObject;

/**
 * @author Renjith Kachappilly Ittoop
 *
 */
@Component
/*@Primary*/
public class EventProcessorDaoImpl implements EventProcessorDao {

	private List<EventObject> list = new ArrayList<>(2100);
	private Map<String, Integer> eventMap = new HashMap<String, Integer>();
	private Map<String, Integer> dataMap = new HashMap<String, Integer>();

	final String textFilePath;


	@Autowired
	public EventProcessorDaoImpl(@Value( "${textFile.path}" ) final String textFilePath) throws IOException{
		this.textFilePath = textFilePath;		
		init(textFilePath);		
	}

	private void init(final String textFilePath) throws IOException {

		try (Scanner s = new Scanner(new BufferedReader(new FileReader(textFilePath))) ) {
			while (s.hasNextLine() ) {
				String st = s.nextLine().trim();
				if (isValidEventJsonLine(st)) 
				{
					parseJsonObjFromString(st );
				}
			}
		}
	} 

	public boolean isValidEventJsonLine(String st) {		
		/*add a regular expression here in need validation of text*/
		return !st.isEmpty() && !st.equals("") && st.startsWith("{") && st.endsWith("}") && st.contains("event_type");
	}

	private void parseJsonObjFromString(String line) {
		ObjectMapper mapper = new ObjectMapper();
		try {		
			EventObject objJson = mapper.readValue(line, EventObject.class);
			getStats(objJson);
			list.add(objJson);			
		} catch (JsonGenerationException e) {			
			e.printStackTrace();
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	private void getStats(EventObject o) {
		String eventType  = o.getEvent_type();
		String data  = o.getData();

		if (eventMap.containsKey(eventType)){
			eventMap.put(eventType, eventMap.get(eventType)+1);
		}else {
			eventMap.put(eventType, 1); 
		}
		if (dataMap.containsKey(data)){
			dataMap.put(data, dataMap.get(data)+1);
		}else {
			dataMap.put(data, 1); 
		}

	}

	@Override
	public Map<String, Integer> getEventMap() {		
		return eventMap;
	}

	@Override
	public Map<String, Integer> getDataMap() {		
		return dataMap;
	}

}
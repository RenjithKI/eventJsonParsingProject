package com.renjith.minimum.webProject.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.renjith.minimum.webProject.domain.EventObject;
/**
 * @author Renjith Kachappilly Ittoop
 *
 */
@Component
@Primary
public class EventProcessorDaoImplVersion2 implements EventProcessorDao {	
	
	private Map<String, Map<String, Integer> > map = new HashMap<>(2);
	final String textFilePath;
	
	@Autowired
	public EventProcessorDaoImplVersion2(@Value( "${textFile.path}" ) final String textFilePath) throws IOException{
		this.textFilePath = textFilePath;		
		
		List<String>  listring =  EventProcessorUtils.produceValidList(textFilePath);
		List<EventObject> liobj = EventProcessorUtils.parseJsonObjFromString(listring);		
		map = EventProcessorUtils.getStats(liobj);	
	}	

	@Override
	public Map<String, Integer> getEventMap() {		
		return map.get("event");
	}

	@Override
	public Map<String, Integer> getDataMap() {		
		return map.get("data");
	}
}
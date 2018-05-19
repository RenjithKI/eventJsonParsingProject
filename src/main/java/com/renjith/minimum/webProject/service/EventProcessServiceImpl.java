package com.renjith.minimum.webProject.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renjith.minimum.webProject.dao.EventProcessorDao;
/**
 * @author Renjith
 *
 */
@Service
public class EventProcessServiceImpl implements EventProcessService  {
	
	@Autowired
	private EventProcessorDao eventProcessorDao;

	@Override
	public Map<String, Integer> getEventMap() {		
		return eventProcessorDao.getEventMap();
	}

	@Override
	public Map<String, Integer> getDataMap() {		
		return eventProcessorDao.getDataMap();
	}

}

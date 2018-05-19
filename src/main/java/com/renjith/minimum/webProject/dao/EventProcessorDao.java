package com.renjith.minimum.webProject.dao;

import java.util.Map;

/**
 * @author Renjith
 *
 */
public interface EventProcessorDao {
	
	public Map<String, Integer> getEventMap();

	public Map<String, Integer> getDataMap();


}
package com.renjith.minimum.webProject.dao;

import java.util.Map;

/**
 * @author Renjith
 *
 */
public interface EventProcessorDao {
	
	public Map<String, Long> getEventMap();

	public Map<String, Long> getDataMap();


}
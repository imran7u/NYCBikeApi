package com.nyc.station.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nyc.station.api.controller.model.BikeStats;
import com.nyc.station.info.dao.StationInfoDao;
import com.nyc.station.info.model.StationInfo;
import com.nyc.station.info.service.StationInfoService;
import com.nyc.station.job.read.json.steam.ReadStreamJob;
import com.nyc.station.status.dao.StationStatusDao;
import com.nyc.station.status.model.AvailableBikesAndDocks;
import com.nyc.station.status.service.StationStatusService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-config-test.xml" })
public class NycStationTest {

	@Autowired private StationStatusDao _stationStatusDao;
	@Autowired private StationInfoDao _stationInfoDao;
	
	@Autowired 	StationStatusService _stationStatusService;
	@Autowired 	StationInfoService _stationInfoService;
	
	@Autowired 	ReadStreamJob _readStreamJob;
	

	@Test
	public void testStationStatusDao() throws Exception {
		AvailableBikesAndDocks available = _stationStatusDao.getGlobalAvailableBikeDocks("146");
		assertNotNull(available);
		
		available = _stationStatusDao.getGlobalAvailableBikeDocks("%");
		assertNotNull(available); 
	}
	
	@Test
	public void testStationStatusService() throws Exception { 
		
		BikeStats bikeStats = _stationStatusService.getMonthlyStats(2); // month 2
		assertNotNull(bikeStats);
		assertNotNull(bikeStats.getBikeRides());
		assertNotNull(bikeStats.getDisableBikes());
		assertTrue("Available Bikes should be greater than 0 ", bikeStats.getBikeRides() > 0);
		assertTrue("Disable Bikes should be greater than 0 ", bikeStats.getDisableBikes() > 0);
		
		String stationId = _stationStatusService.getPopularStation(1); // month 1
		assertNotNull(stationId);
	}
	
	@Test
	public void testStationInfoDao() throws Exception {
		StationInfo info = _stationInfoDao.findById(9926);
		assertNotNull(info);
		
		info = _stationInfoDao.getClosestSation("-73.972924", "40.761628");
		assertNotNull(info);
		
		info = _stationInfoDao.getClosestStreetSation("Prince St");
		assertNotNull(info);
		
		List<StationInfo> infoList = _stationInfoDao.getSationWithCapicity(20);
		assertNotNull(infoList);
		assertEquals(0, infoList.size());
	}

	@Test
	public void testFindForAccountByCodeValidBlanketCoupon() throws Exception {
		int readCount = _readStreamJob.readStationInfoStream();
		assertTrue("StationInfo parsed objects should be greater than 0 ", readCount > 0);
	}
	
	@Test
	public void testDate() throws Exception {
		Calendar c = Calendar.getInstance();  
		c.set(Calendar.MONTH, 2);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		
		assertEquals(2, c.get(Calendar.MONTH));
		assertEquals(31, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
 
}

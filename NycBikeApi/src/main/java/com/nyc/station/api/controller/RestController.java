package com.nyc.station.api.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nyc.station.api.controller.builder.StationDataModelBuilder;
import com.nyc.station.api.controller.model.BikeStats;
import com.nyc.station.api.controller.model.GetAvailableBikeDockDto;
import com.nyc.station.api.controller.model.MonthlyStatsResDto;
import com.nyc.station.api.controller.model.PopularStationIdResDto;
import com.nyc.station.api.controller.model.StationDataResDto;
import com.nyc.station.api.controller.model.StationsDataResDto;
import com.nyc.station.info.model.StationInfo;
import com.nyc.station.info.service.StationInfoService;
import com.nyc.station.status.model.AvailableBikesAndDocks;
import com.nyc.station.status.service.StationStatusService;
 


/**
 * 
 * @author Imran
 *
 */

@Controller
@RequestMapping("/service")
public class RestController {

	@Autowired
	StationStatusService _stationStatusService;
	@Autowired
	StationInfoService _stationInfoService;

	static final Logger logger = Logger.getLogger(RestController.class);
	
	
	/**
	 * overall-stats 
	 * Returns the global number of bikes and docks available right now
	 * @return
	 */
	@RequestMapping(value="/overall-stats", method=RequestMethod.GET) 
	public @ResponseBody GetAvailableBikeDockDto getOverAllStats() {
		GetAvailableBikeDockDto responseDto = new GetAvailableBikeDockDto();
		HttpStatus httpStatus = HttpStatus.OK;
		AvailableBikesAndDocks available = null;
		try {
			available = _stationStatusService.getGlobalAvailableBikeDocks();
			
				if (available == null) {
					responseDto.setMessage("Could not find any availabe Dock/Bike number.");
					responseDto.setResult("error");
				} else {
					responseDto.setAvailableBikeDocks(StationDataModelBuilder.builAvailableBikeDock(available));
				}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}
	
	
	/**
	 * 
	 *  Returns the number of bikes and docks available for “station_id” right now
	 * @param stationId
	 * @return
	 */
	@RequestMapping(value="/station-stats/{stationId}", method=RequestMethod.GET) 
	public @ResponseBody GetAvailableBikeDockDto getOverAllStats(@PathVariable("stationId") String stationId) {
		GetAvailableBikeDockDto responseDto = new GetAvailableBikeDockDto();
		HttpStatus httpStatus = HttpStatus.OK;
		AvailableBikesAndDocks available = null;
		try {
			
			if (stationId == null || stationId.isEmpty()) {
				responseDto.setMessage("stationId required");
				responseDto.setResult("error");
				return responseDto;
			}
			
			available = _stationStatusService.getStationStats(stationId);
			
			if (available == null) {
				responseDto.setMessage("Could not find station-stats.");
				responseDto.setResult("error");
			} else {
				responseDto.setAvailableBikeDocks(StationDataModelBuilder.builAvailableBikeDock(available));
			}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}

	
	/**
	 * 
	 * @param longitude
	 * @param latitude
	 * @return Returns the closest station to the given “latitude” and “longitude”
	 */
	@RequestMapping(value="/closest-station/{longitude},{latitude}", method=RequestMethod.GET) 
	public @ResponseBody StationDataResDto getClosestStation(@PathVariable("longitude") String longitude, @PathVariable("latitude") String latitude) {
		StationDataResDto responseDto = new StationDataResDto();
		HttpStatus httpStatus = HttpStatus.OK;
		StationInfo available = null;
		try {
			if (longitude == null || longitude.isEmpty() || latitude == null || latitude.isEmpty()) {
				responseDto.setMessage("{longitude},{latitude} required");
				responseDto.setResult("error");
				return responseDto;
			}
			
			available = _stationInfoService.getClosestSation(longitude, latitude);
			
			if (available == null) {
				responseDto.setMessage("Could not find station-stats.");
				responseDto.setResult("error");
			} else {
				responseDto.setStationData(StationDataModelBuilder.builStationData(available));
			}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}
	
	
	/**
	 * 
	 * @param street_name
	 * @return Returns the closest station to the given “street_name”
	 */
	
	@RequestMapping(value="/closest-station-by-name/{street_name}", method=RequestMethod.GET) 
	public @ResponseBody StationDataResDto getClosestStation(@PathVariable("street_name") String street_name) {
		StationDataResDto responseDto = new StationDataResDto();
		HttpStatus httpStatus = HttpStatus.OK;
		StationInfo available = null;
		try {
			
			if (street_name == null || street_name.isEmpty()) {
				responseDto.setMessage("{street_name} required");
				responseDto.setResult("error");
				return responseDto;
			}
			
			available = _stationInfoService.getClosestStreetSation(street_name);
			
			if (available == null) {
				responseDto.setMessage("Could not find station-stats.");
				responseDto.setResult("error");
			} else {
				responseDto.setStationData(StationDataModelBuilder.builStationData(available));
			}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}
	
	
	/**
	 * 
	 * @param num_bikes
	 * @return Returns a list of all stations which have at least “num_bikes” available
	 */
	@RequestMapping(value="/stations-with-capacity/{num_bikes}", method=RequestMethod.GET) 
	public @ResponseBody StationsDataResDto getSationWithCapicity(@PathVariable("num_bikes") Integer num_bikes) {
		StationsDataResDto responseDto = new StationsDataResDto();
		HttpStatus httpStatus = HttpStatus.OK;
		List<StationInfo> available = null;
		try {
			
			if (num_bikes == null || num_bikes ==0) {
				responseDto.setMessage("{num_bikes} required");
				responseDto.setResult("error");
				return responseDto;
			}
			
			available = _stationInfoService.getSationWithCapicity(num_bikes);
			
			if (available == null || available.isEmpty()) {
				responseDto.setMessage("Could not find station-stats.");
				responseDto.setResult("error");
			} else {
				responseDto.setStationData(StationDataModelBuilder.builStationListData(available));
			}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}

	 
	/**
	 * 
	 * @param month
	 * @return Returns the number of bike rides and disabled bikes for “month”
	 */
	@RequestMapping(value="/monthly-stats/{month}", method=RequestMethod.GET) 
	public @ResponseBody MonthlyStatsResDto getPopularStation(@PathVariable("month") Integer month) {
		MonthlyStatsResDto responseDto = new MonthlyStatsResDto();
		HttpStatus httpStatus = HttpStatus.OK;
		BikeStats available = null;
		try {
			
			if (month == null || month<1 || month >12) {
				responseDto.setMessage("month should be bitween (0,12)");
				responseDto.setResult("error");
				return responseDto;
			}
			available = _stationStatusService.getMonthlyStats(month);
			
			if (available == null) {
				responseDto.setMessage("Could not find station-stats.");
				responseDto.setResult("error");
			} else {
				responseDto.setBikeStats(available);
			}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}
	 
	 
	/**
	 * 
	 * @param month
	 * @return 
	 * Returns the id of the most popular station in “month”. 
	 * Popularity is a function of the usage of the station 
	 */
	
	@RequestMapping(value="/popular-station/{month}", method=RequestMethod.GET) 
	public @ResponseBody PopularStationIdResDto getPopularStations(@PathVariable("month") Integer month) {
		PopularStationIdResDto responseDto = new PopularStationIdResDto();
		HttpStatus httpStatus = HttpStatus.OK;
		String available = null;
		try {
			
			if (month == null || month<1 || month >12) {
				responseDto.setMessage("month should be bitween (0,12)");
				responseDto.setResult("error");
				return responseDto;
			}
			
			available = _stationStatusService.getPopularStation(month);
			
			if (available == null) {
				responseDto.setMessage("Could not find popular-station.");
				responseDto.setResult("error");
			} else {
				responseDto.setStationId(available);
			}
		} catch (Exception e) {
			responseDto.setMessage("Could not process request, an error occured please try again.");
			responseDto.setResult("error");
			logger.error("", e);
		}
		return responseDto;
	}
	
}

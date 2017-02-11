package com.nyc.station.api.controller.builder;

import java.util.ArrayList;
import java.util.List;

import com.nyc.station.api.controller.model.AvailableBikeDockDto;
import com.nyc.station.api.controller.model.StationData;
import com.nyc.station.info.model.StationInfo;
import com.nyc.station.status.model.AvailableBikesAndDocks;
import com.nyc.station.status.model.StationStatus;
 
public class StationDataModelBuilder {
 
	public static List<AvailableBikeDockDto> statusToAvailableBikeDock(List<StationStatus> stations){
		List<AvailableBikeDockDto> dtoList = new ArrayList<AvailableBikeDockDto>();
		for (StationStatus stationStatus : stations) {
			dtoList.add(builAvailableBikeDock(stationStatus));
		}
		return dtoList;
		
	}
	
	
	public static AvailableBikeDockDto builAvailableBikeDock(StationStatus emStatus) {
		AvailableBikeDockDto wsBikeDockDto = new AvailableBikeDockDto();
		wsBikeDockDto.setBikeNumber(emStatus.getNum_bikes_available());
		wsBikeDockDto.setDockNumber(emStatus.getNum_docks_available());
		return wsBikeDockDto;
	}
	
	public static AvailableBikeDockDto builAvailableBikeDock(AvailableBikesAndDocks emStatus) {
		AvailableBikeDockDto wsBikeDockDto = new AvailableBikeDockDto();
		wsBikeDockDto.setBikeNumber(emStatus.getNumBikesAvailable());
		wsBikeDockDto.setDockNumber(emStatus.getNumDocksAvailable());
		return wsBikeDockDto;
	}
	
	
	public static List<StationData> builStationListData(List<StationInfo> emInfo) {
		
		 List<StationData> stationList = new ArrayList<StationData>();
		 for (StationInfo emData : emInfo) {
			 StationData stationData = builStationData(emData);
			 stationList.add(stationData);
		} 
		return stationList;
	}
	public static StationData builStationData(StationInfo emInfo) {
		StationData wsStationDto = new StationData();
		wsStationDto.setCapacity(emInfo.getCapacity());
		wsStationDto.setEightd_has_key_dispenser(emInfo.isEightd_has_key_dispenser());
		wsStationDto.setLat(emInfo.getLat());
		wsStationDto.setLon(emInfo.getLon());
		wsStationDto.setName(emInfo.getName());
		wsStationDto.setRegion_id(emInfo.getRegion_id());
		wsStationDto.setRental_methods(emInfo.getRental_methods());
		wsStationDto.setShort_name(emInfo.getShort_name());
		wsStationDto.setStation_id(emInfo.getStation_id());
		return wsStationDto;
	}
}

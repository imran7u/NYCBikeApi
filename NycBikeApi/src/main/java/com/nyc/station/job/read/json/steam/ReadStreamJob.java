package com.nyc.station.job.read.json.steam;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.nyc.station.constant.Constants;
import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.info.dao.StationInfoDao;
import com.nyc.station.info.model.StationInfo;
import com.nyc.station.status.dao.StationStatusDao;
import com.nyc.station.status.model.StationStatus;


/**
 * 
 * @author Imran
 *
 */
public class ReadStreamJob {

	private static final Logger _logger = LoggerFactory.getLogger(ReadStreamJob.class);
	
	@Autowired
	private StationStatusDao _stationStatusDao;
	@Autowired
	private StationInfoDao _stationInfoDao;
	
	private Lock statusReadLock = new ReentrantLock();
	private Lock infoReadLock = new ReentrantLock();
	
	private Integer _interval = 0;

	private String _stationInfoURL = null; 
	private String _stationStatusURL = null; 
	
	
	
	public void init() {
		startJob();
	}
	

	public void startJob() {
		while (_interval == 0) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		
		_logger.info("StationStatus Reader Job Started at " + new Date() + ", interval : " + _interval);
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					readStationInfoStream();
				} catch (Exception e) {
					_logger.warn("ReadStationInfoStream error", e);
				}
				try {
					readStationStatus();
				} catch (Exception e) {
					_logger.warn("ReadStationInfoStream error", e);
				}
			}
		}, 0, _interval, TimeUnit.SECONDS);

	}

	/**
	 * @return 
	 * 
	 */

	public int readStationInfoStream() {
		InputStream stream = null;
		JsonReader reader = null;
		List<StationInfo> stationInfoList = new ArrayList<StationInfo>();
		try {
			infoReadLock.lock();
			_logger.info("infoReadLock Stream mode: ");
			stream = new URL(_stationInfoURL).openStream();
			reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
			Gson gson = new GsonBuilder().create();
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (Constants.TAG_LAST_UPDATED.equals(name)) {
					_logger.info("last_updated : " + new Date(Long.parseLong(reader.nextString()) * 1000));
				} else if (Constants.TAG_AGE.equals(name)) {
					_logger.info(Constants.TAG_AGE + " : " +reader.nextInt());
				} else if (Constants.TAG_DATA.equals(name)) {
					reader.beginObject();
					name = reader.nextName();
					if (Constants.TAG_STATION.equals(name)) {
						reader.beginArray();
						while (reader.hasNext()) {
							StationInfo info = gson.fromJson(reader, StationInfo.class);
							stationInfoList.add(info);
						}
						reader.endArray();
					}
				} else {
					reader.skipValue(); // avoid some unhandle events
				}
			}

			reader.endObject();

			_logger.info("stationInfoList Count : "+ stationInfoList.size());
			if (!stationInfoList.isEmpty()) {
				_stationInfoDao.saveAll(stationInfoList);
			}
		} catch (UnsupportedEncodingException ex) {
			_logger.warn("ReadStationInfoStream error", ex);
		} catch (IOException ex) {
			_logger.warn("ReadStationInfoStream error", ex);
		} catch (DaoException e) {
			_logger.warn("Error Saving Station Info", e);
		} finally {
			infoReadLock.unlock();
			try {
				reader.close();
			} catch (IOException e) {
			}
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
		return stationInfoList.size();
	}

	public void readStationStatus() {
		InputStream stream = null;
		JsonReader reader = null;
		try {
			statusReadLock.lock();

			List<StationStatus> stationStatusList = new ArrayList<StationStatus>();
			_logger.info("infoReadLock Stream mode: ");
			stream = new URL(_stationStatusURL).openStream();
			reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
			Gson gson = new GsonBuilder().create();
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (Constants.TAG_LAST_UPDATED.equals(name)) {
					_logger.info("last_updated : " + new Date(Long.parseLong(reader.nextString()) * 1000));
				} else if (Constants.TAG_AGE.equals(name)) {
					_logger.info(Constants.TAG_AGE + " : " +reader.nextInt());
				} else if (Constants.TAG_DATA.equals(name)) {
					reader.beginObject();
					name = reader.nextName();
					if (Constants.TAG_STATION.equals(name)) {
						reader.beginArray();
						while (reader.hasNext()) {
							StationStatus stationStatus = gson.fromJson(reader, StationStatus.class);
							if (stationStatus.getLast_reported() !=null) {
								stationStatus.setLast_reported_date(new Date(stationStatus.getLast_reported() * 1000));
							}
							stationStatusList.add(stationStatus);
						}
						reader.endArray();
					}
				} else {
					reader.skipValue(); // avoid some unhandle events
				}
			}

			reader.endObject();

			_logger.info("readStationStatus Count : "+ stationStatusList.size());
			if (!stationStatusList.isEmpty()) {
				_stationStatusDao.saveAll(stationStatusList);
			}
		} catch (UnsupportedEncodingException ex) {
			_logger.warn("ReadStationStatus error", ex);
		} catch (IOException ex) {
			_logger.warn("ReadStationStatus error", ex);
		} catch (DaoException e) {
			_logger.warn("Error Saving Station Status", e);
		} finally {
			statusReadLock.unlock();
			try {
				reader.close();
			} catch (IOException e) {
			}
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	} 
	public void setStationInfoURL(String stationInfoURL) {
		_stationInfoURL = stationInfoURL;
	}

	public void setStationStatusURL(String stationStatusURL) {
		_stationStatusURL = stationStatusURL;
	}

	public void setInterval(Integer interval) {
		_interval = interval;
	}
}

package com.nyc.station.entity.model;

import java.util.Date;

public interface Entity extends Transportable {
//	public Integer getId();
	public Date getCreationDate();
	public Date getModificationDate();
}

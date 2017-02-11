# NYCBikeApi
Web-service for Querying Real-time NYC Bike Sharing Data

The status and usage data for each station in the city is open and available as a live JSON stream.
Static station information,
Dynamic station information, such as, the number of currently available bikes

In this Project, live data collection and serving solution that will periodically ingest this live stream and make it queryable via a simple REST API.

#Prerequisites

Java 7
Maven 3

#Instructions

1. Install MYSQL 
2. Initialize database and set up schema using DB scripts
3. Configure Maven

#Configurations
Open the application.properties file and set your own configurations for the database connection.

#From Eclipse (Spring Tool Suite) 
Your project is now ready to be imported into Eclipse.
File -> Import -> Existing Maven project into Eclipse

#To run the application:
mvn clean install

clean package jetty:run

#Usage

 	Run the application and go on http://localhost:8081/
	Use the following urls to invoke controllers methods and see the interactions with the database:
		/service/overall-stats: number of bikes and docks available right now.
		/station-stats/[station_id]: number of bikes and docks available for “station_id” right now
		/closest-station/[longitude]/[latitude]: closest station to the given “latitude” and “longitude”
		/closest-station-by-name/[street_name]: closest station to the given “street_name”
		/stations-with-capacity/[num_bikes]:  list of all stations which have at least “num_bikes” available
		/monthly-stats/[month]: number of bike rides and disabled bikes for “month”
		/popular-station/[month]: id of the most popular station in “month”

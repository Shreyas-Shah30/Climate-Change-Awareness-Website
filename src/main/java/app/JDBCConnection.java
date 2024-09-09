package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/climate.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<Population> getPopulation(String view, int startYear, int endYear, String sortBy, String sortOrder, String specificCountry) {
        ArrayList<Population> populations = new ArrayList<>();
    
        Connection connection = null;
    
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
       String query = "SELECT c1.Country, c1.Year AS StartYear, c2.Year AS EndYear, c1.AverageTemperature AS StartYearAvgTemp, c2.AverageTemperature AS EndYearAvgTemp, " +
        "(c2.AverageTemperature - c1.AverageTemperature) AS TemperatureDiff, " +
        "(c2.Population - c1.Population) AS PopulationDiff, " +
        "((c2.AverageTemperature - c1.AverageTemperature)/c1.AverageTemperature) * 100 AS TempPercentageChange, " +
        "((c2.Population - c1.Population)/c1.Population) * 100 AS PopPercentageChange " +
        "FROM CountryYearlyLandTemp c1 " +
        "INNER JOIN CountryYearlyLandTemp c2 ON c1.Country = c2.Country " +
        "WHERE c1.Year = " + startYear + " AND c2.Year = " + endYear;

    
            if (sortBy.equalsIgnoreCase("Temperature")) {
                query += " ORDER BY TemperatureDiff";
            } else if (sortBy.equalsIgnoreCase("Population")) {
                query += " ORDER BY PopulationDiff";
            }
    
            if (sortOrder.equalsIgnoreCase("Ascending")) {
                query += " ASC";
            } else if (sortOrder.equalsIgnoreCase("Descending")) {
                query += " DESC";
            }
    
            ResultSet results = statement.executeQuery(query);
    
            while (results.next()) {
                String name = results.getString("Country");
                int StartYear = results.getInt("StartYear");
                int EndYear = results.getInt("EndYear");
                double startYearAvgTemp = results.getDouble("StartYearAvgTemp");
                double endYearAvgTemp = results.getDouble("EndYearAvgTemp");
                double temperatureDiff = results.getDouble("TemperatureDiff");
                long populationDiff = results.getInt("PopulationDiff");
                double temperaturePercentageDiff = results.getDouble("TempPercentageChange");
                double populationPercentageDiff = results.getDouble("PopPercentageChange");
    
                Population populationData = new Population(name, StartYear, EndYear, startYearAvgTemp, endYearAvgTemp, temperatureDiff, populationDiff, temperaturePercentageDiff, populationPercentageDiff);

                populations.add(populationData);
            }
    
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    
        return populations;
    }
    


        public ArrayList<World> getWorld() {
            ArrayList<World> world = new ArrayList<World>();
    
            // Setup the variable for the JDBC connection
            Connection connection = null;
    
            try {
                // Connect to JDBC data base
                connection = DriverManager.getConnection(DATABASE);
    
                // Prepare a new SQL Query & Set a timeout
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
    
                // The Query
                String query = "SELECT * FROM GlobalYearlyTemp";
    
    
                // Get Result
                ResultSet results = statement.executeQuery(query);
    
                // Process all of the results
                while (results.next()) {
                    int year = results.getInt("Year");
                    double averageTemperature = results.getDouble("AverageTemperature");
                    double minimumTemperature = results.getDouble("MinimumTemperature");
                    double maximumTemperature = results.getDouble("MaximumTemperature");
                    double landAvgTemp = results.getDouble("LandOceanAverageTemperature");
                    double landMinTemp = results.getDouble("LandOceanMinimumTemperature");
                    double landMaxTemp = results.getDouble("LandOceanMaximumTemperature");
                    long WorldPopulation = results.getLong("Population");
                    
                    if (results.wasNull()) {
                        landAvgTemp = 0; 
                        landMinTemp = 0;
                        landMaxTemp = 0; 
                    }
                 
                    World worldData = new World(year, averageTemperature, minimumTemperature, maximumTemperature, landAvgTemp, landMinTemp, landMaxTemp, WorldPopulation);
                    world.add(worldData);
                }
    
                // Close the statement because we are done with it
                statement.close();
            } catch (SQLException e) {
                // If there is an error, lets just pring the error
                System.err.println(e.getMessage());
            } finally {
                // Safety code to cleanup
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    // connection close failed.
                    System.err.println(e.getMessage());
                }
            
            return world;
        }  
        
}

public ArrayList<Worlds> getWorlds(String view, int startYear, int endYear, String sortBy, String sortOrder) {
    ArrayList<Worlds> worlds = new ArrayList<Worlds>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
     String query = "SELECT c1.Year AS StartYear, c2.Year AS EndYear, (c2.AverageTemperature - c1.AverageTemperature) AS temperatureDiff, " +
        "(c2.LandOceanAverageTemperature - c1.LandOceanAverageTemperature) AS landOceanTemperatureDiff, " +
        "(c2.Population - c1.Population) AS populationDiff, " +
        "((c2.AverageTemperature - c1.AverageTemperature)/c1.AverageTemperature) * 100 AS TempPercentageChange, " +
        "((c2.LandOceanAverageTemperature - c1.LandOceanAverageTemperature)/c1.LandOceanAverageTemperature) * 100 AS LandTempPercentageChange, " +
      "((c2.Population - c1.Population)/c1.Population) * 100 AS PopPercentageChange " +
        "FROM GlobalYearlyTemp c1 " +
        "JOIN GlobalYearlyTemp c2 ON c2.Year > c1.Year " +
        "WHERE c1.Year = " + startYear + " AND c2.Year = " + endYear;


        
        
            if (sortBy.equalsIgnoreCase("Temperature")) {
                query += " ORDER BY temperatureDiff";
            } else if (sortBy.equalsIgnoreCase("Population")) {
                query += " ORDER BY populationDiff";
            } 
    
            if (sortOrder.equalsIgnoreCase("Ascending")) {
                query += " ASC";
            } else if
                (sortOrder.equalsIgnoreCase("Descending")) {
                query += " DESC";
            } 
    
            // Get Result
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                int StartYear = results.getInt("StartYear");
                int EndYear = results.getInt("EndYear");
                double averageTemperatureDiff = results.getDouble("temperatureDiff");
                double landAvgTempDiff = results.getDouble("landOceanTemperatureDiff");
                long WorldPopulationDiff = results.getLong("populationDiff");
                double temperaturePercentageDiff = results.getDouble("TempPercentageChange");
                double landTemperaturePercentageDiff = results.getDouble("LandTempPercentageChange");
                double PopPercentageChange = results.getDouble("PopPercentageChange");


                Worlds worldData = new Worlds(StartYear, EndYear, averageTemperatureDiff, landAvgTempDiff, WorldPopulationDiff, temperaturePercentageDiff, landTemperaturePercentageDiff, PopPercentageChange);
                worlds.add(worldData);
            }

        // Close the statement because we are done with it
        statement.close();
    } catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    
    return worlds;
}  

}

public ArrayList<City> getCity(String country, int startYear, int endYear, String regionType, String specificCity) {
    ArrayList<City> cities = new ArrayList<>();

    Connection connection = null;
    try { 
        connection = DriverManager.getConnection(DATABASE);
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30);
    
    String query = "SELECT *, " +
    "CASE " +
        "WHEN t1.AverageTemperature IS NOT NULL AND t2.AverageTemperature IS NOT NULL THEN t2.AverageTemperature - t1.AverageTemperature " +
        "WHEN t1.AverageTemperature IS NULL THEN t2.AverageTemperature " +
        "WHEN t2.AverageTemperature IS NULL THEN t1.AverageTemperature " +
    "END AS AverageTemperatureDiff, " +
    "CASE " +
        "WHEN t1.MinimumTemperature IS NOT NULL AND t2.MinimumTemperature IS NOT NULL THEN t2.MinimumTemperature - t1.MinimumTemperature " +
        "WHEN t1.MinimumTemperature IS NULL THEN t2.MinimumTemperature " +
        "WHEN t2.MinimumTemperature IS NULL THEN t1.MinimumTemperature " +
    "END AS MinimumTemperatureDiff, " +
    "CASE " +
        "WHEN t1.MaximumTemperature IS NOT NULL AND t2.MaximumTemperature IS NOT NULL THEN t2.MaximumTemperature - t1.MaximumTemperature " +
        "WHEN t1.MaximumTemperature IS NULL THEN t2.MaximumTemperature " +
        "WHEN t2.MaximumTemperature IS NULL THEN t1.MaximumTemperature " +
    "END AS MaximumTemperatureDiff, " +
    "RANK() OVER (ORDER BY (t2.AverageTemperature - t1.AverageTemperature) DESC) AS AverageTemperatureRank, " +
    "RANK() OVER (ORDER BY (t2.MinimumTemperature - t1.MinimumTemperature) DESC) AS MinimumTemperatureRank, " +
    "RANK() OVER (ORDER BY (t2.MaximumTemperature - t1.MaximumTemperature) DESC) AS MaximumTemperatureRank " +
"FROM " +
    "CityYearlyLandTemp t1 " +
"JOIN " +
    "CityYearlyLandTemp t2 ON t1.City = t2.City " +
"WHERE " +
    "t1.Country = '" + country + "' " +
    "AND t1.Year = " + startYear + " AND t2.Year = " + endYear;

             

        ResultSet results = statement.executeQuery(query);
     
    while (results.next()) {
                double averageTemperatureDiff = results.getDouble("AverageTemperatureDiff");
                double minimumTemperatureDiff = results.getDouble("MinimumTemperatureDiff");
                double maximumTemperatureDiff = results.getDouble("MaximumTemperatureDiff");
                String city = results.getString("City");
                String Country = results.getString("Country");
                String latitude = results.getString("Latitude");
                String longitude = results.getString("Longitude");
                int averageTemperatureRank = results.getInt("AverageTemperatureRank");
                int minimumTemperatureRank = results.getInt("MinimumTemperatureRank");
                int maximumTemperatureRank = results.getInt("MaximumTemperatureRank");
                City cityData = new City(averageTemperatureDiff, minimumTemperatureDiff, maximumTemperatureDiff, city, Country, latitude, longitude, averageTemperatureRank, minimumTemperatureRank, maximumTemperatureRank);
                cities.add(cityData);
                }
           statement.close();

           if (specificCity != null && !specificCity.isEmpty()){
               String query2 = "SELECT *, " +
        "CASE " +
            "WHEN t1.AverageTemperature IS NOT NULL AND t2.AverageTemperature IS NOT NULL THEN t2.AverageTemperature - t1.AverageTemperature " +
            "WHEN t1.AverageTemperature IS NULL THEN t2.AverageTemperature " +
            "WHEN t2.AverageTemperature IS NULL THEN t1.AverageTemperature " +
        "END AS AverageTemperatureDiff, " +
        "CASE " +
            "WHEN t1.MinimumTemperature IS NOT NULL AND t2.MinimumTemperature IS NOT NULL THEN t2.MinimumTemperature - t1.MinimumTemperature " +
            "WHEN t1.MinimumTemperature IS NULL THEN t2.MinimumTemperature " +
            "WHEN t2.MinimumTemperature IS NULL THEN t1.MinimumTemperature " +
        "END AS MinimumTemperatureDiff, " +
        "CASE " +
            "WHEN t1.MaximumTemperature IS NOT NULL AND t2.MaximumTemperature IS NOT NULL THEN t2.MaximumTemperature - t1.MaximumTemperature " +
            "WHEN t1.MaximumTemperature IS NULL THEN t2.MaximumTemperature " +
            "WHEN t2.MaximumTemperature IS NULL THEN t1.MaximumTemperature " +
        "END AS MaximumTemperatureDiff, " +
        "RANK() OVER (ORDER BY (t2.AverageTemperature - t1.AverageTemperature) DESC) AS AverageTemperatureRank, " +
        "RANK() OVER (ORDER BY (t2.MinimumTemperature - t1.MinimumTemperature) DESC) AS MinimumTemperatureRank, " +
        "RANK() OVER (ORDER BY (t2.MaximumTemperature - t1.MaximumTemperature) DESC) AS MaximumTemperatureRank " +
    "FROM " +
        "CityYearlyLandTemp t1 " +
    "JOIN " +
        "CityYearlyLandTemp t2 ON t1.City = t2.City " +
    "WHERE " +
        "t1.Country = '" + country + "' " +
        "AND t1.City = '" + specificCity + "' " +
        "AND t1.Year = " + startYear + " AND t2.Year = " + endYear;

           ResultSet results2 = statement.executeQuery(query2);

             while (results2.next()) {
                double averageTemperatureDiff2 = results.getDouble("AverageTemperatureDiff");
                double minimumTemperatureDiff2 = results.getDouble("MinimumTemperatureDiff");
                double maximumTemperatureDiff2 = results.getDouble("MaximumTemperatureDiff");
                String city2= results.getString("City");
                String Country2 = results.getString("Country");
                String latitude2 = results.getString("Latitude");
                String longitude2 = results.getString("Longitude");
                int averageTemperatureRank2 = results.getInt("AverageTemperatureRank");
                int minimumTemperatureRank2 = results.getInt("MinimumTemperatureRank");
                int maximumTemperatureRank2 = results.getInt("MaximumTemperatureRank");
                City cityData2 = new City(averageTemperatureDiff2, minimumTemperatureDiff2, maximumTemperatureDiff2, city2, Country2, latitude2, longitude2, averageTemperatureRank2, minimumTemperatureRank2, maximumTemperatureRank2);
                cities.add(cityData2);
              
            }
               statement.close();
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }  
   
return cities; 
}

 public ArrayList<State> getState(String country, int startYear, int endYear, String regionType, String specificState) {
    ArrayList<State> states = new ArrayList<>();

    Connection connection = null;
    try { 
        connection = DriverManager.getConnection(DATABASE);
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30);
    
    String query = "SELECT *, " +
    "CASE " +
        "WHEN t1.AverageTemperature IS NOT NULL AND t2.AverageTemperature IS NOT NULL THEN t2.AverageTemperature - t1.AverageTemperature " +
        "WHEN t1.AverageTemperature IS NULL THEN t2.AverageTemperature " +
        "WHEN t2.AverageTemperature IS NULL THEN t1.AverageTemperature " +
    "END AS AverageTemperatureDiff, " +
    "CASE " +
        "WHEN t1.MinimumTemperature IS NOT NULL AND t2.MinimumTemperature IS NOT NULL THEN t2.MinimumTemperature - t1.MinimumTemperature " +
        "WHEN t1.MinimumTemperature IS NULL THEN t2.MinimumTemperature " +
        "WHEN t2.MinimumTemperature IS NULL THEN t1.MinimumTemperature " +
    "END AS MinimumTemperatureDiff, " +
    "CASE " +
        "WHEN t1.MaximumTemperature IS NOT NULL AND t2.MaximumTemperature IS NOT NULL THEN t2.MaximumTemperature - t1.MaximumTemperature " +
        "WHEN t1.MaximumTemperature IS NULL THEN t2.MaximumTemperature " +
        "WHEN t2.MaximumTemperature IS NULL THEN t1.MaximumTemperature " +
    "END AS MaximumTemperatureDiff, " +
    "RANK() OVER (ORDER BY (t2.AverageTemperature - t1.AverageTemperature) DESC) AS AverageTemperatureRank, " +
    "RANK() OVER (ORDER BY (t2.MinimumTemperature - t1.MinimumTemperature) DESC) AS MinimumTemperatureRank, " +
    "RANK() OVER (ORDER BY (t2.MaximumTemperature - t1.MaximumTemperature) DESC) AS MaximumTemperatureRank " +
"FROM " +
    "StateYearlyLandTemp t1 " +
"JOIN " +
    "StateYearlyLandTemp t2 ON t1.State = t2.State " +
"WHERE " +
    "t1.Country = '" + country + "' " +
    "AND t1.Year = " + startYear + " AND t2.Year = " + endYear;

     

        ResultSet results = statement.executeQuery(query);
     

        while (results.next()) {
            double averageTemperature = results.getDouble("AverageTemperatureDiff");
            double minimumTemperature = results.getDouble("MinimumTemperatureDiff");
            double maximumTemperature = results.getDouble("MaximumTemperatureDiff");
            String state = results.getString("State");
            String Country = results.getString("Country");
            int averageTemperatureRank = results.getInt("AverageTemperatureRank");
            int minimumTemperatureRank = results.getInt("MinimumTemperatureRank");
            int maximumTemperatureRank = results.getInt("MaximumTemperatureRank");

            State stateData = new State(averageTemperature, minimumTemperature, maximumTemperature, state, Country, averageTemperatureRank, minimumTemperatureRank, maximumTemperatureRank);
            states.add(stateData);
          
        }
           statement.close();
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    } finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
return states; 
}


public ArrayList<TotalYears> getTotalYears() {
    ArrayList<TotalYears> years = new ArrayList<TotalYears>();
  
    // Setup the variable for the JDBC connection
    Connection connection = null;
  
    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);
  
        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
  
        // The Query
        String populationQuery = "SELECT COUNT(Year) AS PopulationYears FROM Date WHERE Year >= 1960 AND Year <= 2013;";
        String temperatureQuery = "SELECT COUNT(Year) AS TotalTempYears FROM Date WHERE Year >= 1750 AND Year <= 2015;";
  
        // Get Result
      ResultSet results = statement.executeQuery(populationQuery);


      // Process all of the results
      while (results.next()) {
        int populationTotalYears = results.getInt("PopulationYears");
       
    

        TotalYears populationYearData = new TotalYears(populationTotalYears, 266);

          years.add(populationYearData);
      }
  /* ResultSet results2 = statement.executeQuery(temperatureQuery);
      while (results2.next()) {
        int temperatureTotalYears = results2.getInt("TotalTempYears");
       
    

        TotalYears temperatureYearData = new TotalYears(0, temperatureTotalYears);

          years.add(temperatureYearData);
      } */

  
        // Close the statement because we are done with it
        statement.close();
    } catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    
    return years;
  }  
  
  }
}


package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>View By Country</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='page2A.css' />";
        html = html + "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>";


        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Home</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>View By Country/Global</a>
                <a href='page2B.html'>Temperature Change By City/State</a>
                <a href='page3A.html'>Temperature Changes Over Time</a>
                <a href='page3B.html'>Time Periods With Similarities</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
            <div class='header'>
            
                <h1>Country/Global Temperature/Population Changes</h1>
                 </div>
                
         
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <div class="breadcrumbs">
      <a href='/'>Home</a> > <a href="page2A.html">View By Country</a>
    </div>

    <br>

    <div class="description">
    <h2>Description:</h2> 
    <p>View Temperature and Population Changes for the world or for various countries for your choice of years between the year range of 1750-2013! To make observing the data easier, we have added additional and 
    optional sorting filters to sort out the data of various countries based on your sorting preferences! You can even enter in any chosen country of your choice to get data specific to your chosen country! 
    Note that entering a specific country name is optional as well and the sorting filters are best used only if there is no specific country entered. Make sure the 'countries' view option is selected if you
    are entering a specific country name and ensure each country is spelt correctly when entering your chosen country name.</p>
    </div> 
    <div class="filterheader">
    <h1>Select Filtering Options:</h1>
    </div> 
            <div id="form">
            <form action='page2A.html' method="post">
            
    <label id="view" for="view" required>View:</label>
    <div class="selectdropdown">
    <select id="view" name="view" class="dropdown">
     <option value="">--Please choose an option--</option>
      <option value="countries">Countries</option>
      <option value="world">World</option>
    </select>
    </div>
    <br>

     <label for='specificCity'>Enter A Specific Country Name (Optional):</label>
            <input type='text' id='specificCountry' name="specificCountry" placeholder="Enter A Specific Country Name">
           
            <br>

    <label id="startYear" for="startYear">Start Year:</label>
    <input type="number" id="startYear" name = "startYear" min="1750" max="2013" oninput="checkYear(this)" required>

    <br>

    <label id="endYear" for="endYear">End Year:</label>
    <input type="number" id="endYear" name = "endYear" min="1750" max="2013" oninput="checkYear(this)" required>

    <br>

    <label id = "sortBy" for="sortBy" required>Select A Sort By (Optional):</label>
    <select id="sortBy" name="sortBy">
     <option value="">--Please choose an option--</option>
      <option value="temperature">Temperature</option>
      <option value="population">Population</option>
    </select>

    <br>

    <label id = "sortOrder" for="sortOrder" required>Select An Order (Optional):</label>
    <select id="sortOrder" name = "sortOrder">
     <option value="">--Please choose an option--</option>
    <option value="Ascending">Ascending</option>
    <option value="Descending">Descending</option>
      </select>
      
    <br>

    <div class="search-button">
    <button type="submit" value="Submit">Search</button>
    </div>
    <br>

    <input type="button" class="reset-button" value="Reset" onclick="window.location.reload();">
  </form>
  
  </body>
<div class="disclaimer">
  <p><i>
  <strong>*Disclaimer:</strong> Population data is only available for the years 1960-2013 and some data may not be available for selected options.
                                       
        
  </i></p>
  </div>

<script>
    function checkYear(userInput) {
        var min = parseInt(userInput.min);
        var max = parseInt(userInput.max);
        var value = parseInt(userInput.value);

        if (value < min || value > max) {
            userInput.setCustomValidity("Please Enter A Valid Year Between " + min + " And " + max + ".");
        } else {
            userInput.setCustomValidity("");
        }
    }
</script>


     """;

           
   
   
        if (context.method().equalsIgnoreCase("post")) {   
        String view = context.formParam("view");
        String startYearString = context.formParam("startYear");
        String endYearString = context.formParam("endYear");
        String sortBy = context.formParam("sortBy");
        String sortOrder = context.formParam("sortOrder");
        String specificCountry = context.formParam("specificCountry"); 
        if (view.isEmpty() || startYearString.isEmpty() || endYearString.isEmpty()) {
              html = html + "<div class='missing'>";
            html = html + "<h2><i>There are missing field/s, please try again.</i></h2>";
                html = html + "</div>"; 

        }
        else if (view.equalsIgnoreCase("Countries") && specificCountry.isEmpty()){
            int startYear;
            int endYear;
                startYear = Integer.parseInt(startYearString);
                endYear = Integer.parseInt(endYearString);
            if (startYear < 1750 || startYear > 2013) {
                       html = html + "<div class='missing'>";
                html = html + "<h2><i>Please Enter A Valid Start Year.</i></h2>";
                html = html + "</div>"; 
        } 
         if (endYear < 1750 || endYear > 2013) {
             html = html + "<div class='missing'>";
           html = html + "<h2><i>Please Enter A Valid End Year.</i></h2>";
                html = html + "</div>"; 
        } 
                JDBCConnection jdbc = new JDBCConnection();
                ArrayList<Population> populations = jdbc.getPopulation(view, startYear, endYear, sortBy, sortOrder, specificCountry);
    
            view = capitalizeFirstLetter(view); 
            sortBy = capitalizeFirstLetter(sortBy);
            sortOrder = capitalizeFirstLetter(sortOrder);
            
        

            html += "<div id='user-input'>";
            html += "<h1>You Entered:</h1>";
            html += "<p>View: " + view + "</p>";
            html += "<p>Start Year: " + startYear + "</p>";
            html += "<p>End Year: " + endYear + "</p>";
            html += "<p>Sort By: " + sortBy + "</p>";
            html += "<p>Sort Order: " + sortOrder + "</p>";
            html += "</div> <br>";
            html = html + "<div class='countriestitle'>";
            html = html + "<h2>Countries Temperature And Population Changes:</h2>";
             html = html + "</div>";
            html += "<table>";
            html += "<tr><th>Country</th><th>Average Temperature Change</th><th>Average Temperature Percentage Change</th><th>Population Change</th><th>Population Percentage Change</th></tr>";
        
            for (int i = 0; i < populations.size(); i++) {
                Population population = populations.get(i);
                String country = population.getName();
                boolean duplicate = false;
        

                for (int j = 0; j < i; j++) {
                    Population previousPopulation = populations.get(j);
                    if (country.equals(previousPopulation.getName())) {
                        duplicate = true;
                        break;
                    }
                }
        
             
                if (!duplicate) {
                 html += "<tr>";
                html += "<td>" + country + "</td>";
                html += "<td>" + (population.getAverageTemperatureDiff() != 0 ? formatTemperatureChange(population.getAverageTemperatureDiff()) : "No Data Available") + "</td>";
                html += "<td>" + (population.getPercentageAverageTemperatureDiff() != 0 ? formatPercentageChange(population.getPercentageAverageTemperatureDiff()) : "No Data Available") + "</td>";
                html += "<td>" + (population.getPopulationDiff() != 0 ? formatPopulation(population.getPopulationDiff()) : "No Data Available") + "</td>";
               html += "<td>" + (population.getPercentagePopulationDiff() != 0 ? formatPercentageChange(population.getPercentagePopulationDiff()) : "No Data Available") + "</td>";
                html += "</tr>";

                }
            }
            html += "</table>";
        
        
          /*  
            html = html + "<h2>Countries Data:</h2>"; */

            if (populations.isEmpty()) {
                html = html + "<p>No data found for selected criteria.</p>";
            } /*  else {
                html = html + "<table>";
                html = html + "<tr><th>Country Name</th><th>Year</th><th>Average Temperature</th><th>Population</th></tr>";

                for (Population population : populations) {
                    html = html + "<tr>";
                    html = html + "<td>" + population.getName() + "</td>";
                    html = html + "<td>" + population.getYear() + "</td>";
                    html = html + "<td>" + population.getAverageTemperature() + "</td>";
                    html = html + "<td>" + population.getPopulation() + "</td>";
                    html = html + "</tr>";
                    
                }

              
         
            }
            html = html + "</table>"; */
        
        }
           if (!specificCountry.isEmpty() && specificCountry != null) {
                     if (view.equalsIgnoreCase("Countries")) {
             List<Population> filteredCountries = new ArrayList<>();
             int startYear;
            int endYear;
                startYear = Integer.parseInt(startYearString);
                endYear = Integer.parseInt(endYearString);
                  JDBCConnection jdbc = new JDBCConnection();
                ArrayList<Population> populations = jdbc.getPopulation(view, startYear, endYear, sortBy, sortOrder, specificCountry);
               view = capitalizeFirstLetter(view); 
            specificCountry = capitalizeFirstLetter(specificCountry); 
              if (startYear < 1750 || startYear > 2013) {
                            html = html + "<div class='startyear'>"; 
             html = html + "<h2><i>Please Enter A Valid Start Year.</i></h2>";
             html = html + "</div>"; 
        } 
         if (endYear < 1750 || endYear > 2013) {
                     html = html + "<div class='endyear'>"; 
             html = html + "<h2><i>Please Enter A Valid End Year.</i></h2>";
               html = html + "</div>"; 
        } 


                           html += "<div id='user-input'>";
            html += "<h1>You Entered:</h1>";
            html += "<p>View: " + view + "</p>";
            html += "<p>Specific Country: " + specificCountry + "</p>";
            html += "<p>Start Year: " + startYear + "</p>";
            html += "<p>End Year: " + endYear + "</p>";
            html += "</div> <br>";


                      if (getPopulation(view, startYear, endYear, sortBy, sortOrder, specificCountry) == null) {
                        html = html + "<div class='nodata'>";
                        html = html + "<p>No Data Found For Selected Criteria.</p>";
                        html = html + "</div>";
                    }
                    else {
                for (Population  population : populations) {
                        Set<String> uniqueCountries = new HashSet<>();
                if (population.getName().equalsIgnoreCase(specificCountry)) {
                    String countryKey = population.getName();
                    if (!uniqueCountries.contains(countryKey)) {
                        filteredCountries.add(population);
                        uniqueCountries.add(countryKey);
                    }
                }
               }
            }
         html = html + "<div class='countriestitle'>";
         html = html + "<h2>" + specificCountry + " Data:</h2>";
         html = html + "</div>";
             html = html + "<table>";
        html = html + "<tr><th>Country</th><th>Average Temperature Change</th><th>Average Temperature Percentage Change</th><th>Population Change</th><th>Population Percentage Change</th></tr>";

         for (Population population : filteredCountries) {
        html = html + "<tr>";
        html = html + "<td>" + population.getName() + "</td>";
        html += "<td>" + (population.getAverageTemperatureDiff() != 0 ? formatTemperatureChange(population.getAverageTemperatureDiff()) : "No Data Available") + "</td>";
        html += "<td>" + (population.getPercentageAverageTemperatureDiff() != 0 ? formatPercentageChange(population.getPercentageAverageTemperatureDiff()) : "No Data Available") + "</td>";
        html += "<td>" + (population.getPopulationDiff() != 0 ? formatPopulationChange(population.getPopulationDiff()) : "No Data Available") + "</td>";
         html += "<td>" + (population.getPercentagePopulationDiff() != 0 ? formatPercentageChange(population.getPercentagePopulationDiff()) : "No Data Available") + "</td>";
   
        html = html + "</tr>";
        html = html + "</table>";
    }
             
        } }

            if (view.equalsIgnoreCase("World")){
                if (view.isEmpty() || startYearString.isEmpty() || endYearString.isEmpty()) {
                    html = html + "<h2><i>There are missing field/s, please try again.</i></h2>";
                } else {
                view = capitalizeFirstLetter(view); 
            int startYearWorld = Integer.parseInt(startYearString);
            int endYearWorld = Integer.parseInt(endYearString);
                     if (startYearWorld < 1750 || startYearWorld > 2013) {
             html = html + "<h2><i>Please Enter A Valid Start Year.</i></h2>";
        } 
         if (endYearWorld < 1750 || endYearWorld > 2013) {
             html = html + "<h2><i>Please Enter A Valid End Year.</i></h2>";
        } 
            html += "<div id='user-input'>";
            html += "<h1>You Entered:</h1>";
            html += "<p>View: " + view + "</p>";
            html += "<p>Start Year: " + startYearWorld + "</p>";
            html += "<p>End Year: " + endYearWorld + "</p>";
            html += "</div> <br>";
        
        
                JDBCConnection jdbc = new JDBCConnection();

                ArrayList<Worlds> worlds = jdbc.getWorlds(view, startYearWorld, endYearWorld, sortBy, sortOrder);
                html = html + "<div class='worldtitle'>";
            html = html + "<h2>World Temperature And Population Changes:</h2>";
             html = html + "</div>";
                html += "<tr>";
                for (Worlds world : worlds) {
                    html = html + "<table>";
                    html = html + "<tr><th>Average Temperature Change</th><th>Average Temperature Percentage Change</th><th>Land Ocean Average Temperature Change</th><th>Land Ocean Average Temperature Percentage Change</th><th>Population Change</th><th>Population Percentage Change</th></tr>";
                    html += "<tr>";
                    html += "<td>" + (world.getWorldTemperatureDiff() != 0 ? formatTemperatureChange(world.getWorldTemperatureDiff()) : "No Data Available") + "</td>";
                    html += "<td>" + (world.getWorldAverageTemperaturePercentageDiff() != 0 ? formatPercentageChange(world.getWorldAverageTemperaturePercentageDiff()) : "No Data Available") + "</td>";
                     html += "<td>" + (world.getWorldLandAverageTemperatureDiff() != 0 ? formatTemperatureChange(world.getWorldLandAverageTemperatureDiff()) : "No Data Available") + "</td>";
                    html += "<td>" + (world.getWorldLandTemperaturePercentageDiff() != 0 ? formatPercentageChange(world.getWorldLandTemperaturePercentageDiff()) : "No Data Available") + "</td>";
                    html += "<td>" + (world.getWorldPopulationDiff() != 0 ? formatPopulationChange(world.getWorldPopulationDiff()) : "No Data Available") + "</td>";
                     html += "<td>" + (world.getPopulationPercentageDiff() != 0 ? formatPercentageChange(world.getPopulationPercentageDiff()) : "No Data Available") + "</td>";
                    html += "</tr>";
                    html += "</table>";
                }




            /*     ArrayList<World> world = jdbc.getWorld();
                html = html + "<h2>World Data:</h2>";
                for (World worlds : world) {
                    html = html + "<tr>";
                    html = html + "<td>" + worlds.getYear() + "</td>";
                    html = html + "<td>" + worlds.getAverageTemperature() + "</td>";
                    html = html + "<td>" + worlds.getMinimumTemperature() + "</td>";
                    html = html + "<td>" + worlds.getMaximumTemperature() + "</td>";
                    html = html + "<td>" + (worlds.getLandAverageTemperature() != 0 ? worlds.getLandAverageTemperature() : "0") + "</td>";
                    html = html + "<td>" + (worlds.getLandMinimumTemperature() != 0 ? worlds.getLandMinimumTemperature() : "0") + "</td>";
                    html = html + "<td>" + (worlds.getLandMaximumTemperature() != 0 ? worlds.getLandMaximumTemperature() : "0") + "</td>";
                    html = html + "</tr>";
                }
                html = html + "</table>"; */
                
            }
        }
}



        



        // Footer
        html = html + """
            <footer>
            <div class='footer'>
            <p class="footer-center">
            <a href='/'>Home</a>
            <a href='mission.html'>Our Mission</a>
            <a href='page2A.html'>View By Country</a>
            <a href='page2B.html'>Temperature Change By City/State</a>
            <a href='page3A.html'>Temperature Changes Over Time</a>
            <a href='page3B.html'>Time Periods With Similarities</a>
            <p class="copyright">&copy; 2023 Climate Change Analyser. All rights reserved.</p>
            </p>
            </footer>
            </div>
        """;


        // Finish the HTML webpage
        html = html + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);

        
    }
    
  private String capitalizeFirstLetter(String userInput) {
    if (userInput == null || userInput.isEmpty()) {
        return userInput;
    }
    
    String[] words = userInput.split(" ");
    StringBuilder output = new StringBuilder();
    
    for (String word : words) {
        if (!word.isEmpty()) {
            String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            output.append(capitalizedWord).append(" ");
        }
    }
    
    return output.toString().trim();
}
private String formatTemperatureChange(double temperatureChange) {
    if (temperatureChange > 0) {
        return "Increase Of " + String.format("%.2f", temperatureChange) + "&#176;C";
    } else if (temperatureChange < 0) {
        return "Decrease Of " + String.format("%.2f", Math.abs(temperatureChange)) + "&#176;C";
    } else {
        return "No Data Available";
    }
}

private String formatPercentageChange(double percentageChange) {
    if (percentageChange > 0) {
        return "Increase Of " + String.format("%.2f", percentageChange) + "%";
    } else if (percentageChange < 0) {
        return "Decrease Of " + String.format("%.2f", Math.abs(percentageChange)) + "%";
    } else {
        return "No Data Available";
    }
}

private String formatPopulationChange(long populationChange) {
    if (populationChange > 0) {
        return "Increase Of " + formatPopulation(populationChange);
    } else if (populationChange < 0) {
        return "Decrease Of " + formatPopulation(Math.abs(populationChange));
    } else {
        return "No change";
    }
}

    public static final String DATABASE = "jdbc:sqlite:database/climate.db";
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

    private String formatPopulation(long population) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(population);
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
                long WorldPopulationDiff = results.getInt("populationDiff");
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
}

package app;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import io.javalin.http.Context;
import io.javalin.http.Handler;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class PageST3A implements Handler {
    
 
    public static final String URL = "/page3A.html";
    private static final String DB_URL = "jdbc:sqlite:database/climate.db";
    private Context context;
 
    @Override
    public void handle(Context context) throws Exception {
        this.context = context;
 
        if (context.formParamMap().containsKey("searchButton")) {
            String[] locationTypes = context.formParams("locationType").toArray(String[]::new);
            String countries = context.formParam("country");
            String states = context.formParam("state");
            String cities = context.formParam("city");
            String timePeriod = context.formParam("timePeriod");
            String startYears = context.formParam("startYear");
 
            ArrayList<String> results = performDatabaseQueries(locationTypes, countries, states, cities, timePeriod, startYears);
 
            renderResultsPage(results);
        } else {
            renderFormPage();
        }
    }
 
    private ArrayList<String> performDatabaseQueries(String[] locationTypes, String countries, String states, String cities, String timePeriod, String startYears) {
        ArrayList<String> results = new ArrayList<>();
 
        if (startYears == null || startYears.isEmpty() || timePeriod == null || timePeriod.isEmpty()) {
            results.add("Please provide valid values for start years and time period.");
            return results;
        }
 
        try {
            int timePeriodValue = Integer.parseInt(timePeriod);
            String[] startYearArr = startYears.split(",");
 
            for (String locationType : locationTypes) {
                List<String> locationList = getLocationList(locationType, countries, states, cities);
                if (locationList.isEmpty()) {
                    continue;
                }
 
                for (String startYear : startYearArr) {
                    String query = "SELECT Year, ";
                    if (locationType.equals("country")) {
                        query += "Country";
                    } else if (locationType.equals("state")) {
                        query += "State";
                    } else if (locationType.equals("city")) {
                        query += "City";
                    }
                    query += ", AVG(AverageTemperature) FROM ";
 
                    query += getTableByLocationType(locationType) + " WHERE ";
                    query += getConditionByLocationType(locationType, locationList);
                    query += " AND Year >= " + startYear + " AND Year <= " + (Integer.parseInt(startYear) + timePeriodValue - 1) + " GROUP BY Year, ";
                    if (locationType.equals("country")) {
                        query += "Country";
                    } else if (locationType.equals("state")) {
                        query += "State";
                    } else if (locationType.equals("city")) {
                        query += "City";
                    }
 
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            int year = rs.getInt(1);
                            String location = rs.getString(2);
                            double averageTemperature = rs.getDouble(3);
                            results.add("Location Type: " + locationType + ", Location: " + location + ", Start Year: " + startYear + ", Year: " + year + ", Average Temperature: " + averageTemperature);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        results.add("An error occurred while performing the database query.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            results.add("Invalid numeric input for start years or time period.");
        }
 
        return results;
    }
 
    private List<String> getLocationList(String locationType, String countries, String states, String cities) {
        List<String> locationList = new ArrayList<>();
        if (locationType.equals("country")) {
            locationList.addAll(Arrays.asList(countries.split(",")));
        } else if (locationType.equals("state")) {
            locationList.addAll(Arrays.asList(states.split(",")));
        } else if (locationType.equals("city")) {
            locationList.addAll(Arrays.asList(cities.split(",")));
        }
        return locationList;
    }
 
    private String getTableByLocationType(String locationType) {
        if (locationType.equals("country")) {
            return "CountryYearlyLandTemp";
        } else if (locationType.equals("state")) {
            return "StateYearlyLandTemp";
        } else if (locationType.equals("city")) {
            return "CityYearlyLandTemp";
        }
        return "";
    }
 
    private String getConditionByLocationType(String locationType, List<String> locationList) {
        StringBuilder condition = new StringBuilder();
        if (locationType.equals("country")) {
            condition.append("Country IN (");
        } else if (locationType.equals("state")) {
            condition.append("State IN (");
        } else if (locationType.equals("city")) {
            condition.append("City IN (");
        }
        for (int i = 0; i < locationList.size(); i++) {
            condition.append("'").append(locationList.get(i)).append("'");
            if (i < locationList.size() - 1) {
                condition.append(", ");
            }
        }
        condition.append(")");
        return condition.toString();
    }
 
    private void renderFormPage() {
        // Create a simple HTML webpage in a String
        String html = "<html>";
 
        // Add some Head information
        html += "<head>";
        html += "<title>Temperature Changes Over Time</title>";
 
        // Add some CSS (external file)
        html += "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<link rel='stylesheet' type='text/css' href='PageST3A.css' />";
        html += "</head>";
 
        // Add the body
        html += "<body>";
 
        // Add the topnav
        html += """
                <div class='topnav'>
                    <a href='/'>Homepage</a>
                    <a href='mission.html'>Our Mission</a>
                    <a href='page2A.html'>View By Country/Global</a>
                    <a href='page2B.html'>Temperature Change By City/State</a>
                    <a href='page3A.html'>Temperature Changes Over Time</a>
                    <a href='page3B.html'>Time Periods With Similarities</a>
                </div>
                """;
 
        // Add header content block
        html += """
                <div class='header'>
                    <h1>Temperature Changes Over Time</h1>
                </div>
                """;
 
        // Add Div for page Content
        html += "<div class='content'>";
 
        // Add HTML for the form
        html += """
                <form method='POST' action='/page3A.html'>
                    <label for='locationType'>Select the Geographic Location Type:</label>
                    <input type='checkbox' id='country' name='locationType' value='country'>
                    <label for='country'>Country:</label>
                    <input type='text' id='country' name='country' placeholder='Type country names, separated by commas'>
                    <input type='checkbox' id='state' name='locationType' value='state'>
                    <label for='state'>State:</label>
                    <input type='text' id='state' name='state' placeholder='Type state names, separated by commas'>
                    <input type='checkbox' id='city' name='locationType' value='city'>
                    <label for='city'>City:</label>
                    <input type='text' id='city' name='city' placeholder='Type city names, separated by commas'>
                    <label for='timePeriod'>Time Period (in years):</label>
                    <input type='text' id='timePeriod' name='timePeriod' placeholder='Enter a time period'>
                    <label for='startYear'>Start Year(s):</label>
                    <input type='text' id='startYear' name='startYear' placeholder='Enter start year(s), separated by commas'>
                    <button type='submit' name='searchButton' id='searchButton'>Search</button>
                </form>
                """;
 
        // Close Content div
        html += "</div>";
 
        // Footer
        html += """
                <footer>
                    <div class='footer'>
                        <p class="footer-center">
                            <a href='/'>Home</a>
                            <a href='mission.html'>Our Mission</a>
                            <a href='page2A.html'>View By Country/Global</a>
                            <a href='page2B.html'>Temperature Change By City/State</a>
                            <a href='page3A.html'>Temperature Changes Over Time</a>
                            <a href='page3B.html'>Time Periods With Similarities</a>
                        </p>
                    </div>
                </footer>
                </div>
                """;
 
        // Finish the HTML webpage
        html += "</body>";
        html += "</html>";
 
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
 
    private void renderResultsPage(ArrayList<String> results) {
        // Create a simple HTML webpage in a String
        String html = "<html>";
 
        // Add some Head information
        html += "<head>";
        html += "<title>Search Results</title>";
 
        // Add some CSS (external file)
        html += "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<link rel='stylesheet' type='text/css' href='PageST3A.css' />";
        html += "</head>";
 
        // Add the body
        html += "<body>";
 
        // Add the topnav
        html += """
                <div class='topnav'>
                    <a href='/'>Homepage</a>
                    <a href='mission.html'>Our Mission</a>
                    <a href='page2A.html'>View By Country/Global</a>
                    <a href='page2B.html'>Temperature Change By City/State</a>
                    <a href='page3A.html'>Temperature Changes Over Time</a>
                    <a href='page3B.html'>Time Periods With Similarities</a>
                </div>
                """;
 
        // Add header content block
        html += """
                <div class='header'>
                    <h1>Search Results</h1>
                </div>
                """;
 
        // Add Div for page Content
        html += "<div class='content'>";
 
        // Add HTML for the search results table
        html += "<h2>Search Results:</h2>";
        if (!results.isEmpty()) {
            html += "<table>";
            html += "<tr><th>Location Type</th><th>Location</th><th>Start Year</th><th>Year</th><th>Average Temperature</th></tr>";
            for (String result : results) {
                // Split the result string into individual values
                String[] values = result.split(", ", 5);
 
                // Extract the location list from the result
                String[] locations = values[1].split(", ");
                // Extract the start years from the result
                String[] startYears = values[2].split(", ");
                // Extract the average temperatures from the result
                String[] avgTemperatures = values[4].split(", ");
 
                // Iterate over each combination of location, start year, and average temperature
                for (int i = 0; i < locations.length; i++) {
                    html += "<tr>";
                    html += "<td>" + values[0] + "</td>";  // Location Type
                    html += "<td>" + locations[i] + "</td>";  // Location
                    html += "<td>" + startYears[i] + "</td>";  // Start Year
                    html += "<td>" + values[3] + "</td>";  // Year
                    html += "<td>" + avgTemperatures[i] + "</td>";  // Average Temperature
                    html += "</tr>";
                }
            }
            html += "</table>";
        } else {
            html += "<p>No results found.</p>";
        }
 
        // Close Content div
        html += "</div>";
 
        // Footer
        html += """
                <footer>
                    <div class='footer'>
                        <p class="footer-center">
                            <a href='/'>Home</a>
                            <a href='mission.html'>Our Mission</a>
                            <a href='page2A.html'>View By Country/Global</a>
                            <a href='page2B.html'>Temperature Change By City/State</a>
                            <a href='page3A.html'>Temperature Changes Over Time</a>
                            <a href='page3B.html'>Time Periods With Similarities</a>
                              <p class="copyright">&copy; 2023 Climate Change Analyser. All rights reserved.</p>
            </p>
                        </p>
                    </div>
                </footer>
                </div>
                """;
 
        // Finish the HTML webpage
        html += "</body>";
        html += "</html>";
 
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
}
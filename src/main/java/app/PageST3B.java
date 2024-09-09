package app;
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
import io.javalin.http.Context;
import io.javalin.http.Handler;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class PageST3B implements Handler {
 
    public static final String URL = "/page3B.html";
    private static final String DB_URL = "jdbc:sqlite:database/climate.db";
    private Context context;
    private int numResultsInput;
 
    @Override
    public void handle(Context context) throws Exception {
        this.context = context;
 
        if (context.formParamMap().containsKey("searchButton")) {
            String country = context.formParam("country");
            String state = context.formParam("state");
            String city = context.formParam("city");
            String startYear = context.formParam("startYear");
            String timePeriod = context.formParam("timePeriod");
            String similarityMeasure = context.formParam("similarityMeasure");
            String numResults = context.formParam("numResults");
 
            try {
                numResultsInput = Integer.parseInt(numResults);
                ArrayList<String> results = performDatabaseQueries(country, state, city, startYear, timePeriod, similarityMeasure);
                renderResultsPage(results);
            } catch (NumberFormatException e) {
                renderErrorPage("Invalid number of results.");
            }
        } else {
            renderFormPage();
        }
    }
 
    private ArrayList<String> performDatabaseQueries(String country, String state, String city, String startYear, String timePeriod, String similarityMeasure) {
        ArrayList<String> results = new ArrayList<>();
 
        if (startYear == null || startYear.isEmpty() || timePeriod == null || timePeriod.isEmpty()) {
            results.add("Please provide valid values for start year and time period.");
            return results;
        }
 
        try {
            int startYearValue = Integer.parseInt(startYear);
            int timePeriodValue = Integer.parseInt(timePeriod);
 
            if (country != null && !country.isEmpty()) {
                List<String> countryResults = getSimilarCountries(country, startYearValue, timePeriodValue, similarityMeasure);
                results.addAll(countryResults);
            } else if (state != null && !state.isEmpty()) {
                List<String> stateResults = getSimilarStates(state, startYearValue, timePeriodValue, similarityMeasure);
                results.addAll(stateResults);
            } else if (city != null && !city.isEmpty()) {
                List<String> cityResults = getSimilarCities(city, startYearValue, timePeriodValue, similarityMeasure);
                results.addAll(cityResults);
            }
 
        } catch (NumberFormatException e) {
            results.add("Invalid numeric input for start year or time period.");
        }
 
        return results;
    }
 
    private List<String> getSimilarCountries(String country, int startYear, int timePeriod, String similarityMeasure) {
        List<String> results = new ArrayList<>();
 
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
 
            String query = "SELECT DISTINCT Country FROM CountryYearlyLandTemp";
            ResultSet rs = stmt.executeQuery(query);
            List<String> countries = new ArrayList<>();
            while (rs.next()) {
                String countryName = rs.getString("Country");
                countries.add(countryName);
            }
 
            List<String> filteredCountries = countries.stream()
                    .filter(c -> !c.equalsIgnoreCase(country))
                    .collect(Collectors.toList());
 
            for (String c : filteredCountries) {
                double similarityScore = calculateSimilarityScore(country, c, startYear, timePeriod, similarityMeasure);
                results.add("Country: " + c + ", Similarity Score: " + similarityScore);
            }
 
            results.sort((result1, result2) -> {
                double score1 = Double.parseDouble(result1.split(", Similarity Score: ")[1]);
                double score2 = Double.parseDouble(result2.split(", Similarity Score: ")[1]);
                return Double.compare(score2, score1);
            });
 
            if (numResultsInput > 0 && numResultsInput <= results.size()) {
                results = results.subList(0, numResultsInput);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
            results.add("An error occurred while performing the database query.");
        }
 
        return results;
    }
 
    private List<String> getSimilarStates(String state, int startYear, int timePeriod, String similarityMeasure) {
        List<String> results = new ArrayList<>();
 
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
 
            String query = "SELECT DISTINCT State FROM StateYearlyLandTemp WHERE Country = '" + state + "'";
            ResultSet rs = stmt.executeQuery(query);
            List<String> states = new ArrayList<>();
            while (rs.next()) {
                String stateName = rs.getString("State");
                states.add(stateName);
            }
 
            List<String> filteredStates = states.stream()
                    .filter(s -> !s.equalsIgnoreCase(state))
                    .collect(Collectors.toList());
 
            for (String s : filteredStates) {
                double similarityScore = calculateSimilarityScore(state, s, startYear, timePeriod, similarityMeasure);
                results.add("State: " + s + ", Similarity Score: " + similarityScore);
            }
 
            results.sort((result1, result2) -> {
                double score1 = Double.parseDouble(result1.split(", Similarity Score: ")[1]);
                double score2 = Double.parseDouble(result2.split(", Similarity Score: ")[1]);
                return Double.compare(score2, score1);
            });
 
            if (numResultsInput > 0 && numResultsInput <= results.size()) {
                results = results.subList(0, numResultsInput);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
            results.add("An error occurred while performing the database query.");
        }
 
        return results;
    }
 
    private List<String> getSimilarCities(String city, int startYear, int timePeriod, String similarityMeasure) {
        List<String> results = new ArrayList<>();
 
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
 
            String query = "SELECT DISTINCT City FROM CityYearlyLandTemp WHERE City = '" + city + "'";
            ResultSet rs = stmt.executeQuery(query);
            List<String> cities = new ArrayList<>();
            while (rs.next()) {
                String cityName = rs.getString("City");
                cities.add(cityName);
            }
 
            List<String> filteredCities = cities.stream()
                    .filter(c -> !c.equalsIgnoreCase(city))
                    .collect(Collectors.toList());
 
            for (String c : filteredCities) {
                double similarityScore = calculateSimilarityScore(city, c, startYear, timePeriod, similarityMeasure);
                results.add("City: " + c + ", Similarity Score: " + similarityScore);
            }
 
            results.sort((result1, result2) -> {
                double score1 = Double.parseDouble(result1.split(", Similarity Score: ")[1]);
                double score2 = Double.parseDouble(result2.split(", Similarity Score: ")[1]);
                return Double.compare(score2, score1);
            });
 
            if (numResultsInput > 0 && numResultsInput <= results.size()) {
                results = results.subList(0, numResultsInput);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
            results.add("An error occurred while performing the database query.");
        }
 
        return results;
    }
 
    private double calculateSimilarityScore(String region1, String region2, int startYear, int timePeriod, String similarityMeasure) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
 
            String query1 = "SELECT AverageTemperature FROM CountryYearlyLandTemp WHERE Country = '" + region1 + "' AND Year >= " + startYear + " AND Year <= " + (startYear + timePeriod - 1);
            ResultSet rs1 = stmt.executeQuery(query1);
            List<Double> values1 = new ArrayList<>();
            while (rs1.next()) {
                double value = rs1.getDouble("AverageTemperature");
                values1.add(value);
            }
 
            String query2 = "SELECT AverageTemperature FROM CountryYearlyLandTemp WHERE Country = '" + region2 + "' AND Year >= " + startYear + " AND Year <= " + (startYear + timePeriod - 1);
            ResultSet rs2 = stmt.executeQuery(query2);
            List<Double> values2 = new ArrayList<>();
            while (rs2.next()) {
                double value = rs2.getDouble("AverageTemperature");
                values2.add(value);
            }
 
            if (similarityMeasure.equalsIgnoreCase("absolute")) {
                return calculateAbsoluteSimilarityScore(values1, values2);
            } else if (similarityMeasure.equalsIgnoreCase("relative")) {
                return calculateRelativeSimilarityScore(values1, values2);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return 0.0;
    }
 
    private double calculateAbsoluteSimilarityScore(List<Double> values1, List<Double> values2) {
        if (values1.size() != values2.size()) {
            return 0.0;
        }
 
        double sumOfDifferences = 0.0;
        for (int i = 0; i < values1.size(); i++) {
            double difference = Math.abs(values1.get(i) - values2.get(i));
            sumOfDifferences += difference;
        }
 
        return 1.0 / (1.0 + sumOfDifferences);
    }
 
    private double calculateRelativeSimilarityScore(List<Double> values1, List<Double> values2) {
        if (values1.size() != values2.size()) {
            return 0.0;
        }
 
        double sumOfRelativeDifferences = 0.0;
        for (int i = 0; i < values1.size() - 1; i++) {
            double relativeDifference = Math.abs((values1.get(i + 1) - values1.get(i)) - (values2.get(i + 1) - values2.get(i)));
            sumOfRelativeDifferences += relativeDifference;
        }
 
        return 1.0 / (1.0 + sumOfRelativeDifferences);
    }
 
    private void renderFormPage() {
        // Create a simple HTML webpage in a String
        String html = "<html>";
 
        // Add some Head information
        html += "<head>";
        html += "<title>Time Periods With Similarities</title>";
 
        // Add some CSS (external file)
        html += "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<link rel='stylesheet' type='text/css' href='PageST3B.css' />";
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
                    <h1>Time Periods With Similarities</h1>
                </div>
                """;
 
        // Add Div for page Content
        html += "<div class='content'>";
 
        // Add HTML for the form
        html += """
                <form method='POST' action='/page3B.html'>
                    <label for='country'>Country:</label>
                    <input type='text' id='country' name='country' placeholder='Enter a country'>
                    <label for='state'>State:</label>
                    <input type='text' id='state' name='state' placeholder='Enter a state'>
                    <label for='city'>City:</label>
                    <input type='text' id='city' name='city' placeholder='Enter a city'>
                    <label for='startYear'>Start Year:</label>
                    <input type='text' id='startYear' name='startYear' placeholder='Enter start year'>
                    <label for='timePeriod'>Time Period (in years):</label>
                    <input type='text' id='timePeriod' name='timePeriod' placeholder='Enter time period'>
                    <label for='similarityMeasure'>Similarity Measure:</label>
                    <select id='similarityMeasure' name='similarityMeasure'>
                        <option value='absolute'>Absolute Values</option>
                        <option value='relative'>Relative Change</option>
                    </select>
                    <label for='numResults'>Number of Results:</label>
                    <input type='text' id='numResults' name='numResults' placeholder='Enter number of results'>
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
        html += "<link rel='stylesheet' type='text/css' href='PageST3B.css' />";
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
 
        // Check if there are any results
        if (results.isEmpty()) {
            html += "<p>No results found.</p>";
        } else {
            // Add the results
            html += "<h2>Similar Regions:</h2>";
            html += "<ol>";
            for (String result : results) {
                html += "<li>" + result + "</li>";
            }
            html += "</ol>";
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
 
    private void renderErrorPage(String errorMessage) {
        // Create a simple HTML webpage in a String
        String html = "<html>";
 
        // Add some Head information
        html += "<head>";
        html += "<title>Error</title>";
 
        // Add some CSS (external file)
        html += "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<link rel='stylesheet' type='text/css' href='PageST3B.css' />";
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
                    <h1>Error</h1>
                </div>
                """;
 
        // Add Div for page Content
        html += "<div class='content'>";
 
        // Add the error message
        html += "<p>" + errorMessage + "</p>";
 
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

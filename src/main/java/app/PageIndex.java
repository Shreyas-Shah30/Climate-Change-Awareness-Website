package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Home</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='homepage.css' />";
        html = html + "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Trirong'>"; 
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
     /*    html = html + """
            <div class='header'>
                <h1>
                    We Must Act Now On Climate Change!
                </h1>
            </div>
        """; */

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <body>
            <main>
         

        <div class="headerimage">
          <header class="full-image-wrap">
    <h1>Welcome to the Climate Change Analyser!</h1>
    <img class="full-image" src="homepagebackground.png" alt="">
  </header>
        </div>

        <div class="factsheader">
        <h2>Climate Change Facts</h2>
        </div>

        <div class="facts">
            <p>1. Since the pre-industrial era, the Earth's average temperature has risen by about 1 degree Celsius, mostly because of human activity.</p>
        </div>
    
        <div class="facts">
            <p>2. Because of the use of fossil fuels, atmospheric carbon dioxide (CO2) levels are at their highest point in the last 800,000 years.</p>
        </div>
    
        <div class="facts">
            <p>3. The rate of sea level rise caused by the melting of the polar ice caps, which is worrying, is about 3.3 millimetres per year.</p>
        </div>

        <div class="facts">
        <p>4. Fossil fuel combustion continues to be the principal cause of glasshouse gas emissions, which are what primarily cause climate change.</p>
    </div>


    <div class="facts">
    <p>5. The acidity of the ocean has increased by close to 30%, predominantly caused by the absorption of too much CO2, representing a significant danger to marine ecosystems and biodiversity.</p>
  </div>
   
""";
html = html + "<form action='/' method='post'>";
  html = html + "<div class='worldheader'>";
  html = html + "<h2>Display World Data</h2>"; 
  html = html + "</div>"; 
html = html + "<input type='submit' id = 'world-button' value='Show World Data'>";
html = html + "</form>";
html = html + "<input type='button' value='Reset' onclick='window.location.reload();'>";

    
if (context.method().equalsIgnoreCase("post")) {   
    html = html + "<table>";
    html = html + "<tr><th>Year</th><th>Average Temperature</th><th>Minimum Temperature</th><th>Maximum Temperature</th><th>LandOceanAverageTemperature</th><th>LandOceanMinimumTemperature</th><th>LandOceanMaximumTemperature</th><th>World Population</th></tr>";
    JDBCConnection jdbc = new JDBCConnection();
    ArrayList<World> world = jdbc.getWorld();
    
    for (World worlds : world) {
        html = html + "<tr>";
        html = html + "<td>" + worlds.getYear() + "</td>";
   html += "<td>" + (worlds.getAverageTemperature() != 0 ? String.format("%.2f", worlds.getAverageTemperature()) + "&#176" + "C" : "No Data Available") + "</td>";
        html += "<td>" + (worlds.getMinimumTemperature() != 0 ? String.format("%.2f", worlds.getMinimumTemperature()) + "&#176" + "C" : "No Data Available") + "</td>";
          html += "<td>" + (worlds.getMaximumTemperature() != 0 ? String.format("%.2f", worlds.getMaximumTemperature()) + "&#176" + "C" : "No Data Available") + "</td>";
          html += "<td>" + (worlds.getLandAverageTemperature() != 0 ? String.format("%.2f", worlds.getLandAverageTemperature()) + "&#176" + "C" : "No Data Available") + "</td>";
         html += "<td>" + (worlds.getLandMinimumTemperature() != 0 ? String.format("%.2f", worlds.getLandMinimumTemperature()) + "&#176" + "C" : "No Data Available") + "</td>";
      html += "<td>" + (worlds.getLandMaximumTemperature() != 0 ? String.format("%.2f", worlds.getLandMaximumTemperature()) + "&#176" + "C" : "No Data Available") + "</td>";
       html += "<td>" + (worlds.getWorldPopulation() != 0 ? formatPopulation(worlds.getWorldPopulation()) : "No Data Available") + "</td>";

        html = html + "</tr>";
    }
  }

  html = html + "<div class='globalheader'>";
  html = html + "<h2>Total Global Years Available</h2>"; 
  html = html + "</div>"; 
  html = html + "<table>";
  html = html + "<tr><th>Total of Global Population Years Data Available</th><th>Total of Global Temperature Years Data Available</th>";
  JDBCConnection jdbc = new JDBCConnection();
  ArrayList<TotalYears> years = jdbc.getTotalYears();
  for (TotalYears year : years) {
      html = html + "<tr>";
      html = html + "<td>" + year.getPopulationTotalYears() + "</td>";
      html = html + "<td>" + year.getTemperatureTotalYears() + "</td>";
      html = html + "</tr>";
  }

  html = html + "</table>";
  
    html = html + """
        <section id="climateimages">
  <div class="climatechangeimages">
    <img src="climateimage.png" alt="Climate Change Image 1">
  </div>
  <div class="climatechangeimages">
    <img src="climateimage2.png" alt="Climate Change Image 2">
  </div>
  <div class="climatechangeimages">
    <img src="carousel3.png" alt="Climate Change Image 3">
  </div>
</section>
<p class="climatechangecaption">Some of the devastating consequences of Climate Change.</p>

<br>

<section id="visual">
<div class="visual-content">
  <h2>Explore Key Climate Change Patterns</h2>
  <p>Discover and analyse temperature and population changes over a 260-year period.</p>
  <a href="mission.html" class="getstarted">Get Started &rarr;</a>
</div>
</section>

<div class="container">
    <div class="box">
      <h2>About</h2>
      <p>Our web product provides a detailed analysis of temperature and population patterns over a 260-year period. 
      It offers various statistics and calculated information about observed temperatures and populations from across 
      different countries, which will allow you to identify trends across various geographic locations such as cities and states 
      as well. Further to this, you can view the 'Our Mission' page to get a detailed outlook on how this web product functions and who this webpage
      is catered for specifically while also receiving a bit of information about the developers of the site and in particular, our goals and ambitions
      with this product.
       Our product can also cater to all sorts of users, whether you are intrigued in high-level concepts, analysis of data or simply want to be informed about
       the various changes that climate change has caused through viewing certain population and temperature patterns for many geographic regions.</p>
       <p><b>FOR MORE INFORMATION AND HELP VISIT THE 'OUR MISSION' PAGE.</b></p>
    </div>
    <div class="box cta">
      <h2>Call To Action</h2>
      <p>Together, we must combat climate change and create a liveable future for the future generations of our planet!</p>
      <div class="climatechangecalltoaction">
      <img src="climatecalltoaction.png" alt="Climate Change Call To Action">
    </div>
    </div>
    <div class="box feedback">
      <h2>Feedback</h2>
      <form method="get">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="message">Your Message:</label>
        <textarea id="message" name="message" rows="8" required></textarea><br>

        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
          </main>
            """;

        // Close Content div
        html = html + "</div>";

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
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
    private String formatPopulation(long population) {
      DecimalFormat decimalFormat = new DecimalFormat("#,###");
      return decimalFormat.format(population);
  }
    

    public static final String DATABASE = "jdbc:sqlite:database/climate.db";
   
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
 /*   ResultSet results2 = statement.executeQuery(temperatureQuery);
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

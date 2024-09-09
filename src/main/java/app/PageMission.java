package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";
        html = html + "<link rel='stylesheet' type='text/css' href='PageMission.css' />";
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
            <div class='header'>
                <h1>Our Mission</h1>
            </div>
        """;

       // Add Div for page Content
       html = html + "<div class='content'>";

       // Add HTML for the page content
       html = html + """
           
    
    <div id="logo">
      <img src="https://storage.googleapis.com/publiclab-production/public/system/images/photos/000/045/624/original/ddr7eyk-2bf5dbb5-2425-4a04-8dc2-1b0207f3eb36.jpg">
    </div>
    
    <div class="text">
      <h1>Present your perspective on how your website addresses the social challenge</h1>
      <p>Our website serves as a dedicated platform to tackle the social challenge of climate change by raising awareness and providing users with convenient access to valuable and reliable information. With a focus on inclusivity, our website aims to cater to users of all demographics, ensuring that they can easily obtain insightful information about climate change. Additionally, we strive to keep users up to date with the latest developments in the climate change movement.</p>
    </div>
  </div>

  <div class="text">
    <h1>Describe how your site can be used</h1>
    <p>Our site can be used as a platform to view and access accurate information regarding climate change. The site offers a range of tools and knowledge to aid individuals in gaining insight into climate change. The site features a tool to view temperature and population changes of a selected country or global data available from 1750 to 2013. Furthermore, it provides an opportunity to view temperature changes by city or state. The site also offers more intensive features to enable a deep dive into the challenge of climate change, specifically a tool to observe temperature and population changes over a desired period of time. This site aims to assist climate change enthusiasts of all levels in promoting awareness and understanding of the issue.</p>
  </div>
  
 
        
        
    
 
                """;
        // List the SID, StudentName, and StudentEmail from the Student1 table
html += "<div class='text'>";
html += "<h1>Student1 Data</h1>";
html += "<table>";
html += "<tr><th>SID</th><th>Student Name</th><th>Student Email</th></tr>";

try {
    // Establish a connection to the SQLite database
    Connection connection = DriverManager.getConnection("jdbc:sqlite:database/climate.db");

    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT SID, StudentName, StudentEmail FROM Student1 ");

    while (resultSet.next()) {
        String sid = resultSet.getString("SID");
        String studentName = resultSet.getString("StudentName");
        String studentEmail = resultSet.getString("StudentEmail");

        html += "<tr><td>" + sid + "</td><td>" + studentName + "</td><td>" + studentEmail + "</td></tr>";
    }

    resultSet.close();
    statement.close();
    connection.close();
} catch (SQLException e) {
    e.printStackTrace();
}

html += "</table>";
html += "</div>";


        // List the SID, StudentName, and StudentEmail from the Student2 table
        html += "<div class='text'>";
        html += "<h1>Student2 Data</h1>";
        html += "<table>";
        html += "<tr><th>SID</th><th>Student Name</th><th>Student Email</th></tr>";

        try {
            // Establish a connection to the SQLite database
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database/climate.db");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SID, StudentName, StudentEmail FROM Student2");

            while (resultSet.next()) {
                String sid = resultSet.getString("SID");
                String studentName = resultSet.getString("StudentName");
                String studentEmail = resultSet.getString("StudentEmail");

                html += "<tr><td>" + sid + "</td><td>" + studentName + "</td><td>" + studentEmail + "</td></tr>";
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        html += "</table>";
        html += "</div>";

// Establish a connection to the SQLite database
    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database/climate.db")) {
        // Retrieve data from Persona1 table
        html += "<div class='text'>";
        html += "<h1>Persona1 Data</h1>";
        html += "<table>";
        html += "<tr><th>Name</th><th>PersonaBackground</th><th>PersonaRequirements</th><th>PersonaSkills</th></tr>";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Persona1")) {

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String personaBackground = resultSet.getString("PersonaBackground");
                String personaRequirements = resultSet.getString("PersonaRequirements");
                String personaSkills = resultSet.getString("PersonaSkills");

                html += "<tr><td>" + name + "</td><td>" + "</td><td>" + personaBackground + "</td><td>" + personaRequirements + "</td><td>" + personaSkills + "</td></tr>";
      
            }
        }
           html = html + """
               <img src="persona1image.png" alt="Persona Image 1">
           """;
               
              

        html += "</table>";
        html += "</div>";

        // Retrieve data from Persona2 table
        html += "<div class='text'>";
        html += "<h1>Persona2 Data</h1>";
        html += "<table>";
        html += "<tr><th>Name</th><th>PersonaBackground</th><th>PersonaRequirements</th><th>PersonaSkills</th></tr>";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Persona2")) {

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String personaBackground = resultSet.getString("PersonaBackground");
                String personaRequirements = resultSet.getString("PersonaRequirements");
                String personaSkills = resultSet.getString("PersonaSkills");

                html += "<tr><td>" + name + "</td><td>" + "</td><td>" + personaBackground + "</td><td>" + personaRequirements + "</td><td>" + personaSkills + "</td></tr>";
            }
        }
html = html + """
               <img src="persona2image.png" alt="Persona Image 2">
           """;
        html += "</table>";
        html += "</div>";
        

        // Retrieve data from Persona3 table
        html += "<div class='text'>";
        html += "<h1>Persona3 Data</h1>";
        html += "<table>";
        html += "<tr><th>Name</th><th>PersonaBackground</th><th>PersonaRequirements</th><th>PersonaSkills</th></tr>";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Persona3")) {

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String personaBackground = resultSet.getString("PersonaBackground");
                String personaRequirements = resultSet.getString("PersonaRequirements");
                String personaSkills = resultSet.getString("PersonaSkills");

                html += "<tr><td>" + name + "</td><td>" + "</td><td>" + personaBackground + "</td><td>" + personaRequirements + "</td><td>" + personaSkills + "</td></tr>";
            }
        }
  html = html + """
               <img src="persona3image.png" alt="Persona Image 3">
           """;
        html += "</table>";
        html += "</div>";

      
    } catch (SQLException e) {
        e.printStackTrace();
    }
       // Finish the List HTML
       html = html + "</ul>";


       // Close Content div
       html = html + "</div>";

     
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
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}

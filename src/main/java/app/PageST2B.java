package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*; 


/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Temperature Change By City/State</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='page2B.css' />";
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
                <h1>Temperature Change By City/State</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """

        <div class="breadcrumbs">
      <a href='/'>Home</a> > <a href="page2B.html">Temperature Change By City/State</a>
    </div>

    <br>

    <div class="description">
    <h2>Description:</h2> 
    <p>View Temperature Changes and Rankings for your selected country's cities or states between the year range of 1750-2013! You have the option to view all cities or states's temperature changes and rankings
     for your selected country or you can even enter in any city or state located in your selected country and get that individual city/state's temperature change and ranking! Rankings are calculated based upon largest 
     to smallest temperature changes of each available city/state from the country. Note that entering a specific city/state name is optional. Make sure each city/state is spelt correctly when entering your chosen city/state name
     and the corresponding region type is selected.
     </p>
    </div> 

    <div class="filterheader">
    <h1>Select Filtering Options:</h1>
    </div> 
            <form action='page2B.html' method="post" id='form'>
            <label for="country" required>Select A Country:</label>
            <select id="country" name="country">
             <option value="">--Please choose an option--</option>
            <option value="Aruba">Aruba</option>
            <option value="Afghanistan">Afghanistan</option>
            <option value="Angola">Angola</option>
            <option value="Albania">Albania</option>
            <option value="Andorra">Andorra</option>
            <option value="United Arab Emirates">United Arab Emirates</option>
            <option value="Argentina">Argentina</option>
            <option value="Armenia">Armenia</option>
            <option value="American Samoa">American Samoa</option>
            <option value="Antigua And Barbuda">Antigua And Barbuda</option>
            <option value="Australia">Australia</option>
            <option value="Austria">Austria</option>
            <option value="Azerbaijan">Azerbaijan</option>
            <option value="Burundi">Burundi</option>
            <option value="Belgium">Belgium</option>
            <option value="Benin">Benin</option>
            <option value="Burkina Faso">Burkina Faso</option>
            <option value="Bangladesh">Bangladesh</option>
            <option value="Bulgaria">Bulgaria</option>
            <option value="Bahrain">Bahrain</option>
            <option value="Bahamas">Bahamas</option>
            <option value="Bosnia And Herzegovina">Bosnia And Herzegovina</option>
            <option value="Belarus">Belarus</option>
            <option value="Belize">Belize</option>
            <option value="Bolivia">Bolivia</option>
            <option value="Brazil">Brazil</option>
            <option value="Barbados">Barbados</option>
            <option value="Bhutan">Bhutan</option>
            <option value="Botswana">Botswana</option>
            <option value="Central African Republic">Central African Republic</option>
            <option value="Canada">Canada</option>
            <option value="Switzerland">Switzerland</option>
            <option value="Chile">Chile</option>
            <option value="China">China</option>
            <option value="Côte D'Ivoire">Côte D'Ivoire</option>
            <option value="Cameroon">Cameroon</option>
            <option value="Congo (Democratic Republic Of The)">Congo (Democratic Republic Of The)</option>
            <option value="Congo">Congo</option>
            <option value="Colombia">Colombia</option>
            <option value="Comoros">Comoros</option>
            <option value="Cape Verde">Cape Verde</option>
            <option value="Costa Rica">Costa Rica</option>
            <option value="Cuba">Cuba</option>
            <option value="Cayman Islands">Cayman Islands</option>
            <option value="Cyprus">Cyprus</option>
            <option value="Czech Republic">Czech Republic</option>
            <option value="Germany">Germany</option>
            <option value="Djibouti">Djibouti</option>
            <option value="Dominica">Dominica</option>
            <option value="Denmark">Denmark</option>
            <option value="Dominican Republic">Dominican Republic</option>
            <option value="Algeria">Algeria</option>
            <option value="Ecuador">Ecuador</option>
            <option value="Egypt">Egypt</option>
            <option value="Eritrea">Eritrea</option>
            <option value="Spain">Spain</option>
            <option value="Estonia">Estonia</option>
            <option value="Ethiopia">Ethiopia</option>
            <option value="Finland">Finland</option>
            <option value="Fiji">Fiji</option>
            <option value="France">France</option>
            <option value="Faroe Islands">Faroe Islands</option>
            <option value="Federated States Of Micronesia">Federated States Of Micronesia</option>
            <option value="Gabon">Gabon</option>
            <option value="United Kingdom">United Kingdom</option>
            <option value="Georgia">Georgia</option>
            <option value="Ghana">Ghana</option>
            <option value="Guinea">Guinea</option>
            <option value="Gambia">Gambia</option>
            <option value="Guinea Bissau">Guinea Bissau</option>
            <option value="Equatorial Guinea">Equatorial Guinea</option>
            <option value="Greece">Greece</option>
            <option value="Grenada">Grenada</option>
            <option value="Greenland">Greenland</option>
            <option value="Guatemala">Guatemala</option>
            <option value="Guam">Guam</option>
            <option value="Guyana">Guyana</option>
            <option value="Hong Kong">Hong Kong</option>
            <option value="Honduras">Honduras</option>
            <option value="Croatia">Croatia</option>
            <option value="Haiti">Haiti</option>
            <option value="Hungary">Hungary</option>
            <option value="Indonesia">Indonesia</option>
            <option value="Isle Of Man">Isle Of Man</option>
            <option value="India">India</option>
            <option value="Ireland">Ireland</option>
            <option value="Iran">Iran</option>
            <option value="Iraq">Iraq</option>
            <option value="Iceland">Iceland</option>
            <option value="Israel">Israel</option>
            <option value="Italy">Italy</option>
            <option value="Jamaica">Jamaica</option>
            <option value="Jordan">Jordan</option>
            <option value="Japan">Japan</option>
            <option value="Kazakhstan">Kazakhstan</option>
            <option value="Kenya">Kenya</option>
            <option value="Kyrgyzstan">Kyrgyzstan</option>
            <option value="Cambodia">Cambodia</option>
            <option value="Kiribati">Kiribati</option>
            <option value="Saint Kitts And Nevis">Saint Kitts And Nevis</option>
            <option value="South Korea">South Korea</option>
            <option value="Kuwait">Kuwait</option>
            <option value="Laos">Laos</option>
            <option value="Lebanon">Lebanon</option>
            <option value="Liberia">Liberia</option>
            <option value="Libya">Libya</option>
            <option value="Saint Lucia">Saint Lucia</option>
            <option value="Liechtenstein">Liechtenstein</option>
            <option value="Sri Lanka">Sri Lanka</option>
            <option value="Lesotho">Lesotho</option>
            <option value="Lithuania">Lithuania</option>
            <option value="Luxembourg">Luxembourg</option>
            <option value="Latvia">Latvia</option>
            <option value="Macau">Macau</option>
            <option value="Saint Martin">Saint Martin</option>
            <option value="Morocco">Morocco</option>
            <option value="Monaco">Monaco</option>
            <option value="Moldova">Moldova</option>
            <option value="Madagascar">Madagascar</option>
            <option value="Mexico">Mexico</option>
            <option value="Macedonia">Macedonia</option>
            <option value="Mali">Mali</option>
            <option value="Malta">Malta</option>
            <option value="Burma">Burma</option>
            <option value="Montenegro">Montenegro</option>
            <option value="Mongolia">Mongolia</option>
            <option value="Northern Mariana Islands">Northern Mariana Islands</option>
            <option value="Mozambique">Mozambique</option>
            <option value="Mauritania">Mauritania</option>
            <option value="Mauritius">Mauritius</option>
            <option value="Malawi">Malawi</option>
            <option value="Malaysia">Malaysia</option>
            <option value="Namibia">Namibia</option>
            <option value="New Caledonia">New Caledonia</option>
            <option value="Niger">Niger</option>
            <option value="Nigeria">Nigeria</option>
            <option value="Nicaragua">Nicaragua</option>
            <option value="Netherlands">Netherlands</option>
            <option value="Norway">Norway</option>
            <option value="Nepal">Nepal</option>
            <option value="New Zealand">New Zealand</option>
            <option value="Oman">Oman</option>
            <option value="Pakistan">Pakistan</option>
            <option value="Panama">Panama</option>
            <option value="Peru">Peru</option>
            <option value="Philippines">Philippines</option>
            <option value="Palau">Palau</option>
            <option value="Papua New Guinea">Papua New Guinea</option>
            <option value="Poland">Poland</option>
            <option value="Puerto Rico">Puerto Rico</option>
            <option value="North Korea">North Korea</option>
            <option value="Portugal">Portugal</option>
            <option value="Paraguay">Paraguay</option>
            <option value="Gaza Strip">Gaza Strip</option>
            <option value="French Polynesia">French Polynesia</option>
            <option value="Qatar">Qatar</option>
            <option value="Romania">Romania</option>
            <option value="Russia">Russia</option>
            <option value="Rwanda">Rwanda</option>
            <option value="Saudi Arabia">Saudi Arabia</option>
            <option value="Sudan">Sudan</option>
            <option value="Senegal">Senegal</option>
            <option value="Singapore">Singapore</option>
            <option value="Solomon Islands">Solomon Islands</option>
            <option value="Sierra Leone">Sierra Leone</option>
            <option value="El Salvador">El Salvador</option>
            <option value="San Marino">San Marino</option>
            <option value="Somalia">Somalia</option>
            <option value="Serbia">Serbia</option>
            <option value="Sao Tome And Principe">Sao Tome And Principe</option>
            <option value="Suriname">Suriname</option>
            <option value="Slovakia">Slovakia</option>
            <option value="Slovenia">Slovenia</option>
            <option value="Sweden">Sweden</option>
            <option value="Swaziland">Swaziland</option>
            <option value="Sint Maarten">Sint Maarten</option>
            <option value="Seychelles">Seychelles</option>
            <option value="Syria">Syria</option>
            <option value="Turks And Caicas Islands">Turks And Caicas Islands</option>
            <option value="Chad">Chad</option>
            <option value="Togo">Togo</option>
            <option value="Thailand">Thailand</option>
            <option value="Tajikistan">Tajikistan</option>
            <option value="Turkmenistan">Turkmenistan</option>
            <option value="Timor Leste">Timor Leste</option>
            <option value="Tonga">Tonga</option>
            <option value="Trinidad And Tobago">Trinidad And Tobago</option>
            <option value="Tunisia">Tunisia</option>
            <option value="Turkey">Turkey</option>
            <option value="Tanzania">Tanzania</option>
            <option value="Uganda">Uganda</option>
            <option value="Ukraine">Ukraine</option>
            <option value="Uruguay">Uruguay</option>
            <option value="United States">United States</option>
            <option value="Uzbekistan">Uzbekistan</option>
            <option value="Saint Vincent And The Grenadines">Saint Vincent And The Grenadines</option>
            <option value="Venezuela">Venezuela</option>
            <option value="British Virgin Islands">British Virgin Islands</option>
            <option value="Virgin Islands">Virgin Islands</option>
            <option value="Vietnam">Vietnam</option>
            <option value="Samoa">Samoa</option>
            <option value="Yemen">Yemen</option>
            <option value="South Africa">South Africa</option>
            <option value="Zambia">Zambia</option>
            <option value="Zimbabwe">Zimbabwe</option>

             
            </select>
            <br>

             <label for='specificCity'>Enter A Specific City/State Name (Optional):</label>
            <input type='text' id='specificCity' name="specificCity" placeholder="Enter A Specific City/State Name">
           
            <br>
        
          <label id="startYear" for="startYear">Start Year:</label>
         <input type="number" id="startYear" name = "startYear" min="1750" max="2013" oninput="checkYear(this)" required>

             <br>

            <label id="endYear" for="endYear">End Year:</label>
            <input type="number" id="endYear" name = "endYear" min="1750" max="2013" oninput="checkYear(this)" required>
        
            <br>

            <label for='regionType'>Region Type:</label>
            <input type='radio' id='cities' name="regionType" value='cities' required>
            <label for='cities'>Cities</label>
            <input type='radio' id='states' name="regionType" value='states' required>
            <label for='states'>States</label><br>



            <div class="search-button">
    <button type="submit">Search</button>
    <i class="fas fa-search"></i>
    </div>
    <br>
    <input type="button" class="reset-button" value="Reset" onclick="window.location.reload();">

        <div class="disclaimer"> 
        <p><i><strong>*Disclaimer:</strong> Some data may not be available for specific options.</i></p>
        </div>

  </form>

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

        // Close Content div
        html = html + "</div>";

        if (context.method().equalsIgnoreCase("post")) {
            context.formParamMap().containsKey("country");
            String country = context.formParam("country");
            String startYearString = context.formParam("startYear");
            String endYearString = context.formParam("endYear");
            String regionType = context.formParam("regionType");
            String specificCity = context.formParam("specificCity"); 
            String specificState = context.formParam("specificCity");
            
            if (country.isEmpty() || startYearString.isEmpty() || endYearString.isEmpty() || regionType.isEmpty()) {
                html = html + "<div class='missing'>";
                html = html + "<h2><i>There are missing field/s, please try again.</i></h2>";
                html = html + "</div>"; 
            } else {
                int startYear = Integer.parseInt(startYearString);
                int endYear = Integer.parseInt(endYearString);
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
                
                if (regionType.equalsIgnoreCase("Cities") && specificCity.isEmpty()) {
                    ArrayList<City> cities = jdbc.getCity(country, startYear, endYear, regionType, specificCity);
                    regionType = capitalizeFirstLetter(regionType);
                    
                    html += "<div id='user-input'>";
                    html += "<h1>You Entered:</h1>";
                    html += "<p>Country: " + country + "</p>";
                    html += "<p>Start Year: " + startYear + "</p>";
                    html += "<p>End Year: " + endYear + "</p>";
                    html += "<p>Region Type: " + regionType + "</p>";
                    html += "</div>";

        
                    if (cities.isEmpty()) {
                        html = html + "<div class='nodata'>";
                        html = html + "<p>No Data Found For Selected Criteria.</p>";
                        html = html + "</div>";
                    } else {
                         html = html + "<div class='citiestitle'>";
                         html = html + "<h2>" + country + "'s Cities Data:</h2>";
                          html = html + "</div>";
                        html = html + "<table>";
                        html = html + "<tr><th>Country</th><th>City</th><th>Latitude</th><th>Longitude</th><th>Average Temperature Change</th><th>Minimum Temperature Change</th><th>Maximum Temperature Change</th></tr>";
        
                        for (City city : cities) {
                            html = html + "<tr>";
                            html = html + "<td>" + city.getCountry() + "</td>";
                            html = html + "<td>" + city.getCity() + "</td>";
                            html = html + "<td>" + city.getLatitude() + "</td>";
                            html = html + "<td>" + city.getLongitude() + "</td>";
                            html += "<td>" + (city.getAverageTemperatureDiff() != 0 ? formatTemperatureChange(city.getAverageTemperatureDiff()) : "No Data Available") + "</td>";
                             html += "<td>" + (city.getMinimumTemperatureDiff() != 0 ? formatTemperatureChange(city.getMinimumTemperatureDiff()) : "No Data Available") + "</td>";
                             html += "<td>" + (city.getMaximumTemperatureDiff() != 0 ? formatTemperatureChange(city.getMaximumTemperatureDiff()) : "No Data Available") + "</td>";
                            html = html + "</tr>";
                        }
                        html = html + "<br>"; 
                        html = html + "</table>";
                        html = html + "<div class='rankingstitle'>";
                     html = html + "<h2>" + country + "'s Cities Rankings:</h2>";
                         html = html + "</div>";
                        html = html + "<table>";
                        html = html + "<tr><th>Average Temperature Rank</th><th>Minimum Temperature Rank</th><th>Maximum Temperature Rank</th></tr>";
                
                        for (City city : cities) {
                            html = html + "<tr>";
                            html = html + "<td>" + city.getAverageTemperatureRank() + "." + " " + city.getCity() + "</td>";
                            html = html + "<td>" + city.getMinimumTemperatureRank() + "." + " " + city.getCity() +  "</td>";
                            html = html + "<td>" + city.getMaximumTemperatureRank() + "." + " " + city.getCity() + "</td>";
                            html = html + "</tr>";
                        }
                
                        html = html + "</table>";
                    }


                 }
               
                   if (!specificCity.isEmpty() && specificCity != null) {
                     if (regionType.equalsIgnoreCase("Cities")) {
             List<City> filteredCities = new ArrayList<>();
                ArrayList<City> cities = jdbc.getCity(country, startYear, endYear, regionType, specificCity);
                    regionType = capitalizeFirstLetter(regionType);
                    specificCity = capitalizeFirstLetter(specificCity); 
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
                    html += "<p>Country: " + country + "</p>";
                    html += "<p>Specific City: " + specificCity + "</p>";
                    html += "<p>Start Year: " + startYear + "</p>";
                    html += "<p>End Year: " + endYear + "</p>";
                    html += "<p>Region Type: " + regionType + "</p>";
                    html += "</div>";


                      if (cities.isEmpty()) {
                        html = html + "<div class='nodata'>";
                   html = html + "<p>No Data Found For Selected Criteria.</p>";
                        html = html + "</div>";
                    }
                    else {
                for (City city : cities) {
                        Set<String> uniqueCities = new HashSet<>();
                if (city.getCountry().equalsIgnoreCase(country) && city.getCity().equalsIgnoreCase(specificCity)) {
                    String cityKey = city.getCountry() + city.getCity();
                    if (!uniqueCities.contains(cityKey)) {
                        filteredCities.add(city);
                        uniqueCities.add(cityKey);
                    }
                }
               }
            }
         html = html + "<div class='citiestitle'>";
         html = html + "<h2>" + specificCity + " Data:</h2>";
         html = html + "</div>";
        html = html + "<table>";
        html = html + "<tr><th>Country</th><th>City</th><th>Latitude</th><th>Longitude</th><th>Average Temperature Change</th><th>Minimum Temperature Change</th><th>Maximum Temperature Change</th></tr>";

         for (City city : filteredCities) {
        html = html + "<tr>";
        html = html + "<td>" + city.getCountry() + "</td>";
        html = html + "<td>" + city.getCity() + "</td>";
        html = html + "<td>" + city.getLatitude() + "</td>";
        html = html + "<td>" + city.getLongitude() + "</td>";
        html += "<td>" + (city.getAverageTemperatureDiff() != 0 ? formatTemperatureChange(city.getAverageTemperatureDiff()) : "No Data Available") + "</td>";
        html += "<td>" + (city.getMinimumTemperatureDiff() != 0 ? formatTemperatureChange(city.getMinimumTemperatureDiff()) : "No Data Available") + "</td>";
         html += "<td>" + (city.getMaximumTemperatureDiff() != 0 ? formatTemperatureChange(city.getMaximumTemperatureDiff()) : "No Data Available") + "</td>";
        html = html + "</tr>";
    }
              html = html + "<br>"; 
             html = html + "</table>";
            html = html + "<div class='rankingstitle'>";
            html = html + "<h2>" + specificCity + "'s Rankings:</h2>";
             html = html + "</div>";
            html = html + "<table>";
            html = html + "<tr><th>Average Temperature Rank</th><th>Minimum Temperature Rank</th><th>Maximum Temperature Rank</th></tr>";

         for (City city : filteredCities) {
        html = html + "<tr>";
        html = html + "<td>" + city.getAverageTemperatureRank() + "." + " " + city.getCity() + "</td>";
        html = html + "<td>" + city.getMinimumTemperatureRank() + "." + " " + city.getCity() +  "</td>";
        html = html + "<td>" + city.getMaximumTemperatureRank() + "." + " " + city.getCity() + "</td>";
        html = html + "</tr>";
        }

        html = html + "</table>";
        } }
    
                 
                 
                 
                 else if (regionType.equalsIgnoreCase("States") && specificState.isEmpty()) {
                    ArrayList<State> states = jdbc.getState(country, startYear, endYear, regionType, specificState);
                    regionType = capitalizeFirstLetter(regionType);
                        if (startYear < 1750 || startYear > 2013) {
                            html = html + "<div class='startyear'>"; 
             html = html + "<h2><i>Please Enter A Valid Start Year.</i></h2>";
             html = html + "</div>"; 
        } 
         if (endYear < 1750 || endYear > 2013) {
                     html = html + "<div class='endyear'>"; 
             html = html + "<h2><i>Please enter a valid end year.</i></h2>";
               html = html + "</div>"; 
        } 
                    html += "<div id='user-input'>";
                    html += "<h1>You Entered:</h1>";
                    html += "<p>Country: " + country + "</p>";
                    html += "<p>Start Year: " + startYear + "</p>";
                    html += "<p>End Year: " + endYear + "</p>";
                    html += "<p>Region Type: " + regionType + "</p>";
                    html += "</div>";
             
        
                    if (states.isEmpty()) {
                       html = html + "<div class='nodata'>";
                   html = html + "<p>No Data Found For Selected Criteria.</p>";
                        html = html + "</div>";
                    } else {
                        html = html + "<table>";
                            html = html + "<div class='statestitle'>";
                    html = html + "<h2>" + country + "'s States Data:</h2>";
                     html = html + "</div>";
                        html = html + "<tr><th>Country</th><th>State</th><th>Average Temperature Change</th><th>Minimum Temperature Change</th><th>Maximum Temperature Change</th></tr>";
        
                        for (State state : states) {
                            html = html + "<tr>";
                             html = html + "<td>" + state.getCountry() + "</td>";
                             html = html + "<td>" + state.getState() + "</td>";
                             html += "<td>" + (state.getAverageTemperatureDiff() != 0 ? formatTemperatureChange(state.getAverageTemperatureDiff()) : "No Data Available") + "</td>";
                            html += "<td>" + (state.getMinimumTemperatureDiff() != 0 ? formatTemperatureChange(state.getMinimumTemperatureDiff()) : "No Data Available") + "</td>";
                             html += "<td>" + (state.getMaximumTemperatureDiff() != 0 ? formatTemperatureChange(state.getMaximumTemperatureDiff()) : "No Data Available") + "</td>";
                            html = html + "</tr>";
                        }
                          html = html + "<br>"; 
                        html = html + "</table>";
                      html = html + "<div class='rankingstitle'>";
                     html = html + "<h2>" + country + "'s States Rankings:</h2>";
                         html = html + "</div>";
                        html = html + "<table>";
                        html = html + "<tr><th>Average Temperature Rank</th><th>Minimum Temperature Rank</th><th>Maximum Temperature Rank</th></tr>";
                
                        for (State state : states) {
                            html = html + "<tr>";
                            html = html + "<td>" + state.getAverageTemperatureRank() + "." + " " + state.getState() + "</td>";
                            html = html + "<td>" + state.getMinimumTemperatureRank() + "." + " " + state.getState() + "</td>";
                            html = html + "<td>" + state.getMaximumTemperatureRank() + "." + " " + state.getState() + "</td>";
                            html = html + "</tr>";
                        }
                
                        html = html + "</table>";
                    }
                }
                if (!specificState.isEmpty() && specificState != null) {
        if (regionType.equalsIgnoreCase("States")) {
        List<State> filteredStates = new ArrayList<>();
        ArrayList<State> states = jdbc.getState(country, startYear, endYear, regionType, specificState);
        regionType = capitalizeFirstLetter(regionType);
        specificState = capitalizeFirstLetter(specificState); 
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
        html += "<p>Country: " + country + "</p>";
        html += "<p>Specific State: " + specificState + "</p>";
        html += "<p>Start Year: " + startYear + "</p>";
        html += "<p>End Year: " + endYear + "</p>";
        html += "<p>Region Type: " + regionType + "</p>";
        html += "</div>";

        if (states.isEmpty()) {
            html = html + "<div class='nodata'>";
            html = html + "<p>No Data Found For Selected Criteria.</p>";
            html = html + "</div>";
        }


        else {
            Set<String> uniqueStates = new HashSet<>();
            for (State state : states) {
                if (state.getCountry().equalsIgnoreCase(country) && state.getState().equalsIgnoreCase(specificState)) {
                    String stateKey = state.getCountry() + state.getState();
                    if (!uniqueStates.contains(stateKey)) {
                        filteredStates.add(state);
                        uniqueStates.add(stateKey);
                    }
                }
            }

            html = html + "<div class='statestitle'>";
            html = html + "<h2>" + specificState + " Data:</h2>";
            html = html + "</div>";
            html = html + "<table>";
            html = html + "<tr><th>Country</th><th>State</th><th>Average Temperature Change</th><th>Minimum Temperature Change</th><th>Maximum Temperature Change</th></tr>";

            for (State state : filteredStates) {
                html = html + "<tr>";
                html = html + "<td>" + state.getCountry() + "</td>";
                html = html + "<td>" + state.getState() + "</td>";
                html += "<td>" + (state.getAverageTemperatureDiff() != 0 ? formatTemperatureChange(state.getAverageTemperatureDiff()) : "No Data Available") + "</td>";
                html += "<td>" + (state.getMinimumTemperatureDiff() != 0 ? formatTemperatureChange(state.getMinimumTemperatureDiff()) : "No Data Available") + "</td>";
                html += "<td>" + (state.getMaximumTemperatureDiff() != 0 ? formatTemperatureChange(state.getMaximumTemperatureDiff()) : "No Data Available") + "</td>";
                html = html + "</tr>";
            }
              html = html + "<br>"; 
            html = html + "</table>";
            html = html + "<div class='rankingstitle'>";
            html = html + "<h2>" + specificState + "'s Rankings:</h2>";
            html = html + "</div>";
            html = html + "<table>";
            html = html + "<tr><th>Average Temperature Rank</th><th>Minimum Temperature Rank</th><th>Maximum Temperature Rank</th></tr>";

            for (State state : filteredStates) {
                html = html + "<tr>";
                html = html + "<td>" + state.getAverageTemperatureRank() + "." + " " + state.getState() + "</td>";
                html = html + "<td>" + state.getMinimumTemperatureRank() + "." + " " + state.getState() + "</td>";
                html = html + "<td>" + state.getMaximumTemperatureRank() + "." + " " + state.getState() + "</td>";
                html = html + "</tr>";
            }

            html = html + "</table>";
        }
    }
}
else if (!specificState.equalsIgnoreCase(context.formParam("specificCity"))) {
        html = html + "<div class='nodata'>";
                   html = html + "<p>No Data Found For Selected Criteria.</p>";
                        html = html + "</div>";
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
        html = html + "</body>" + "</html>";

    
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


    public static final String DATABASE = "jdbc:sqlite:database/climate.db";
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
        }} catch (SQLException e) {
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
    "StateYearlyLandTemp t1 " +
"JOIN " +
    "StateYearlyLandTemp t2 ON t1.State = t2.State " +
"WHERE " +
    "t1.Country = '" + country + "' " +
     "AND t1.State = '" + specificState + "' " +
    "AND t1.Year = " + startYear + " AND t2.Year = " + endYear;

     

        ResultSet results2 = statement.executeQuery(query2);
     

        while (results2.next()) {
            double averageTemperature = results.getDouble("AverageTemperatureDiff");
            double minimumTemperature = results.getDouble("MinimumTemperatureDiff");
            double maximumTemperature = results.getDouble("MaximumTemperatureDiff");
            String state = results.getString("State");
            String Country = results.getString("Country");
            int averageTemperatureRank = results.getInt("AverageTemperatureRank");
            int minimumTemperatureRank = results.getInt("MinimumTemperatureRank");
            int maximumTemperatureRank = results.getInt("MaximumTemperatureRank");

            State stateData2 = new State(averageTemperature, minimumTemperature, maximumTemperature, state, Country, averageTemperatureRank, minimumTemperatureRank, maximumTemperatureRank);
            states.add(stateData2);
          
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
}

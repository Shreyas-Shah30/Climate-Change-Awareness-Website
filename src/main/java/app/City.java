package app;

public class City {
 
    private double averageTemperatureDiff;
 
    private double minimumTemperatureDiff;
 
    private double maximumTemperatureDiff; 
    
    private String city;

    private String country;

    private String latitude;

    private String longitude;
    
    private int averageTemperatureRank;

    private int minimumTemperatureRank;

    private int maximumTemperatureRank; 


        public City() {

        }


        public City(double averageTemperatureDiff, double minimumTemperatureDiff, double maximumTemperatureDiff, String city, String country, String latitude, String longitude, int averageTemperatureRank, int minimumTemperatureRank, int maximumTemperatureRank) {
            this.averageTemperatureDiff = averageTemperatureDiff;
            this.minimumTemperatureDiff = minimumTemperatureDiff;
            this.maximumTemperatureDiff = maximumTemperatureDiff;
            this.city = city;
            this.country = country;
            this.latitude = latitude;
            this.longitude = longitude;
            this.averageTemperatureRank = averageTemperatureRank;
            this.minimumTemperatureRank = minimumTemperatureRank;
            this.maximumTemperatureRank = maximumTemperatureRank; 
        }
      
         public double getAverageTemperatureDiff() {
            return averageTemperatureDiff;
        }
      
        public double getMinimumTemperatureDiff() {
         return minimumTemperatureDiff;
      }
      
        public double getMaximumTemperatureDiff() {
         return maximumTemperatureDiff;
      }

      public String getCity(){
        return city;
      }

      public String getCountry(){
        return country;
      }

      public String getLatitude(){
        return latitude;
      }

      public String getLongitude(){
        return longitude;
      }

         
      public int getAverageTemperatureRank() {
        return averageTemperatureRank;
    }
  
    public int getMinimumTemperatureRank() {
     return minimumTemperatureRank;
  }
  
    public int getMaximumTemperatureRank() {
     return maximumTemperatureRank;
  }
}

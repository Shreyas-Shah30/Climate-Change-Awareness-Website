package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class Population {

   private String name;

   private int startyear;

   private int endyear;

   private double startaverageTemperature;

   private double endaverageTemperature;

   private double temperatureDiff;

   private long populationdiff;

   private double temperaturePercentageDiff; 

   private double populationPercentageDiff; 


   public Population() {

   }

  
   public Population(String name, int startyear, int endyear, double startaverageTemperature, double endaverageTemperature, double temperatureDiff, long populationdiff, double temperaturePercentageDiff, double populationPercentageDiff) {
      this.name = name; 
      this.startyear = startyear;
      this.endyear = endyear;
      this.startaverageTemperature = startaverageTemperature; 
      this.endaverageTemperature = endaverageTemperature;
      this.temperatureDiff = temperatureDiff;
      this.populationdiff = populationdiff;
      this.temperaturePercentageDiff = temperaturePercentageDiff; 
      this.populationPercentageDiff = populationPercentageDiff; 
   }

   public String getName() {
      return name;
   }

   public int getStartYear() {
      return startyear;
   }

   public int getEndYear() {
      return endyear;
   }
   
   public double getStartAverageTemperature() {
      return startaverageTemperature;
  }

  public double getEndAverageTemperature() {
   return endaverageTemperature;
}


   public double getAverageTemperatureDiff() {
      return temperatureDiff;
  }


public long getPopulationDiff() {
   return populationdiff;
}

 public double getPercentageAverageTemperatureDiff() {
      return temperaturePercentageDiff;
  }

  public double getPercentagePopulationDiff() {
      return populationPercentageDiff;
  }

}

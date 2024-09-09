package app;

public class Worlds {
    private int startyear;

    private int endyear;

    private double worldAverageTemperatureDiff;
 
    private double worldLandAverageTemperatureDiff; 

    private long worldPopulationDiff; 

    private double worldAverageTemperaturePercentageDiff;

    private double worldLandTemperaturePercentageDiff;

    private double populationPercentageDiff;
 
 
    public Worlds() {
 
    }
 
   
    public Worlds(int startyear, int endyear, double worldAverageTemperatureDiff, double worldLandAverageTemperatureDiff, long worldPopulationDiff, double worldAverageTemperaturePercentageDiff, double worldLandTemperaturePercentageDiff, double populationPercentageDiff ) {
        this.startyear = startyear;
        this.endyear = endyear;
       this.worldAverageTemperatureDiff = worldAverageTemperatureDiff; 
       this.worldLandAverageTemperatureDiff = worldLandAverageTemperatureDiff; 
       this.worldPopulationDiff = worldPopulationDiff;
       this.worldAverageTemperaturePercentageDiff = worldAverageTemperaturePercentageDiff; 
       this.worldLandTemperaturePercentageDiff = worldLandTemperaturePercentageDiff; 
       this.populationPercentageDiff = populationPercentageDiff;
    }
 
 
  public int getStartYear(){
    return startyear;
  }
  public int getEndYear(){
    return endyear;
  }
    public double getWorldTemperatureDiff() {
       return worldAverageTemperatureDiff;
   }

 
 
 public double getWorldLandAverageTemperatureDiff() {
    return worldLandAverageTemperatureDiff;
 }


 public long getWorldPopulationDiff() {
    return worldPopulationDiff; 
 }

 public double getWorldAverageTemperaturePercentageDiff() {
    return worldAverageTemperaturePercentageDiff;
 }

 public double getWorldLandTemperaturePercentageDiff() {
    return worldLandTemperaturePercentageDiff;
 }

  public double getPopulationPercentageDiff() {
    return populationPercentageDiff;
 }
}

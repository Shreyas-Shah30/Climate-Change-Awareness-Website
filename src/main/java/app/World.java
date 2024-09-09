package app;

public class World {

    private int year;
 
    private double averageTemperature;
 
    private double minimumTemperature;
 
    private double maximumTemperature; 
 
    private double landAvgTemp; 

    private double landMinTemp; 

    private double landMaxTemp; 

    private long population; 
 
 
    public World() {
 
    }
 
   
    public World(int year, double averageTemperature, double minimumTemperature, double maximumTemperature, double landAvgTemp, double landMinTemp, double landMaxTemp, long population ) {

       this.year = year;
       this.averageTemperature = averageTemperature; 
       this.minimumTemperature = minimumTemperature;
       this.maximumTemperature = maximumTemperature; 
       this.landAvgTemp = landAvgTemp; 
       this.landMinTemp = landMinTemp;
       this.landMaxTemp = landMaxTemp;
       this.population = population;
    }
 
 
    public int getYear() {
       return year;
    }
 
    public double getAverageTemperature() {
       return averageTemperature;
   }
 
   public double getMinimumTemperature() {
    return minimumTemperature;
 }
 
   public double getMaximumTemperature() {
    return maximumTemperature;
 }
 
 public double getLandAverageTemperature() {
    return landAvgTemp;
 }

 public double getLandMinimumTemperature() {
    return landMinTemp;
 }

 public double getLandMaximumTemperature() {
    return landMaxTemp;
 }

 public long getWorldPopulation() {
    return population; 
 }


 }


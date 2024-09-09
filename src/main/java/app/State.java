package app;

public class State {

    private double averageTemperatureDiff;
 
    private double minimumTemperatureDiff;
 
    private double maximumTemperatureDiff; 
    
    private String state;

    private String country;

    private int averageTemperatureRank;

    private int minimumTemperatureRank;

    private int maximumTemperatureRank; 


    public State() {

    }

    public State(double averageTemperatureDiff, double minimumTemperatureDiff, double maximumTemperatureDiff, String state, String country, int averageTemperatureRank, int minimumTemperatureRank, int maximumTemperatureRank) {

        this.averageTemperatureDiff = averageTemperatureDiff;
        this.minimumTemperatureDiff = minimumTemperatureDiff;
        this.maximumTemperatureDiff = maximumTemperatureDiff;
        this.state = state;
        this.country = country;
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

  public String getState(){
    return state;
  }

  public String getCountry(){
    return country;
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

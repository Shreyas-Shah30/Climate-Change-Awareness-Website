package app;

public class TotalYears {
    
private int populationTotalYears;

private int temperatureTotalYears;

public TotalYears() {
    
}



    public TotalYears(int populationTotalYears, int temperatureTotalYears){
        this.populationTotalYears = populationTotalYears;
        this.temperatureTotalYears = temperatureTotalYears;
    }

    public int getPopulationTotalYears() {
        return populationTotalYears;
    }

    public int getTemperatureTotalYears() {
        return temperatureTotalYears;
    }

}

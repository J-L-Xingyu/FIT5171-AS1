public class Airplane {
    private int airplaneID;
    private String airplaneModel;
    private int businessSitsNumber;
    private int economySitsNumber;
    private int crewSitsNumber;

    public Airplane() {
        // 默认构造函数
    }

    public Airplane(int airplaneID, String airplaneModel, int businessSitsNumber, int economySitsNumber, int crewSitsNumber) {
        this.airplaneID = airplaneID;
        this.airplaneModel = airplaneModel;
        setBusinessSitsNumber(businessSitsNumber);
        setEconomySitsNumber(economySitsNumber);
        setCrewSitsNumber(crewSitsNumber);
    }

    public int getAirplaneID() {
        return airplaneID;
    }

    public void setAirplaneID(int airplaneID) {
        this.airplaneID = airplaneID;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        this.airplaneModel = airplaneModel;
    }

    public int getBusinessSitsNumber() {
        return businessSitsNumber;
    }

    public void setBusinessSitsNumber(int businessSitsNumber) {
        if (businessSitsNumber < 1 || businessSitsNumber > 300) {
            throw new IllegalArgumentException("Business seats number must be in the range [1, 300]");
        }
        this.businessSitsNumber = businessSitsNumber;
    }

    public int getEconomySitsNumber() {
        return economySitsNumber;
    }

    public void setEconomySitsNumber(int economySitsNumber) {
        if (economySitsNumber < 1 || economySitsNumber > 300) {
            throw new IllegalArgumentException("Economy seats number must be in the range [1, 300]");
        }
        this.economySitsNumber = economySitsNumber;
    }

    public int getCrewSitsNumber() {
        return crewSitsNumber;
    }

    public void setCrewSitsNumber(int crewSitsNumber) {
        if (crewSitsNumber < 1 || crewSitsNumber > 300) {
            throw new IllegalArgumentException("Crew seats number must be in the range [1, 300]");
        }
        this.crewSitsNumber = crewSitsNumber;
    }

    public String toString() {
        return "Airplane{" +
                "model=" + getAirplaneModel() + '\'' +
                ", business sits=" + getBusinessSitsNumber() + '\'' +
                ", economy sits=" + getEconomySitsNumber() + '\'' +
                ", crew sits=" + getCrewSitsNumber() + '\'' +
                '}';
    }

    public static Airplane getAirPlaneInfo(int airplane_id) {
        // TODO Auto-generated method stub
        return null;
    }
}

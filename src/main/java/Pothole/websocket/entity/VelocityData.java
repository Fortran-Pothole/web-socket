package Pothole.websocket.entity;

public class VelocityData {
    private double velocity;

    // 기본 생성자
    public VelocityData() {}

    // 생성자
    public VelocityData(double velocity) {
        this.velocity = velocity;
    }

    // Getter 및 Setter
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "VelocityData{" +
                "velocity=" + velocity +
                '}';
    }
}
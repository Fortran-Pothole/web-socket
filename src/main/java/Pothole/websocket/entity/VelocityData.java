package Pothole.websocket.entity;

import lombok.Getter;

@Getter
public class VelocityData {
    // Getter 및 Setter
    private int velocity;

    // 기본 생성자
    public VelocityData() {}

    // 생성자
    public VelocityData(int velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "VelocityData{" +
                "velocity=" + velocity +
                '}';
    }
}
package vehicles;

import java.awt.Point;

public class VehicleInfo {
    private Point position;
    private int batteryLevel;

    public VehicleInfo(Point position, int batteryLevel) {
        this.position = position;
        this.batteryLevel = batteryLevel;
    }

    public Point getPosition() {
        return position;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

	public void setBatteryLevel(int batteryLevel2) {
		this.batteryLevel = batteryLevel;
		
	}
}


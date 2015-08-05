package euclid.two.dim.world;

import euclid.two.dim.Configuration;
import euclid.two.dim.model.EuVector;

public class Camera {
	private double mapX, mapY;
	private double width, height;
	private double zoom;
	private double rotation;
	private double mapWidth, mapHeight;

	public Camera(Camera clone) {
		this.mapX = clone.getMapX();
		this.mapY = clone.getMapY();
		this.width = clone.getWidth();
		this.height = clone.getHeight();
		this.zoom = clone.getZoom();
		this.rotation = clone.getRotation();
		this.mapWidth = clone.getMapWidth();
		this.mapHeight = clone.getMapHeight();
	}

	private double getMapHeight() {
		return mapHeight;
	}

	private double getMapWidth() {
		return mapWidth;
	}

	public Camera() {
		this.mapX = 0;
		this.mapY = 0;
		this.width = 1000;
		this.height = 1000;
		this.zoom = Configuration.initialZoom;
		this.rotation = Configuration.rotation;
		this.mapWidth = 2000;
		this.mapHeight = 2000;
	}

	public double getRotation() {
		return rotation;
	}

	public double getMapX() {
		return mapX;
	}

	public void setMapX(double mapX) {
		this.mapX = Math.max(Math.min(mapX, mapWidth * 1.5 * zoom), -.5 * mapWidth * zoom);
	}

	public double getMapY() {
		return mapY;
	}

	public void setMapY(double mapY) {
		this.mapY = Math.max(Math.min(mapY, mapHeight * 1.5 * zoom), -.5 * mapHeight * zoom);
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = Math.max(Math.min(zoom, 4), .5);
	}

	public EuVector veiwToMap(EuVector vect) {
		double x = -mapX / zoom + vect.getX() / zoom;
		double y = -mapY / zoom + vect.getY() / zoom;

		return new EuVector(x, y);
	}

	public EuVector mapToView(EuVector euVector) {
		double x = 0;
		double y = 0;

		return new EuVector(x, y);
	}

	public Camera deepCopy() {
		return new Camera(this);
	}
}

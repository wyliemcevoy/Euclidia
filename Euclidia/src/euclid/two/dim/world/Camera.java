package euclid.two.dim.world;

import euclid.two.dim.Configuration;
import euclid.two.dim.model.EuVector;

public class Camera {
	private double mapX, mapY;
	private double width, height;
	private double scale;
	private double rotation;
	private double mapWidth, mapHeight;

	public Camera(Camera clone) {
		this.mapX = clone.getMapX();
		this.mapY = clone.getMapY();
		this.width = clone.getWidth();
		this.height = clone.getHeight();
		this.scale = clone.getScale();
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
		this.scale = Configuration.initialZoom;
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
		this.mapX = Math.max(Math.min(mapX, mapWidth * 1.5), -.5 * mapWidth);
	}

	public double getMapY() {
		return mapY;
	}

	public void translate(EuVector translation) {
		translate(translation.getX(), translation.getY());
	}

	public void translate(double x, double y) {
		this.mapX += x;
		this.mapY += y;
	}

	public void setMapY(double mapY) {
		this.mapY = Math.max(Math.min(mapY, mapHeight * 1.5), -.5 * mapHeight);
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

	public double getScale() {
		return scale;
	}

	public void zoom(double zoom) {

		EuVector oldBounds = screenToMap(new EuVector(width, height));
		this.scale = Math.max(Math.min(zoom, 4), .5);
		EuVector newBounds = screenToMap(new EuVector(width, height));

		this.mapX = mapX + (oldBounds.getX() - newBounds.getX()) / 2;
		this.mapY = mapY + (oldBounds.getY() - newBounds.getY()) / 2;
	}

	public EuVector screenToMap(EuVector vect) {

		double x = mapX + vect.getX() / scale;
		double y = mapY + vect.getY() / scale;
		return new EuVector(x, y);
	}

	public EuVector mapToScreen(EuVector vect) {
		double x = (-mapX + vect.getX()) * scale;
		double y = (-mapY + vect.getY()) * scale;

		return new EuVector(x, y);
	}

	public Camera deepCopy() {
		return new Camera(this);
	}
}

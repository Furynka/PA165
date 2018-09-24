package cz.fi.muni.carshop.entities;

import java.awt.Color;
import java.util.Objects;

import cz.fi.muni.carshop.enums.CarTypes;

public class Car {

	private Color color;
	private CarTypes type;
	private int constructionYear;
	private int price;

	public Car(Color color, CarTypes type, int constructionYear, int price) {
		super();
		this.color = color;
		this.type = type;
		this.constructionYear = constructionYear;
		this.price = price;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public CarTypes getType() {
		return type;
	}
	
	public int getConstructionYear() {
		return constructionYear;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Car car = (Car) o;
		return constructionYear == car.constructionYear &&
				price == car.price &&
				Objects.equals(color, car.color) &&
				type == car.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, type, constructionYear, price);
	}
}

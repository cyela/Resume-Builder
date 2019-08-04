package com.demo.model;

public class Project {

	private String name;
	private String description;
	private String technology;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTechnology() {
		return technology;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", description=" + description + ", technology=" + technology + "]";
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}
}
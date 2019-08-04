package com.demo.model;

import java.util.ArrayList;
import java.util.Map;

public class Resume {

	private Header header;
	private ArrayList<Education> education;
	private ArrayList<Experience> experience;
	private Map<String, String> skills;
	private ArrayList<Project> projects;

	public ArrayList<Education> getEducation() {
		return education;
	}

	public void setEducation(ArrayList<Education> education) {
		this.education = education;
	}

	public ArrayList<Experience> getExperience() {
		return experience;
	}

	public void setExperience(ArrayList<Experience> experience) {
		this.experience = experience;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Map<String, String> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, String> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Resume [header=" + header + ", education=" + education + ", experience=" + experience + ", skills="
				+ skills + ", projects=" + projects + "]";
	}
}
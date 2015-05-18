package com.roc.springmvc.beans;

import java.util.Map;

import com.roc.collaborativeFiltering.annotation.FilterType;
import com.roc.collaborativeFiltering.annotation.MapFilter;
import com.roc.collaborativeFiltering.handler.MapCosineSimilarity;

@FilterType(MapCosineSimilarity.class)
public class User {

	private Integer id;
	private String gender;
	private Integer age;
	private String occupation;

	@MapFilter
	private Map<Object, Double> map;
	
	private double similar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGender() {
		if (gender.equals("M")) {
			return "男";
		} else if (gender.equals("F")) {
			return "女";
		} else {
			return gender;
		}
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Map<Object, Double> getMap() {
		return map;
	}

	public User setMap(Map<Object, Double> map) {
		this.map = map;
		return this;
	}


	public double getSimilar() {
		return similar;
	}

	public void setSimilar(Double similar) {
		this.similar = similar;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", gender=" + gender + ", age=" + age
				+ ", occupation=" + occupation + "]";
	}

}

package com.roc.springmvc.beans;

public class Movie implements Comparable<Movie> {
	private Integer id;
	private String name;
	private Integer year;

	private Integer userid;
	private double rating;
	private double avgrating;
	private double similar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public double getSimilar() {
		return similar;
	}

	public void setSimilar(double similar) {
		this.similar = similar;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public int compareTo(Movie o) {
		return (int) ((this.getSimilar() - o.getSimilar()) * 100);
	}

	public double getAvgrating() {
		return avgrating;
	}

	public void setAvgrating(double avgrating) {
		this.avgrating = avgrating;
	}

}

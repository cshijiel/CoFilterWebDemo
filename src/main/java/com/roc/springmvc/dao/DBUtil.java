package com.roc.springmvc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.roc.springmvc.beans.Movie;
import com.roc.springmvc.beans.MovieRating;
import com.roc.springmvc.beans.User;

@Repository
public class DBUtil implements CoFilterRepository {

	private SqlSessionFactory sq = MyBatisConnectionFactory
			.getSqlSessionFactory();

	@Override
	public List<User> getToperUsers() {
		try (SqlSession session = sq.openSession()) {
			CoFilterRepository cf = session.getMapper(CoFilterRepository.class);
			return cf.getToperUsers();
		}
	}

	@Override
	public List<MovieRating> getMovieRatingByUserId(int userid) {
		try (SqlSession session = sq.openSession()) {
			CoFilterRepository cf = session.getMapper(CoFilterRepository.class);
			return cf.getMovieRatingByUserId(userid);
		}
	}

	@Override
	public User getUser(int userid) {
		try (SqlSession session = sq.openSession()) {
			CoFilterRepository cf = session.getMapper(CoFilterRepository.class);
			Map<Object, Double> map = new HashMap<Object, Double>();
			for (MovieRating movieRating : getMovieRatingByUserId(userid)) {
				map.put(movieRating.getMovieid(), movieRating.getRating());
			}
			return cf.getUser(userid).setMap(map);
		}
	}

	@Override
	public List<Movie> getMoviesByUsers(String userids) {
		try (SqlSession session = sq.openSession()) {
			CoFilterRepository cf = session.getMapper(CoFilterRepository.class);
			return cf.getMoviesByUsers(userids);
		}
	}

	@Override
	public User getRandomUser() {
		try (SqlSession session = sq.openSession()) {
			CoFilterRepository cf = session.getMapper(CoFilterRepository.class);
			User user = cf.getRandomUser();
			Map<Object, Double> map = new HashMap<Object, Double>();
			for (MovieRating movieRating : getMovieRatingByUserId(user.getId())) {
				map.put(movieRating.getMovieid(), movieRating.getRating());
			}
			return user.setMap(map);
		}
	}

}

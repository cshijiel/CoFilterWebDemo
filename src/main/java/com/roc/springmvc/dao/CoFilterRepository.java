package com.roc.springmvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.roc.springmvc.beans.Movie;
import com.roc.springmvc.beans.MovieRating;
import com.roc.springmvc.beans.User;

public interface CoFilterRepository {

	/**
	 * 查找Top User：观看电影最多的用户
	 * 
	 * @return
	 */
	@Select("SELECT `user`.*,occupation.`name` as occupation,t.sum FROM `user` LEFT JOIN occupation ON `user`.occupationid = occupation.id LEFT JOIN (SELECT COUNT(*) sum,userid FROM movierating GROUP BY userid)t ON `user`.id = t.userid  ORDER BY t.sum DESC")
	List<User> getToperUsers();
	
	@Select("SELECT `user`.*,occupation.`name` as occupation FROM `user` LEFT JOIN occupation ON `user`.occupationid = occupation.id  ORDER BY RAND() LIMIT 1")
	User getRandomUser();

	@Select("SELECT `user`.*,occupation.`name` as occupation FROM `user` LEFT JOIN occupation ON `user`.occupationid = occupation.id WHERE `user`.id = #{userid}")
	User getUser(@Param("userid") int userid);

	@Select("SELECT * FROM movierating WHERE userid = #{userid}")
	List<MovieRating> getMovieRatingByUserId(@Param("userid") int userid);
	
	@Select("SELECT DISTINCT movie.id,movie.name,movie.year,movierating.rating,movierating.userid,t.avgrating FROM movie LEFT JOIN (SELECT AVG(rating) AS avgrating,movieid FROM movierating GROUP BY movieid)t ON t.movieid = movie.id LEFT JOIN moviegenre ON movie.id = moviegenre.movieid LEFT JOIN movierating ON movie.id = movierating.movieid WHERE movierating.userid IN (#{userids})")
	List<Movie> getMoviesByUsers(@Param("userids") String userids);

}

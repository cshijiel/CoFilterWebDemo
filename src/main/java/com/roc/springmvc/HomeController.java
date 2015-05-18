package com.roc.springmvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.roc.collaborativeFiltering.handler.CoFilter;
import com.roc.collaborativeFiltering.handler.CollaborativeFiltering;
import com.roc.springmvc.beans.Movie;
import com.roc.springmvc.beans.User;
import com.roc.springmvc.dao.DBUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private DBUtil dbUtil;

	private static CollaborativeFiltering collaborativeFiltering = new CoFilter();;

	/**
	 * 首页
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("topUsers", dbUtil.getToperUsers());
		logger.info("index");
		return "index";
	}

	@RequestMapping(value = "/similar-users", method = RequestMethod.GET)
	public String getSimilarUsers(@RequestParam("fromUser") Integer fromUserId,
			Model model) {
		User user = dbUtil.getUser(fromUserId);
		model.addAttribute("user", user);

		model.addAttribute("similarUsers", getSimilarUsersByFromUser(user));
		logger.info("getSimilarUsers");
		return "similar-users";
	}

	@RequestMapping(value = "/similar-users-from-random", method = RequestMethod.GET)
	public String getSimilarUsersByRandom(Model model) {
		User user = dbUtil.getRandomUser();
		model.addAttribute("user", user);

		model.addAttribute("similarUsers", getSimilarUsersByFromUser(user));
		logger.info("getSimilarUsersByRandom");
		return "similar-users";
	}

	@RequestMapping(value = "/recommend-movies", method = RequestMethod.GET)
	public String getRecommendMovies(
			@RequestParam("fromUser") Integer fromUserId, Model model) {
		User user = dbUtil.getUser(fromUserId);
		model.addAttribute("user", user);
		List<User> users = getSimilarUsersByFromUser(user);
		Map<Integer, User> usersMap = new HashMap<Integer, User>();
		for (User u : users) {
			usersMap.put(u.getId(), u);
		}

		String userids = userids2String(users);
		List<Movie> movies = dbUtil.getMoviesByUsers(userids);
		Map<Integer, Movie> map = new HashMap<Integer, Movie>();
		for (Movie movie : movies) {
			if (!map.containsKey(movie.getId())) {
				movie.setSimilar(usersMap.get(movie.getUserid()).getSimilar()
						* movie.getRating());
				map.put(movie.getId(), movie);
			}
		}

		List<Movie> movieList = new ArrayList<Movie>();
		for (Integer k : map.keySet()) {
			movieList.add(map.get(k));
		}
		Collections.sort(movieList);
		Collections.reverse(movieList);

		model.addAttribute("movieList", movieList);
		logger.info("getRecommendMovies");
		return "recommend-movies";
	}

	private String userids2String(List<User> users) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < users.size(); i++) {
			buffer = (users.size() - 1) == i ? buffer.append(users.get(i)
					.getId()) : buffer.append(users.get(i).getId() + ",");
		}

		return buffer.toString();

	}

	private List<User> getSimilarUsersByFromUser(User user) {
		Map<Double, User> similar_userMap = new HashMap<Double, User>();
		List<Double> similarList = new ArrayList<Double>();
		List<User> users = new ArrayList<User>();

		for (User u : dbUtil.getToperUsers()) {
			int uid = u.getId();
			int oid = user.getId();
			if (uid != oid) {
				u = dbUtil.getUser(u.getId());
				Double similar = new Double(
						collaborativeFiltering.getSimilarity(user, u));
				u.setSimilar(similar);
				similar_userMap.put(similar, u);
			}
		}
		for (Double similar : similar_userMap.keySet()) {
			similarList.add(similar);
		}
		Collections.sort(similarList);
		Collections.reverse(similarList);

		for (double similar : similarList) {
			if (similar > 0.01 && similar < 1.0) {
				users.add(similar_userMap.get(similar));
			}
		}
		return users;
	}

}

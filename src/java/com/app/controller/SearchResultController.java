package com.app.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.dao.impl.SearchQueryDAOImpl;
import com.app.dao.impl.SearchResultDAOImpl;

import com.app.model.SearchQueryModel;
import com.app.model.SearchResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SearchResultController {

	@RequestMapping(
		value = {
			"/result", "/search_result", "/view_search_result",
			"/view_search_results"
		},
		method = RequestMethod.GET)
	public String viewSearchResults(Map<String, Object> model)
		throws SQLException {

		Map<Integer, List<SearchResultModel>> searchResultModelMap =
			new HashMap<>();

		List<SearchQueryModel> searchQueryModels =
			_searchQueryDAOImpl.getSearchQueries();

		for (SearchQueryModel searchQueryModel : searchQueryModels) {
			int searchQueryId = searchQueryModel.getSearchQueryId();

			List<SearchResultModel> searchResultModels =
				_searchResultDAOImpl.getSearchQueryResults(searchQueryId);

			if (!searchResultModels.isEmpty()) {
				searchResultModelMap.put(searchQueryId, searchResultModels);
			}
		}

		model.put("searchResultModelMap", searchResultModelMap);

 		return "view_search_results";
	}

	private static final SearchQueryDAOImpl _searchQueryDAOImpl =
		new SearchQueryDAOImpl();

	private static final SearchResultDAOImpl _searchResultDAOImpl =
		new SearchResultDAOImpl();

	private static final Logger _log = LoggerFactory.getLogger(
		SearchQueryController.class);
}
package com.app.test.dao;

import com.app.dao.impl.SearchQueryDAOImpl;
import com.app.exception.DatabaseConnectionException;
import com.app.model.SearchQueryModel;
import com.app.test.BaseDatabaseTestCase;

import java.sql.SQLException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jonathan McCann
 */
public class SearchQueryDAOTest extends BaseDatabaseTestCase {

	@Before
	public void doSetUp() throws DatabaseConnectionException {
		_searchQueryDAOImpl = new SearchQueryDAOImpl();
	}

	@Test
	public void testSearchQueryDAO() throws SQLException {

		// Test add

		_searchQueryDAOImpl.addSearchQuery("First test search query");
		_searchQueryDAOImpl.addSearchQuery("Second test search query");

		// Test get

		String searchQuery = _searchQueryDAOImpl.getSearchQuery(1);

		Assert.assertEquals("First test search query", searchQuery);

		// Test get multiple

		List<SearchQueryModel> searchQueryModels =
			_searchQueryDAOImpl.getSearchQueries();

		SearchQueryModel firstSearchQueryModel = searchQueryModels.get(0);
		SearchQueryModel secondSearchQueryModel = searchQueryModels.get(1);

		Assert.assertEquals(2, searchQueryModels.size());
		Assert.assertEquals(1, firstSearchQueryModel.getSearchQueryId());
		Assert.assertEquals(2, secondSearchQueryModel.getSearchQueryId());
		Assert.assertEquals(
			"First test search query", firstSearchQueryModel.getSearchQuery());
		Assert.assertEquals(
			"Second test search query",
			secondSearchQueryModel.getSearchQuery());

		// Test update

		_searchQueryDAOImpl.updateSearchQuery(1, "Updated test search query");

		String updatedSearchQuery = _searchQueryDAOImpl.getSearchQuery(1);

		Assert.assertEquals("Updated test search query", updatedSearchQuery);

		// Test delete multiple

		_searchQueryDAOImpl.deleteSearchQuery(1);
		_searchQueryDAOImpl.deleteSearchQuery(2);

		searchQueryModels = _searchQueryDAOImpl.getSearchQueries();

		Assert.assertEquals(0, searchQueryModels.size());
	}

	private static SearchQueryDAOImpl _searchQueryDAOImpl;
}
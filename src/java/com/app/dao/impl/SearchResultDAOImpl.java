package com.app.dao.impl;

import com.app.dao.SearchResultDAO;
import com.app.exception.DatabaseConnectionException;
import com.app.model.SearchResultModel;
import com.app.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jonathan McCann
 */
public class SearchResultDAOImpl implements SearchResultDAO {

	@Override
	public void addSearchResult(SearchResultModel searchResultModel)
		throws SQLException {

		Connection connection = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_ADD_SEARCH_RESULT_SQL);

			preparedStatement.setInt(1, searchResultModel.getSearchQueryId());
			preparedStatement.setString(2, searchResultModel.getItemId());
			preparedStatement.setString(3, searchResultModel.getItemTitle());
			preparedStatement.setString(
				4, searchResultModel.getTypeOfAuction());
			preparedStatement.setString(5, searchResultModel.getItemURL());
			preparedStatement.setLong(
				6, searchResultModel.getEndingTime().getTime());
			preparedStatement.setDouble(7, searchResultModel.getAuctionPrice());
			preparedStatement.setDouble(8, searchResultModel.getFixedPrice());

			preparedStatement.executeUpdate();
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error(
				"Unable to add search result for item ID: " +
					searchResultModel.getItemId());

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void addSearchResult(
			int searchQueryId, String itemId, String itemTitle,
			double auctionPrice, double fixedPrice, String itemURL,
			Date endingTime, String typeOfAuction)
		throws SQLException {

		Connection connection = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_ADD_SEARCH_RESULT_SQL);

			preparedStatement.setInt(1, searchQueryId);
			preparedStatement.setString(2, itemId);
			preparedStatement.setString(3, itemTitle);
			preparedStatement.setString(4, typeOfAuction);
			preparedStatement.setString(5, itemURL);
			preparedStatement.setLong(6, endingTime.getTime());
			preparedStatement.setDouble(7, auctionPrice);
			preparedStatement.setDouble(8, fixedPrice);

			preparedStatement.executeUpdate();
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error("Unable to add search result for item ID: " + itemId);

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void deleteSearchResult(int searchResultId) throws SQLException {
		Connection connection = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_DELETE_SEARCH_RESULT_SQL);

			preparedStatement.setInt(1, searchResultId);

			preparedStatement.executeUpdate();
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error(
				"Unable to delete search result for search result ID: " +
					searchResultId);

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void deleteSearchQueryResults(int searchQueryId)
		throws SQLException {

		Connection connection = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_DELETE_SEARCH_RESULT_WITH_SEARCH_QUERY_SQL);

			preparedStatement.setInt(1, searchQueryId);

			preparedStatement.executeUpdate();
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error(
				"Unable to delete search results for search query ID: " +
					searchQueryId);

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public List<SearchResultModel> getSearchQueryResults(int searchQueryId)
		throws SQLException {

		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_GET_SEARCH_RESULT_SQL);

			preparedStatement.setInt(1, searchQueryId);

			resultSet = preparedStatement.executeQuery();

			List<SearchResultModel> searchResults =
				new ArrayList<SearchResultModel>();

			while (resultSet.next()) {
				SearchResultModel searchResult = new SearchResultModel();

				searchResult.setSearchResultId(
					resultSet.getInt("searchResultId"));
				searchResult.setSearchQueryId(
					resultSet.getInt("searchQueryId"));
				searchResult.setItemId(resultSet.getString("itemId"));
				searchResult.setItemTitle(resultSet.getString("itemTitle"));
				searchResult.setTypeOfAuction(
					resultSet.getString("typeOfAuction"));
				searchResult.setItemURL(resultSet.getString("itemURL"));
				searchResult.setEndingTime(
					new Date(resultSet.getLong("endingTime")));
				searchResult.setAuctionPrice(
					resultSet.getDouble("auctionPrice"));
				searchResult.setFixedPrice(resultSet.getDouble("fixedPrice"));

				searchResults.add(searchResult);
			}

			return searchResults;
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error(
				"Unable to return all search query results for search query " +
					"ID: " + searchQueryId);

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	@Override
	public SearchResultModel getSearchResult(int searchResultId)
		throws SQLException {

		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_GET_SEARCH_RESULT_SQL);

			preparedStatement.setInt(1, searchResultId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				SearchResultModel searchResult = new SearchResultModel();

				searchResult.setSearchResultId(
					resultSet.getInt("searchResultId"));
				searchResult.setSearchQueryId(
					resultSet.getInt("searchQueryId"));
				searchResult.setItemId(resultSet.getString("itemId"));
				searchResult.setItemTitle(resultSet.getString("itemTitle"));
				searchResult.setTypeOfAuction(
					resultSet.getString("typeOfAuction"));
				searchResult.setItemURL(resultSet.getString("itemURL"));
				searchResult.setEndingTime(
					new Date(resultSet.getLong("endingTime")));
				searchResult.setAuctionPrice(
					resultSet.getDouble("auctionPrice"));
				searchResult.setFixedPrice(resultSet.getDouble("fixedPrice"));

				return searchResult;
			}
			else {
				return new SearchResultModel();
			}
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error(
				"Cannot find search query for search result ID: " +
					searchResultId);

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	@Override
	public List<SearchResultModel> getSearchResults() throws SQLException {
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getDatabaseConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_GET_SEARCH_RESULTS_SQL);

			resultSet = preparedStatement.executeQuery();

			List<SearchResultModel> searchResults =
				new ArrayList<SearchResultModel>();

			while (resultSet.next()) {
				SearchResultModel searchResult = new SearchResultModel();

				searchResult.setSearchResultId(
					resultSet.getInt("searchResultId"));
				searchResult.setSearchQueryId(
					resultSet.getInt("searchQueryId"));
				searchResult.setItemId(resultSet.getString("itemId"));
				searchResult.setItemTitle(resultSet.getString("itemTitle"));
				searchResult.setTypeOfAuction(
					resultSet.getString("typeOfAuction"));
				searchResult.setItemURL(resultSet.getString("itemURL"));
				searchResult.setEndingTime(
					new Date(resultSet.getLong("endingTime")));
				searchResult.setAuctionPrice(
					resultSet.getDouble("auctionPrice"));
				searchResult.setFixedPrice(resultSet.getDouble("fixedPrice"));

				searchResults.add(searchResult);
			}

			return searchResults;
		}
		catch (DatabaseConnectionException | SQLException exception) {
			_log.error("Unable to return all search results.");

			throw new SQLException(exception);
		}
		finally {
			if (connection != null) {
				connection.close();
			}

			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		SearchResultDAOImpl.class);

	private static final String _ADD_SEARCH_RESULT_SQL =
		"INSERT INTO SearchResult(searchQueryId, itemId, itemTitle, " +
			"typeOfAuction, itemURL, endingTime, auctionPrice, " +
				" fixedPrice) VALUES(?, ?, ? ,?, ?, ?, ?, ?)";

	private static final String _DELETE_SEARCH_RESULT_SQL =
		"DELETE FROM SearchResult WHERE searchResultId = ?";

	private static final String _DELETE_SEARCH_RESULT_WITH_SEARCH_QUERY_SQL =
		"DELETE FROM SearchResult WHERE searchQueryId = ?";

	private static final String _GET_SEARCH_RESULT_SQL =
		"SELECT * FROM SearchResult WHERE searchQueryId = ?";

	private static final String _GET_SEARCH_RESULTS_SQL =
		"SELECT * FROM SearchResult";
}
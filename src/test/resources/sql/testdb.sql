DROP TABLE IF EXISTS SearchQuery, SearchResult;

CREATE TABLE IF NOT EXISTS SearchQuery(
	searchQueryId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	searchQuery VARCHAR(100) null
);

CREATE TABLE IF NOT EXISTS SearchResult(
	searchResultId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	itemId INT NOT NULL,
	itemTitle VARCHAR(100),
	typeOfAuction VARCHAR(25),
	URL VARCHAR(50),
	endingTime DATE,
	auctionPrice DOUBLE,
	fixedPrice DOUBLE
);
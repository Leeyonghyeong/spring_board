package org.spring.searching;

import org.spring.paging.Criteria;

public class SearchCriteria extends Criteria {
	
	// 검색 조건 변수
	private String searchType;
	
	// 검색어 변수
	private String keyword;
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + "]";
	}
	
	
}

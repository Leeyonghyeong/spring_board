package org.spring.paging;

public class Criteria {
	
	// default 페이지 번호
	private int page = 1;
	
	// default 페이지에 보여질 게시물 갯수
	private int perPageNum = 10;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}
	
	// SQL Mapper 용 method
	public int getPageStart() {
		
		return (this.page -1) * perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
	
	
	
}

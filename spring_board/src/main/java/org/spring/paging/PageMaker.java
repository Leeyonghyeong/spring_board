package org.spring.paging;

import org.spring.searching.SearchCriteria;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	
	// 모든 글 갯수 및 검색조건에 맞는 글 갯수
	private int totalCount;
	
	// 시작 페이지 ex) displayPageNum 이 10일 경우 총 10페이지씩 보여지게 되며 총 페이지가 32페이지일 경우 startPage는 1, 11, 21, 31 의 수를 갖게 된다.
	private int startPage;
	
	// 끝 페이지 번호 ex) 총 페이지 갯수가 32일 경우 endPage는 32의 수를 갖게된다.
	private int endPage;
	
	// 만약 현제 보고 있는 페이지가 1페이지가 아닐 경우 뒤로 가기 버튼 활성화를 위한 bool 변수
	private boolean prev;
	
	// 만약 현제 보고 있는 페이지가 endPage가 아닐 경우 앞으로 가기 버튼 활성화를 위한 bool 변수
	private boolean next;
	
	// 현재 보고 있는 페이지에서 startPage - 1 로 이동 하기 위한 bool 변수
	private boolean start;
	
	// 현재 보고 있는 페이지에서 다음 startPage가 있다면 startPage로 이동 하기 위한 bool 변수
	private boolean end;
	
	// 한 화면에 처리할 페이징 갯수
	private int displayPageNum = 10;
	
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	
	private void calcData() {
		/* 
		 * math.ceil 함수를 이용해 보여질 페이지 갯수를 계산
		 * ex) cri.getPage = 는 현재 보고 있는 페이지를 나타내며 만약 현재 보고 있는 페이지가 13페이지 일 경우
		 * 13 / 10 = 1.3 이지만 ceil 함수를 이용해 2이라는 값을 출력 후 2 * 10에 연산에 따라 endPage는 20을 할당 받게 됨
		 */ 
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		
		// 위에서 예를 들어 계산한 endPage가 10이라면 (20 - 10) + 1 = 11 을 할당 받게 되는 변수
		startPage = (endPage - displayPageNum) + 1;
		
		/* 
		 * 총 게시물의 갯수를 파악해 실제 출력되야할 최종 페이값을 계산
		 * ex) totalCount = 123 이라면 123 / 10 = 12.3 이지만 ceil 함수를 통해 13이라는 결과를 출력
		 */
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		
		// 위에서 구한 endPage = 20 이고 tempEndPage = 13 이기 때문에 실제 endPage는 20까지 출력 하는게 아니라 tempEndPage 인 13을 할당해 최종 13페이지 까지만 출력
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// 각 페이지의 startPage - 1로 이동하기 위해 현재 페이지에서 구한 startPage 변수가 1이라면 false를 반환 아니면 true를 반환함
		// 현재 위에서 예를 들은 startPage는 11이므로 true를 반환하며 10페이지로 이동함
		start = startPage == 1 ? false : true;
		
		/* 
		 * 다음 시작하는 페이지로의 이동을 위해 true, false를 반환
		 * ex) 현재 보고 있는 페이지가 3페이지이고 위에서 계산한 결과를 예로 endPage는 10의 값을 갖고 있음
		 * getPerPageNum은 기본값으로 10을 할당 받고 있으므로 10 * 10 = 100 이 totalCount = 123 보다 작으므로 true를 반환하며
		 * 실제 이동하는 페이지는 endPage + 1 = 11 페이지로 이동하게 됨
		 */
		end = endPage * cri.getPerPageNum() >= totalCount ? false : true;
		
		// 현재 보고 있는 페이지가 1페이지가 아니라면 이전페이지로 이동하기 위한 버튼 10페이지, 9페이지, 8페이지 .....
		prev = cri.getPage() == 1 ? false : true;
		
		// 현재 보고 있는 페이지가 마지막 페이지가 아니라면 다음페이지로 이동하기 위한 버튼 1페이지, 2페이지, 3페이지 .....
		next = cri.getPage() == tempEndPage ? false : true;
	}
	
	public String makeQuery(int page) {
		// 현재 보고 있는 페이지, 한페이지의 표시될 게시글 갯수, 검색 조건, 검색어 등을 저장해서 갖고 다닐 uri parameter
		UriComponents uri = 
				UriComponentsBuilder.newInstance()
				.queryParam("page",	page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
				.queryParam("keyword", ((SearchCriteria)cri).getKeyword())
				.build();
		
		return uri.toString();
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public Criteria getCri() {
		return cri;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}
	
	

}

package com.ezen.springboard.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.springboard.entity.Board;

@Transactional //@Modifying이 일어난 메소드가 실행 된 후 바로 트랜잭션이 일어날 수 있도록
			   //Repository 자체를 @Transactional로 선언
public interface BoardRepository extends JpaRepository <Board, Integer> {
	//JPA 규칙 메소드 
	//find, read, query, count, get
	//Entity의 변수명으로 조건을 달아줘야함.
	//boardNo으로 List<boardFile>를 찾고자 한다면 
	//findbyBoardBoardNo(int boardNo);-> 메소드만 선언해도 쿼리 생성 
	// => SELECT * FROM T_BOARD_FILE_TEST WHERE BOARD_NO = :boardNo 
	//findByBoard(Board board);  // 조인 컬럼을 명시했기 때문에 SELECT * FROM T_BOARD_FILE_TEST WHERE BOARD_NO = :boardNo 쿼리 생성
	//AND, OR 두 컬럼 사이에 And Or를 붙여준다. 
	//boardTitle, boardContent로 검색 할 때
	//findByBoardTitleAnd(Or)BoardContent(Board board)
	// => SELECT * FROM T_BOARD_TEST
	//    WHERE BOARD_TITLE = :boardTitle AND BOARD_CONTENT = :boardContent
	//Containing == like '%keyword%'
	
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_TITLE LIKE '%searchKeyword%'
	Page<Board> findByBoardTitleContaining(String searchKeyword, Pageable pageable);
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_Content LIKE '%searchKeyword%'
	Page<Board> findByBoardContentContaining(String searchKeyword, Pageable pageable);
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_Writer LIKE '%searchKeyword%'
	Page<Board> findByBoardWriterContaining(String searchKeyword, Pageable pageable); // 파람 지정 안해도 됨...
	
	// SELECT * FROM T_BOARD_TEST 
	// WHERE BOARD_TITLE LIKE '%searchKeyword1%'
	// OR BOARD_Content LIKE '%searchKeyword2%'
	// OR BOARD_Writer LIKE '%searchKeyword3%'
	
	Page<Board> findByBoardTitleContainingOrBoardContentContainingOrBoardWriterContaining(
			String searchKeyword1, String searchKeyword2, String searchKeyword3, Pageable pageable);
	
	@Modifying //데이터의 변경이 일어나는 @Query을 사용할 때는 @Modifying을 붙여준다.
	@Query(value="UPDATE T_BOARD SET BOARD_CNT = BOARD_CNT + 1 WHERE BOARD_NO = :boardNo", nativeQuery=true)
	void updateBoardCnt(@Param("boardNo") int boardNo);
}

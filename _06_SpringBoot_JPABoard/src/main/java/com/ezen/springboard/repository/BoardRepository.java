package com.ezen.springboard.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.springboard.entity.Board;

@Transactional //@Modifying이 일어난 메소드가 실행된 후 바로 트랜잭션이 일어날 수 있도록 
               // Repository 자체를 @Transactional로 선언
public interface BoardRepository extends JpaRepository<Board, Integer>{
	// JPA 규칙 메소드 
	// find, read, query, count, get
	// 엔티티의 변수명으로 조건을 달아줘야 함
	// boardNo으로 List<BoardFile> 검색하려면
	// findbyBoardBoardNo(int boardNo); 
	//   => SELECT * FROM T_BOARD_FILE_TEST WHERE BOARD_NO = : boardNo
	// findbuBoard(Board board);
	// AND, OR 두 컬럼사이에 And Or를 붙여준다
	// boardTitle, boardContent로 검색할 때
	// findbyBoardTitleAnd(Or)BoardContent(Board board)
	//  => SELECT * FROM T_BOARD_TEST WHERE BOARD_TITLE = :boardTitle AND BOARD_CONTENT = :boardContent
	// Containing == like '%keyword%'
	
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_TITLE LIKE '%searchKeyword%'
	List<Board> findByBoardTitleContaining(String searchKeyword);
	
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_CONTENT LIKE '%searchKeyword%'
	List<Board> findByBoardContentContaining(String searchKeyword);
	
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_WRITER LIKE '%searchKeyword%'
	List<Board> findByBoardWriterContaining(String searchKeyword);
	
	// SELECT * FROM T_BOARD_TEST 
	// 	 WHERE BOARD_TITLE LIKE '%searchKeyword%'
	//   OR BOARD_CONTENT LIKE '%searchKeyword%'
	//   OR WHERE BOARD_WRITER LIKE '%searchKeyword%'
	List<Board> findByBoardTitleContainingOrBoardContentContainingOrBoardWriterContaining(String searchKeyword1, String searchKeyword2, String searchKeyword3);
	
	@Modifying // 데이터의 변경이 일어나는 @Query
	@Query(value="UPDATE T_BOARD SET BOARD_CNT = BOARD_CNT + 1 WHERE BOARD_NO = :boardNo", nativeQuery=true)
	void updateBoardCnt(@Param("boardNo")int boardNo);
	
	

}

package com.ezen.springboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.springboard.entity.Board;
import com.ezen.springboard.entity.BoardFile;
import com.ezen.springboard.entity.BoardFileId;

public interface BoardFileRepository extends JpaRepository<BoardFile, BoardFileId>{
    // @Query: 워나는 쿼리를 작성할 수 있는 어노테이션
	// nativeQuery: true로 설정하면 원하는대로 쿼리 작성, 메소드명도 JPA 규칙에서 벗어날 수 있다.
	// value: 쿼리 작성
	@Query(value="SELECT IFNULL(MAX(F.BOARD_FILE_NO), 0) + 1 FROM T_BOARD_FILE F WHERE F.BOARD_NO=:boardNo", nativeQuery=true)
	// 서비스임플에서 넘겨주는 파라미터의 변수명이 받아주는 변수의 이름과 다를 때 해당 파라미터 이름을 명시
	// 매퍼나 레포지토리에 여러 개의 파라미터를 보낼 때 @Param 어노테이션 사용
	int getMaxFileNo(@Param("boardNo") int boardNo);
	
	// select * from t_board_file
	// where boardMno =:boardNo
	List<BoardFile> findByBoard(Board board);
}
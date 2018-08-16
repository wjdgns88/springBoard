package kr.or.ddit.board.dao;

import java.util.List;

import javax.annotation.Resource;

import kr.or.ddit.board.model.BoardVo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("boardDao")
public class BoardDao implements BoardDaoInf {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;

	@Override
	public List<BoardVo> selectAllBoard() {
		return template.selectList("board.selectAllBoard");
	}

	@Override
	public int insertBoard(BoardVo board) {
		return template.insert("board.insertBoard", board);
	}

	@Override
	public int boardUpdate(BoardVo board) {
		return template.update("board.boardUpdate", board);
	}

}

package kr.or.ddit.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.board.dao.BoardDao;
import kr.or.ddit.board.dao.BoardDaoInf;
import kr.or.ddit.board.model.BoardVo;

@Service("boardService")
public class BoardService implements BoardServiceInf {

	@Resource(name="boardDao")
	BoardDaoInf boardDao;
	
	@Override
	public List<BoardVo> selectAllBoard() {
		return boardDao.selectAllBoard();
	}

	@Override
	public int insertBoard(BoardVo board) {
		return boardDao.insertBoard(board);
	}

	@Override
	public int boardUpdate(BoardVo board) {
		return boardDao.boardUpdate(board);
	}

}

package kr.or.ddit.board.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceInf;
import kr.or.ddit.student.model.StudentVo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@RequestMapping("/board")
@Controller("boardController")
public class BoardController {
	
	@Resource(name="boardService")
	BoardServiceInf boardService;
	
	@RequestMapping("/boardList")
	public String boardList(Model model){
		
		List<BoardVo> boardList = boardService.selectAllBoard();
		model.addAttribute("boardList", boardList);
		return "board/board";
	}
	
	@RequestMapping("/boardInsert")
	public String boardInsert(@RequestParam("boardName")String upName,
							  @RequestParam("select")String delete,
							  HttpSession session,
							  Model model) throws UnsupportedEncodingException{
		
		StudentVo studentVo = (StudentVo) session.getAttribute("studentVo");
		if(upName != ""){
			upName = new String(upName.getBytes("8859_1"),"UTF-8");
			
			BoardVo board = new BoardVo();
			board.setBoard_name(upName);
			board.setBoard_dt(new Date());
			board.setId(studentVo.getId());
			board.setBoard_delet(delete.charAt(0));		// 옵션값 가져와서 넣어야됨
			boardService.insertBoard(board);
		}
		return boardList(model);
	}
	
	@RequestMapping("/boardUpdate")
	public String boardUpdate(@RequestParam("upboardName")String upName,
			  @RequestParam("select")String delete,
			  @RequestParam("boardnum")int boardId,
			  HttpSession session,
			  Model model) throws UnsupportedEncodingException{
		
		StudentVo studentVo = (StudentVo) session.getAttribute("studentVo");
		upName = new String(upName.getBytes("8859_1"),"UTF-8");		
		BoardVo board = new BoardVo();
		board.setBoard_id(boardId);
		board.setBoard_name(upName);
		board.setId(studentVo.getId());
		board.setBoard_dt(new Date());
		board.setBoard_delet(delete.charAt(0));
		
		boardService.boardUpdate(board);
		
		return boardList(model);
	}
	
}

package kr.or.ddit.student.web;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.BoardServiceInf;
import kr.or.ddit.student.model.StudentVo;
import kr.or.ddit.student.service.StudentServiceInf;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/login")
@Controller("loginController")
@SessionAttributes({"studentVo","boardList"})
public class LoginController {
	
	@Resource(name="boardService")
	BoardServiceInf boardService;
	
	@Resource(name="studentService")
	StudentServiceInf studentService;
	
	@RequestMapping("/view")
	public String login(){
		return "login/login";
	}
	
	
	@RequestMapping("/loginProcess")
	public String loginProcess(StudentVo userVo, Model model){
		StudentVo studentVo = studentService.getOneStudent(userVo.getStd_id());
		
		List<BoardVo> boardList = boardService.selectAllBoard();
		
		if(studentVo == null){
			return "login/login";
		}
		
		if(studentVo.validateUser(userVo.getStd_id(), userVo.getPass())){
			model.addAttribute("studentVo", studentVo);
			model.addAttribute("boardList", boardList);
			return "jsp/main";
		}else{
			return "login/login";
		}
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		
		return login();
	}
	
	
	
}

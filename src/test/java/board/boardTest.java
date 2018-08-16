package board;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.BoardServiceInf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:kr/or/ddit/config/spring/datasource_dev.xml",
								"classpath:kr/or/ddit/config/spring/root-context.xml",
								"classpath:kr/or/ddit/config/spring/transaction.xml"})
public class boardTest {

	
	@Resource(name="boardService")
	private BoardServiceInf boardService;
	
	@Before
	public void setup(){
		//populator : 스프링에서 제공
		//datasource(db 연결정보), 초기화 스크립트
		
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		datasource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
		datasource.setUsername("boardtest");
		datasource.setPassword("1234");
		
		//초기화 스크립트(init.sql)
		
		//poplucator 생성
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("kr/or/ddit/config/db/init.sql"));
		
		DatabasePopulatorUtils.execute(populator, datasource);
		
	}

	@Test
	public void selectAllBoardTest() {
		/*** Given ***/

		/*** When ***/
		List<BoardVo> boardList = boardService.selectAllBoard();
		for (BoardVo vo : boardList) {
			System.out.println(vo);
		}
		/*** Then ***/
		assertEquals(1, boardList.size());

	}

	@Test
	public void insertBoardTest() {
		/*** Given ***/
		BoardVo boardVo = new BoardVo();
		boardVo.setId(2);
		boardVo.setBoard_dt(new Date());
		boardVo.setBoard_name("게시판 테스트");
		boardVo.setBoard_delet('1');

		/*** When ***/
		int cnt = boardService.insertBoard(boardVo);

		/*** Then ***/
		assertEquals(1, cnt);
	}
	
	@Test
	public void boardUpdate(){
		/***Given***/
		BoardVo boardVo = new BoardVo();
		boardVo.setId(2);
		boardVo.setBoard_delet('1');
		boardVo.setBoard_dt(new Date());
		boardVo.setBoard_id(78);
		boardVo.setBoard_name("수정 테스트");

		/***When***/
		int cnt = boardService.boardUpdate(boardVo);
		/***Then***/
		assertEquals(1, cnt);
	}

}

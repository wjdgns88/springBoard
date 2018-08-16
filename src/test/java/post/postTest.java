package post;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import kr.or.ddit.post.model.PostVo;
import kr.or.ddit.post.service.PostServiceInf;

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
public class postTest {
	
	@Resource(name="postService")
	private PostServiceInf postService;
	
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
	public void postServiceBeanTest(){
		assertNotNull(postService);
	}

	@Test
	public void postInsertTest() {
		/***Given***/
		PostVo postVo = new PostVo();
		postVo.setBoard_id(1);
		postVo.setId(2);
		postVo.setPost_dt(new Date());
		postVo.setPost_content("테스트");
		postVo.setPost_delet('1');
		postVo.setPost_title("테스트제목");
		
		/***When***/
		
		int cnt = postService.insertPost(postVo);
		
		/***Then***/
		assertEquals(1, cnt);
	}

}

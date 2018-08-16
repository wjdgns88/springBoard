package tag;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.or.ddit.tag.model.TagVo;
import kr.or.ddit.tag.service.TagServiceInf;

import org.apache.commons.dbcp2.BasicDataSource;
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
public class tagTest {

	@Resource(name="tagService")
	private TagServiceInf tagService;
	
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
	public void selectTagTest() {
		/***Given***/
		

		/***When***/
		List<TagVo> tagList = tagService.selectTag(1);
		
		/***Then***/
		assertEquals(0, tagList.size());

	}
	
	@Test
	public void insertTag(){
		/***Given***/
		
		TagVo tag = new TagVo();
		tag.setId(2);
		tag.setTag_content("댓글 추가 입니다");
		tag.setTag_delet('1');
		tag.setTag_dt(new Date());
		tag.setPost_id(2);

		/***When***/
		int cnt = tagService.insertTag(tag);
		/***Then***/
		assertEquals(cnt, 1);

	}

	
	@Test
	public void deleteTag(){
		/***Given***/
		

		/***When***/
		int cnt = tagService.deleteTag(3);
		/***Then***/
		assertEquals(cnt, 1);

	}

}

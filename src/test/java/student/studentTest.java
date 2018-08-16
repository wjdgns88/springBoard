package student;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import kr.or.ddit.student.dao.StudentDaoInf;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:kr/or/ddit/config/spring/datasource.xml",
								"classpath:kr/or/ddit/config/spring/root-context.xml",
								"classpath:kr/or/ddit/config/spring/transaction.xml"})
public class studentTest {
	
	@Resource(name="studentDao")
	StudentDaoInf studentDao;

	@Test
	public void test() {
		assertNotNull(studentDao);
	}

}

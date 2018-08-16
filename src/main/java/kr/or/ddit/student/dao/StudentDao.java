package kr.or.ddit.student.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.student.model.StudentVo;

@Repository("studentDao")
public class StudentDao implements StudentDaoInf {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;

	/**
	 * 
	 * Method : selectAllStudents
	 * 최초작성일 : 2018. 7. 9.
	 * 작성자 : PC01
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 학생 정보를 조회한다.
	 */
	@Override
	public List<StudentVo> selectAllStudents() {
		List<StudentVo> studentList = template.selectList("student.selectAllStudents");
		
		return studentList;
	}

	/**
	 * 
	 * Method : selectOneStudent
	 * 최초작성일 : 2018. 7. 10.
	 * 작성자 : PC01
	 * 변경이력 :
	 * @param id
	 * @return
	 * Method 설명 : 아이디로 특정 학생 정보를 조회한다.
	 */
	@Override
	public StudentVo getStudent(int id) {
		StudentVo oneStudent = template.selectOne("student.selectOneStudent", id);
		
		return oneStudent;
	}

	@Override
	public StudentVo getStudent(StudentVo studentvo) {
		StudentVo oneStudentVo = template.selectOne("student.selectOneStudentVo", studentvo);
		
		return oneStudentVo;
	}

	/**
	* Method : getStudentPageList
	* 최초작성일 : 2018. 7. 10.
	* 작성자 : PC01
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 학생 페이지 리스트 조회
	*/
	@Override
	public List<StudentVo> getStudentPageList(Map<String, Integer> map) {
		List<StudentVo> studentList = template.selectList("student.getStudentPageList",map);
		
		return studentList;
	}

	/**
	* Method : getStudentTotCnt
	* 최초작성일 : 2018. 7. 11.
	* 작성자 : PC01
	* 변경이력 :
	* @return
	* Method 설명 : 전체 학생 수 조회
	*/
	@Override
	public int getStudentTotCnt() {
		int studenttotcnt = template.selectOne("student.getStudentTotCnt");
		
		return studenttotcnt;
	}

	/**
	* Method : studentUpdate
	* 최초작성일 : 2018. 7. 17.
	* 작성자 : PC01
	* 변경이력 :
	* @param studentVo
	* @return
	* Method 설명 : 학생 정보 업데이트
	*/
	@Override
	public int studentUpdate(StudentVo studentVo) {
		int updateCnt = template.update("studentUpdate", studentVo);
		return updateCnt;
	}

	@Override
	public StudentVo getOneStudent(String std_id) {
		StudentVo student = template.selectOne("student.getOneStudent",std_id);
		return student;
	}


}

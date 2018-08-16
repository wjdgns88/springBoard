package kr.or.ddit.file.dao;

import java.util.List;

import javax.annotation.Resource;

import kr.or.ddit.file.model.FileVo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("fileDao")
public class FileDao implements FileDaoInf {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;

	@Override
	public List<FileVo> selectFile(int post_id) {
		return template.selectList("file.selectFile",post_id);
	}

	@Override
	public int insertFile(FileVo fileVo) {
		return template.insert("file.insertFile", fileVo);
	}

	@Override
	public int updateFile(FileVo fileVo) {
		return template.insert("file.updateFile", fileVo);
	}
	
}

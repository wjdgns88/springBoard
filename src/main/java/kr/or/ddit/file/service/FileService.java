package kr.or.ddit.file.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.file.dao.FileDao;
import kr.or.ddit.file.dao.FileDaoInf;
import kr.or.ddit.file.model.FileVo;

@Service("fileService")
public class FileService implements FileServiceInf {
	
	@Resource(name="fileDao")
	private FileDaoInf fileDao;

	@Override
	public List<FileVo> selectFile(int post_id) {
		return fileDao.selectFile(post_id);
	}

	@Override
	public int insertFile(FileVo fileVo) {
		return fileDao.insertFile(fileVo);
	}

	@Override
	public int updateFile(FileVo fileVo) {
		return fileDao.updateFile(fileVo);
	}
	

}

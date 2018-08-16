package kr.or.ddit.tag.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.tag.dao.TagDao;
import kr.or.ddit.tag.dao.TagDaoInf;
import kr.or.ddit.tag.model.TagVo;

@Service("tagService")
public class TagService implements TagServiceInf {

	@Resource(name="tagDao")
	TagDaoInf tagDao;
	
	@Override
	public List<TagVo> selectTag(int post_id) {
		return tagDao.selectTag(post_id);
	}

	@Override
	public int insertTag(TagVo tag) {
		return tagDao.insertTag(tag);
	}

	@Override
	public int deleteTag(int tag_id) {
		return tagDao.deleteTag(tag_id);
	}

}

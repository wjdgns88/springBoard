package kr.or.ddit.tag.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.tag.model.TagVo;

@Repository("tagDao")
public class TagDao implements TagDaoInf {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;
	
	@Override
	public List<TagVo> selectTag(int post_id) {
		return template.selectList("tag.selectTag", post_id);
	}

	@Override
	public int insertTag(TagVo tag) {
		return template.insert("tag.insertTag", tag);
	}

	@Override
	public int deleteTag(int tag_id) {
		return template.update("tag.deleteTag", tag_id);
	}

}

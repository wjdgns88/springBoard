package kr.or.ddit.post.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.or.ddit.post.model.PostVo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("postDao")
public class PostDao implements PostDaoInf {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;
	
	@Override
	public List<PostVo> searchPost(int board_id) {
		return template.selectList("post.searchPost", board_id);
	}

	@Override
	public List<PostVo> getPostPageList(Map<String, Integer> map) {
		return template.selectList("post.getPostPageList", map);
	}

	@Override
	public int getPostTotCnt(int boardId) {
		return template.selectOne("post.getPostTotCnt", boardId);
	}

	@Override
	public PostVo selectPost(int post_id) {
		return template.selectOne("post.selectPost", post_id);
	}

	@Override
	public int insertPost(PostVo postVo) {
		return template.insert("post.insertPost", postVo);
	}

	@Override
	public int postUpdate(PostVo postVo) {
		return template.update("post.postUpdate", postVo);
	}

	@Override
	public int postdelete(PostVo postVo) {
		return template.update("post.postdelete", postVo);
	}

	@Override
	public int insertPostReply(PostVo postVo) {
		return template.insert("post.insertPostReply", postVo);
	}

	@Override
	public int nowSequence() {
		return template.selectOne("post.nowSequence");
	}

}

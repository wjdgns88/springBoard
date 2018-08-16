package kr.or.ddit.post.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.post.dao.PostDao;
import kr.or.ddit.post.dao.PostDaoInf;
import kr.or.ddit.post.model.PostVo;

@Service("postService")
public class PostService implements PostServiceInf {
	
	@Resource(name="postDao")
	private PostDaoInf postDao;
	
	@Override
	public List<PostVo> searchPost(int board_id) {
		return postDao.searchPost(board_id);
	}

	@Override
	public Map<String, Object> getPostPageList(Map<String, Integer> map) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<PostVo> postList = postDao.getPostPageList(map);
		resultMap.put("postList", postList);
		
		int totCnt = postDao.getPostTotCnt(map.get("boardId"));
		resultMap.put("totCnt", totCnt);
		
		int page = map.get("page");
		int pageSize = map.get("pageSize");
		
		resultMap.put("pageNavi", makePageNavai(page, pageSize, totCnt, map.get("boardId")));
		return resultMap;
	}
	
	/**
	* Method : makePageNavai
	* 최초작성일 : 2018. 7. 20.
	* 작성자 : PC01
	* 변경이력 :
	* @param page
	* @param pageSize
	* @param totCnt
	* @return
	* Method 설명 : 페이지 네비게이션 문자 생성
	*/
	private String makePageNavai(int page, int pageSize, int totCnt,int boardId){
		int cnt = totCnt / pageSize;
		int mod = totCnt % pageSize;
		
		if(mod > 0)
			cnt++;
		
		StringBuffer pageNaviStr = new StringBuffer();
		int prevPage = page == 1 ? 1 : page-1;
		int nextPage = page == cnt ? page : page+1;
		
		pageNaviStr.append("<li><a href=\"/post/postList?page=1"+ "&pageSize=" + pageSize + "&boardId=" + boardId + "\" aria-label=\"Previous\"><span aria-hidden=\"true\">≪</span></a></li>");
		pageNaviStr.append("<li><a href=\"/post/postList?page=" + prevPage + "&pageSize=" + pageSize + "&boardId=" + boardId + "\" aria-label=\"Previous\"><span aria-hidden=\"true\">＜</span></a></li>");
		
		for(int i = 1; i <= cnt; i++){
			//studentList?page=1&pageSize=10
			String activeClass ="";
			if(i==page)
				activeClass = "class=\"active\"";
			
			pageNaviStr.append("<li "+activeClass + "><a href=\"/post/postList?page="+i+"&pageSize=" + pageSize + "&boardId=" + boardId + "\">"+i+"</a></li>");
		}
		
		pageNaviStr.append("<li><a href=\"/post/postList?page=" + nextPage + "&pageSize=" + pageSize + "&boardId=" + boardId + "\" aria-label=\"Next\"><span aria-hidden=\"true\">＞</span></a></li>");
		pageNaviStr.append("<li><a href=\"/post/postList?page=" + cnt + "&pageSize=" + pageSize + "&boardId=" + boardId + "\" aria-label=\"Next\"><span aria-hidden=\"true\">≫</span></a></li>");
		
		
		return pageNaviStr.toString();
	}

	@Override
	public PostVo selectPost(int post_id) {
		return postDao.selectPost(post_id);
	}

	@Override
	public int insertPost(PostVo postVo) {
		return postDao.insertPost(postVo);
	}

	@Override
	public int postUpdate(PostVo postVo) {
		return postDao.postUpdate(postVo);
	}

	@Override
	public int postdelete(PostVo postVo) {
		return postDao.postdelete(postVo);
	}

	@Override
	public int insertPostReply(PostVo postVo) {
		return postDao.insertPostReply(postVo);
	}

	@Override
	public int nowSequence() {
		return postDao.nowSequence();
	}

}

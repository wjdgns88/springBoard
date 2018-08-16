package kr.or.ddit.tag.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import kr.or.ddit.post.model.PostVo;
import kr.or.ddit.post.web.PostController;
import kr.or.ddit.student.model.StudentVo;
import kr.or.ddit.tag.model.TagVo;
import kr.or.ddit.tag.service.TagServiceInf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/tag")
@Controller("tagController")
public class TagController {
	
	@Resource(name="tagService")
	private TagServiceInf tagService;
	
	@Resource(name="postController")
	PostController postController;
	
	@RequestMapping("tagInsert")
	public String tagInsert(@RequestParam(value="delete", defaultValue="0")String delete,
							Model model,
							HttpSession session,
							@RequestParam(value="tagnum", defaultValue="0")int tag,
							@RequestParam(value="tag", defaultValue="")String tagContent) throws UnsupportedEncodingException{

		PostVo postVo = (PostVo) session.getAttribute("postVo");
		if(!delete.equals("0")){
			tagService.deleteTag(tag);
			
		}else{
	
			StudentVo studentVo = (StudentVo) session.getAttribute("studentVo");
	
			if (tagContent != null) {
				tagContent = new String(tagContent.getBytes("8859_1"), "UTF-8");
				TagVo addTag = new TagVo();
				addTag.setTag_content(tagContent);
				addTag.setTag_dt(new Date());
				addTag.setId(studentVo.getId());
				addTag.setPost_id(postVo.getPost_id());
				addTag.setTag_delet('1');
	
				tagService.insertTag(addTag);
			}
		}
		
		
		
		return postController.postDetail(postVo.getPost_id(), model);
	}
}

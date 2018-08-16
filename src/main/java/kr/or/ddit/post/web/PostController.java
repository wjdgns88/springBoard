package kr.or.ddit.post.web;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.or.ddit.file.model.FileUpVo;
import kr.or.ddit.file.model.FileVo;
import kr.or.ddit.file.service.FileServiceInf;
import kr.or.ddit.file.web.FileUtil;
import kr.or.ddit.post.model.PostVo;
import kr.or.ddit.post.service.PostServiceInf;
import kr.or.ddit.student.model.StudentVo;
import kr.or.ddit.student.service.StudentServiceInf;
import kr.or.ddit.tag.model.TagVo;
import kr.or.ddit.tag.service.TagServiceInf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/post")
@Controller("postController")
@SessionAttributes({ "boardId", "postVo", "fileList" })
public class PostController {

	@Resource(name = "postService")
	PostServiceInf postService;

	@Resource(name = "studentService")
	StudentServiceInf studentService;

	@Resource(name = "fileService")
	FileServiceInf fileService;

	@Resource(name = "tagService")
	TagServiceInf tagService;

	@RequestMapping("/postList")
	public String postList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "boardId") int boardId, Model model) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("page", page);
		map.put("pageSize", pageSize);
		map.put("boardId", boardId);

		Map<String, Object> resultMap = postService.getPostPageList(map);

		model.addAllAttributes(resultMap);
		model.addAttribute("boardId", boardId);

		return "post/postList";
	}

	@RequestMapping("postInsert")
	public String postInsert(@RequestParam("title") String title,
			@RequestParam("contents") String contents,
			FileUpVo files,
			HttpSession session,
			Model model) throws IOException,
			ServletException {

		StudentVo student = (StudentVo) session.getAttribute("studentVo");
		int boardId = (int) session.getAttribute("boardId");
		PostVo postVo = new PostVo();
		postVo.setBoard_id(boardId);
		postVo.setPost_title(title);
		postVo.setPost_content(contents);
		postVo.setPost_dt(new Date());
		postVo.setId(student.getId());
		postVo.setPost_delet('1');

		postService.insertPost(postVo);
		for (MultipartFile f : files.getFiles()) {
			FileVo file = new FileVo();
			String pic = f.getOriginalFilename();
			String picpath = FileUtil.fileUploadPath;
			String picname = UUID.randomUUID().toString();
			System.out.println(pic);
			if (pic != "") {
				file.setFile_name(picname);
				file.setFile_path(picpath);
				file.setFile_up(pic);
				
				
				fileService.insertFile(file);
				
				File downFile = new File(picpath + File.separator + picname);
				f.transferTo(downFile);
			}
		}
		int id = postService.nowSequence();

		return postDetail(id, model);
	}

	@RequestMapping("/postDetail")
	public String postDetail(@RequestParam("id") int id, Model model) {

		PostVo postVo = postService.selectPost(id);

		List<FileVo> fileList = fileService.selectFile(postVo.getPost_id());
		List<TagVo> tagList = tagService.selectTag(postVo.getPost_id());
		List<StudentVo> student = studentService.selectAllStudents();
		model.addAttribute("student", student);
		model.addAttribute("tagList", tagList);

		model.addAttribute("postVo", postVo);
		model.addAttribute("fileList", fileList);

		return "post/postDetail";
	}

	@RequestMapping("/postUpdate")
	public String postUpdate(@RequestParam("id") int id,
			@RequestParam("btn") String btn, Model model) {

		PostVo postVo = postService.selectPost(id);
		if (btn.equals("updata")) {

			List<FileVo> fileVo = fileService.selectFile(id);
			model.addAttribute("fileVo", fileVo);
			model.addAttribute("postVo", postVo);
			
			return "post/postUpdate";

		} else if (btn.equals("delete")) {
			postVo.setPost_delet('0');
			postService.postdelete(postVo);

			return postList(1, 10, postVo.getBoard_id(), model);
					
		} else {
			model.addAttribute("postVo", postVo);
			return "post/postReply";

		}

	}
	
	@RequestMapping("/postReply")
	public String postReply(PostVo post,
							Model model,
							FileUpVo files,
							HttpSession session) throws IOException, ServletException{
		
		post.setPost_title(new String(post.getPost_title().getBytes("8859_1"), "UTF-8")); 
		post.setPost_content(new String(post.getPost_content().getBytes("8859_1"), "UTF-8"));
		
		StudentVo student = (StudentVo) session.getAttribute("studentVo");
		int boardId = (int) session.getAttribute("boardId");
		PostVo postVo = new PostVo();
		postVo.setPost_id2(post.getPost_id2());
		postVo.setPost_group(post.getPost_group());
		postVo.setBoard_id(boardId);
		postVo.setPost_title(post.getPost_title());
		postVo.setPost_content(post.getPost_content());
		postVo.setPost_dt(new Date());
		postVo.setId(student.getId());
		postVo.setPost_delet('1');
		
		postService.insertPostReply(postVo);
		for (MultipartFile f : files.getFiles()) {
			FileVo upFile = new FileVo();
			String pic = FileUtil.getFileName(f.getOriginalFilename());
			String picpath = FileUtil.fileUploadPath;
			String picname = UUID.randomUUID().toString();
			
			if(pic != ""){
				upFile.setFile_name(picname);
				upFile.setFile_path(picpath);
				upFile.setFile_up(pic);
				
				fileService.insertFile(upFile);
				
				File downFile = new File(picpath + File.separator + picname);
				f.transferTo(downFile);
			}
		}
	
		return postList(1, 10, boardId, model);
	}
	
	@RequestMapping("/postRedate")
	public String postUpdate(@RequestParam("post_id") int id,
			@RequestParam("title") String title,
			@RequestParam("contents")String contents,
			FileUpVo files,
			Model model) throws IllegalStateException, IOException {
		
		PostVo postVo = postService.selectPost(id);
		
		postVo.setPost_id(id);
		postVo.setPost_title(title);
		postVo.setPost_content(contents);
		postVo.setPost_dt(new Date());
		
		postService.postUpdate(postVo);
		
		for (MultipartFile f: files.getFiles()) {
			FileVo file = new FileVo();
			String pic = f.getOriginalFilename();
			String picpath = FileUtil.fileUploadPath;
			String picname = UUID.randomUUID().toString();
			System.out.println(pic);
			if(pic != ""){
				file.setPost_id(id);
				file.setFile_name(picname);
				file.setFile_path(picpath);
				file.setFile_up(pic);
				
				fileService.updateFile(file);
				
				File downFile = new File(picpath + File.separator + picname);
				f.transferTo(downFile);
			}
		}
		return postDetail(id, model);
	}

}

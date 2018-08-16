package kr.or.ddit.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

@RequestMapping("fileDown")
@Controller("fileDownloadView")
public class FileDownloadView implements View {

	@Override
	public String getContentType() {
		return "aplication/octet-stream";
	}

	@RequestMapping("file")
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileup = request.getParameter("fileup");
		String fileName = request.getParameter("fileName");
		
		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileup +"\""); 
		response.setContentType("aplication/octet-stream");

		// D:\A_TeachingMaterial\7.JspSrpgin\fileUpload\sally.png
		String file = FileUtil.fileUploadPath + File.separator + fileName;

		// file 입출력을 위한 준비
		ServletOutputStream sos = response.getOutputStream();

		File f = new File(file);
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[512];
		int len = 0;
		while ((len = fis.read(b)) != -1) {
			sos.write(b, 0, len);
		}

		sos.close();
		fis.close();

	}

}

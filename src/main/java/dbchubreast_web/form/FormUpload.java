package dbchubreast_web.form;

import org.springframework.web.multipart.MultipartFile;

public class FormUpload {

	private MultipartFile file;

	public FormUpload() {
		super();
	}

	public FormUpload(MultipartFile file) {
		super();
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "FileUpload [file=" + (file==null ? null : file.getOriginalFilename()) + "]";
	}
	

}

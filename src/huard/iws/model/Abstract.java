package huard.iws.model;


public class Abstract {
	private int id;
	private byte [] file;
	private String contentType;
	private String filename;
	private String subject;
	private boolean methodPresentation;
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCleanFilename() {
		String cleanFileName=filename;
		if(cleanFileName.indexOf(".")>-1)
			cleanFileName=cleanFileName.substring(0,filename.indexOf("."));
		return cleanFileName.replace("_", " ");
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public boolean getMethodPresentation() {
		return methodPresentation;
	}
	public void setMethodPresentation(boolean methodPresentation) {
		this.methodPresentation = methodPresentation;
	}
	

}

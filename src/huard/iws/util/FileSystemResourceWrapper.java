package huard.iws.util;

import org.springframework.core.io.FileSystemResource;

public class FileSystemResourceWrapper {
	private FileSystemResource fileSystemResource;

	public FileSystemResourceWrapper (String filename){
		fileSystemResource = new FileSystemResource(filename);
	}

	public FileSystemResource getFileSystemResource() {
		return fileSystemResource;
	}

	public void setFileSystemResource(FileSystemResource fileSystemResource) {
		this.fileSystemResource = fileSystemResource;
	}

	public String getName(){
		String filename = fileSystemResource.getFilename();
		String name = filename.substring( 0, filename.lastIndexOf("."));
		return name;
	}
}

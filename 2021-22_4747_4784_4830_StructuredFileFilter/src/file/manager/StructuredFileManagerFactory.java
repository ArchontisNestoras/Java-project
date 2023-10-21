package file.manager;

public class StructuredFileManagerFactory {

	public StructuredFileManagerInterface createStructuredFileManager() {
		StructuredFileManagerInterface manager = new StructuredFileManager();
		return manager;
	}

}

package care.up.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	public void init();

	public String save(MultipartFile file);

	public Resource load(String filename);

	public void deleteAll();

	public boolean deleteFileByName(String name);
}

package care.up.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import care.up.service.FilesStorageService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	private final Path root = Paths.get("/var/www/careup/uploads");
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	@Override
	public void init() {
		try {
			if (!Files.exists(root)) {
				Files.createDirectory(root);
			}

		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}

	}

	@Override
	public String save(MultipartFile file) {
		try {
			this.init();
			String newFileName = file.getOriginalFilename().replace(file.getOriginalFilename(),
					this.randomString(15) + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), this.root.resolve(newFileName));
			return newFileName;
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			
			System.out.println(file);

			if (!(resource.exists() || resource.isReadable())) {
				System.out.println("exist");
				return null;

			} else {
				
				System.out.println("else");
				return resource;
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());

	}

	@Override
	public boolean deleteFileByName(String name) {
		boolean res = false;
		if (name != null) {
			Path file = root.resolve(name);
			try {
				Files.delete(file);
				res = true;
			} catch (IOException e) {
				throw new RuntimeException("Could not delete the files!: " + e.getMessage());
			}
		}

		return res;
	}

}

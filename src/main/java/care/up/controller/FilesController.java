package care.up.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.service.FilesStorageService;

@RestController
@RequestMapping("/files")
@CrossOrigin
public class FilesController {

	@Autowired
	FilesStorageService storageService;

	@GetMapping("/{filename}")
	public ResponseEntity<?> getFile(@PathVariable(name = "filename") String filename) {
		Resource file = storageService.load(filename);
		System.out.println(file);

		if (file != null) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);
		} else {
			return new ResponseEntity<>("Could not read the file!", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/files/delete/{filename}")
	public ResponseEntity<?> deleteFile(@PathVariable(name = "filename") String filename) {
		if (storageService.deleteFileByName(filename)) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
					.body("file deleted successfully");
		} else {
			return new ResponseEntity<>("Could not delete the file!", HttpStatus.NOT_FOUND);
		}

	}
}

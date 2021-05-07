package com.neptunebank.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.neptunebank.app.service.FileUploadService;
import com.neptunebank.app.service.dto.FileUploadDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing MediaFiles.
 */
@RestController
@RequestMapping("/api")
public class FileUploadResource {

	private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

	private static final String ENTITY_NAME = "file upload";

	@Inject
	private FileUploadService fileUploadService;

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	/**
	 * POST /mediaFiles : Download the file and save it in the resources folder.
	 *
	 * @param file
	 * @return
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/mediaFiles",
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
		log.debug("REST request to upload media file");
		fileUploadService.saveMediaFile(file);
		//return ResponseEntity.created(new URI("/mediaFiles/" + file.getOriginalFilename())).build();
		return ResponseEntity.created(new URI("/mediaFiles/" + file.getOriginalFilename()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, file.getOriginalFilename()))
			.body(null);
	}

	/**
	 * GET /mediaFiles/:fileName : Upload the file from the resource folder by file name.
	 *
	 * @param fileName
	 * @param response
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/mediaFiles/{fileName:.+}",
		method = RequestMethod.GET)
	@Timed
	public void getFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		fileName = URLDecoder.decode(fileName, "UTF-8");

		log.debug("REST request to download media file: {}", fileName);

		File file = fileUploadService.getMediaFile(fileName);

		if (file != null) {
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

			OutputStream out = response.getOutputStream();
			try (FileInputStream in = new FileInputStream(file)) {
				byte[] buffer = new byte[4096];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			}
			out.flush();
		} else {
			response.setStatus(404);
		}
	}

	/**
	 * GET /mediaFiles : get the list of all files in the resource folder.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of files in the resource folder
	 */
	@GetMapping("/mediaFiles")
	@Timed
	public ResponseEntity<List<FileUploadDTO>> getAllFiles() {
		log.debug("REST request to get a page of Comments");

		List<FileUploadDTO> list = fileUploadService.getMediaFileList().stream()
			.map(FileUploadDTO::new).collect(Collectors.toList());

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * DELETE /mediaFiles/{fileName:.+} : delete the file from the resources folder.
	 *
	 * @param fileName
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws java.io.UnsupportedEncodingException
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER'})")
	@DeleteMapping("/mediaFiles/{fileName:.+}")
	@Timed
	public ResponseEntity<Void> deleteComment(@PathVariable String fileName) throws UnsupportedEncodingException {
		fileName = URLDecoder.decode(fileName, "UTF-8");

		log.debug("REST request to delete media file: {}", fileName);

		if (fileUploadService.deleteMediaFile(fileName)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}

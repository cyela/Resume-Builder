package com.demo.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Resume;
import com.demo.util.PdfOneGenerator;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path="/api")
public class ResumeController {
	
	@Autowired
	private PdfOneGenerator pdfGen;
	
	@PostMapping(path="/resume")
	public ResponseEntity<String> postResume(@Valid @RequestBody Resume resume) throws IOException {
		 
		System.out.println(resume.getHeader());
		System.out.println(resume.getExperience());
		System.out.println(resume.getEducation());
		System.out.println(resume.getProjects());
		System.out.println(resume.getSkills());
		return new ResponseEntity<String>(pdfGen.createDocument(resume), HttpStatus.OK);
	}
	
	@GetMapping(path="/resumef")
	public ResponseEntity<byte[]> getResume(@RequestParam("filename") String filename) throws IOException {
		
		return new ResponseEntity<byte[]>(pdfGen.getDocument(filename), HttpStatus.OK);
	}
}

/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import com.abs.oec.exception.ResourceNotFoundException;
import com.abs.oec.model.Note;
import com.abs.oec.repository.NoteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	NoteRepository noteRepository;

	//=========================================================================
	
	@GetMapping("/notes")
	public ResponseEntity<List<Note>> getAllNotes() {
		String logTag = "getAllNotes() ";
		LOGGER.info(logTag + "START of the method");
		List<Note> list = noteRepository.findAll();
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<List<Note>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping("/notes")
	public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
		Note n = noteRepository.save(note);
		return new ResponseEntity<Note>(n, new HttpHeaders(), HttpStatus.OK);
	}
	
	//=========================================================================

	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}
	
	//=========================================================================

	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}

	//=========================================================================
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		noteRepository.delete(note);
		return ResponseEntity.ok().build();
	}
	
	//=========================================================================
}

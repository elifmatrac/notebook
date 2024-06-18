package com.dogus.notebook.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogus.notebook.model.Note;
import com.dogus.notebook.service.NoteService;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> create(@RequestBody Note note) {
        Note createdNote = noteService.save(note);
        return new ResponseEntity<>(createdNote, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> get(@PathVariable Long id) {
        Optional<Note> note = noteService.get(id);
        if (note.isPresent()) {
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable("id") Long id, @RequestBody Note note) {
        Note updatedNote = noteService.update(id, note);
        return new ResponseEntity<>(updatedNote, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<Note>> list() {
        List<Note> notes = noteService.list();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            noteService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

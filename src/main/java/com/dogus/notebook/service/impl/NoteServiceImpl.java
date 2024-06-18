package com.dogus.notebook.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogus.notebook.model.Note;
import com.dogus.notebook.model.User;
import com.dogus.notebook.repository.NoteRepository;
import com.dogus.notebook.service.NoteService;
import com.dogus.notebook.service.UserService;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired NoteRepository noteRepository;
    @Autowired UserService userService;

    @Override
    public Note save(Note note) {

        User user = userService.getAuthenticatedUser();
        Note noteToCreate = new Note();
        noteToCreate.setDate(LocalDateTime.now().toLocalDate());
        noteToCreate.setUser(user);
        noteToCreate.setNoteDescription(note.getNoteDescription());

        return noteRepository.save(noteToCreate);
    }

    @Override
    public Optional<Note> get(Long id) {
        return noteRepository.findById(id);

    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> list() {
        User user = userService.getAuthenticatedUser();
        return noteRepository.findAll().stream()
                .filter(note -> note.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public Note update(Long id, Note note) {
        Note existingNote = get(id).orElseThrow(() -> {
            throw new RuntimeException("Note is not found");
        });

        existingNote.setTitle(note.getTitle());
        existingNote.setNoteDescription(note.getNoteDescription());
        return noteRepository.save(existingNote);
    }
}

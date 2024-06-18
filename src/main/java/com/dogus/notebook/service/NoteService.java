package com.dogus.notebook.service;

import java.util.List;
import java.util.Optional;

import com.dogus.notebook.model.Note;

public interface NoteService {

    Note save(Note note);

    Optional<Note> get(Long id);

    void delete(Long id);

    List<Note> list();

    Note update(Long id, Note note);
}

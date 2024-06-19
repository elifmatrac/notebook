package com.dogus.notebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dogus.notebook.repository.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Note findByTitle(String title);

}

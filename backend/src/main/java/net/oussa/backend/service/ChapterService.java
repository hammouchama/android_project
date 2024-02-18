package net.oussa.backend.service;


import net.oussa.backend.model.Chapter;
import net.oussa.backend.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    @Autowired
    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public Chapter addChapter(Chapter chapter) {
        // Add logic for validating and saving the chapter to the database
        return chapterRepository.save(chapter);
    }

    public Chapter getChapter(long id) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);
        return optionalChapter.orElse(null);
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    public Chapter updateChapter(long id, Chapter updatedChapter) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);

        if (optionalChapter.isPresent()) {
            Chapter existingChapter = optionalChapter.get();
            // Update the existing chapter with the values from updatedChapter
            existingChapter.setChapterName(updatedChapter.getChapterName());
            existingChapter.setDescription(updatedChapter.getDescription());

            // Save the updated chapter to the database
            return chapterRepository.save(existingChapter);
        } else {
            // Handle the case where the chapter with the given id is not found
            return null;
        }
    }

    public void deleteChapter(long id) {
        chapterRepository.deleteById(id);
    }
}
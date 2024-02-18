package net.oussa.backend.controller;

import lombok.AllArgsConstructor;
import net.oussa.backend.model.Chapter;
import net.oussa.backend.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapter")
@AllArgsConstructor

public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping("/add")
    public ResponseEntity<?> addChapter(@RequestBody Chapter chapter) {
        try {
            Chapter addedChapter = chapterService.addChapter(chapter);
            return ResponseEntity.ok(addedChapter);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getChapter(@PathVariable long id) {
        try {
            Chapter chapter = chapterService.getChapter(id);
            return ResponseEntity.ok(chapter);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllChapters() {
        try {
            List<Chapter> chapters = chapterService.getAllChapters();
            return ResponseEntity.ok(chapters);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChapter(@PathVariable long id, @RequestPart("chapter") Chapter chapter) {
        try {
            Chapter updatedChapter = chapterService.updateChapter(id, chapter);
            return ResponseEntity.ok(updatedChapter);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable long id) {
        try {
            chapterService.deleteChapter(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
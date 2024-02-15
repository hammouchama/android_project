package net.hammouchama.e_learnebackend.controller;

import net.hammouchama.e_learnebackend.model.PlayList;
import net.hammouchama.e_learnebackend.repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlayListController {

    @Autowired
    private PlayListRepository playListRepository;

    @GetMapping
    public List<PlayList> getAllPlayLists() {
        return playListRepository.findAll();
    }

    @PostMapping
    public PlayList createPlayList(@RequestBody PlayList playList) {
        return playListRepository.save(playList);
    }


}
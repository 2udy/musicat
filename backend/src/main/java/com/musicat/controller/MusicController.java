package com.musicat.controller;

import com.musicat.data.dto.MusicInfoDto;
import com.musicat.data.dto.MusicRequestResultDto;
import com.musicat.data.dto.MusicRequestDto;
import com.musicat.data.dto.SpotifySearchResultDto;
import com.musicat.data.entity.Music;
import com.musicat.service.MusicService;
import com.sun.jdi.request.DuplicateRequestException;
import java.sql.SQLOutput;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
@CrossOrigin
public class MusicController {

  private final MusicService musicService;

  @PostMapping("/request")
  public ResponseEntity<MusicRequestResultDto> insertMusic(
      @RequestBody MusicRequestDto musicRequestDto)
      throws Exception {
    try {
      MusicRequestResultDto musicRequestResultDto = musicService.requestMusic(musicRequestDto);
      return ResponseEntity.ok(musicRequestResultDto);
    } catch (DuplicateRequestException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  @GetMapping("/")
  public ResponseEntity<List<MusicInfoDto>> getRequestMusic() throws Exception {

    List<MusicInfoDto> requestMusic = musicService.getMusicInfoList();

    try {
      return ResponseEntity.ok(requestMusic);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{musicSeq}")
  public ResponseEntity<MusicInfoDto> getMusic(@PathVariable long musicSeq) throws Exception {

    try {
      MusicInfoDto musicInfoDto = musicService.getMusic(musicSeq);
      return ResponseEntity.ok(musicInfoDto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/search")
  public List<SpotifySearchResultDto> searchMusic(@RequestParam String queryString)
      throws Exception {
    return musicService.searchMusic(queryString);
  }

}

package com.musicat.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotifySearchResultDto {

  private String musicTitle;
  private String musicArtist;
  private String musicAlbum;
  private String musicImage;
  private String musicGenre;
  private String musicReleaseDate;
}

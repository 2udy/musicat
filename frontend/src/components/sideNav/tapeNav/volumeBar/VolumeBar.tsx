import { useState } from "react";
import style from "./VolumeBar.module.css";
import ReactPlayer from "react-player";

export const VolumeBar = () => {
  const [volume, setVolume] = useState(0.5);
  // Youtube, Socket 음원 소스 어떤 형식으로 주는지 백에 확인하기
  // 사연, 채팅, 곡 소개는 mp3형식 파일로 ==> Web Audio API 가능 할 듯!
  // 그냥 파일 넘겨주는거 받아서 리스트에 넣어서 사용하면 될듯
  // 유튜브는 embed로 ==> <audio> 소스로 이용 방법 고민해보기

  const videoId = "JEJGD9mIYQQ";

  return (
    <div className={style.volumeBar}>
      <ReactPlayer
        url={`https://www.youtube.com/embed/${videoId}`}
        controls
        playing={true}
        volume={volume}
        width="1px"
        height="1px"
      />
      ;
      <input
        type="range"
        min={0}
        max={1}
        step={0.01}
        value={volume}
        onChange={(e) => setVolume(e.target.valueAsNumber)}
        className={style.rangeInput}
      />
      <img src="img/volumeBar.png" className={style.bgImg} />
    </div>
  );
};

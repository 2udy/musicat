import { useRecoilState, useRecoilValue, useResetRecoilState } from "recoil";
import { GraphicCanvas } from "./graphicCanvas/GraphicCanvas";
import { RadioPlayer } from "./radioPlayer/RadioPlayer";
import { socketConnection } from "@/atoms/socket.atom";
import { nowMainPageState } from "@/atoms/common.atom";
import { useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPlayCircle,
  faStopCircle,
} from "@fortawesome/free-regular-svg-icons";
import { playNowState } from "@/atoms/song.atom";
import style from "./Broadcast.module.css";
import radioBoothIMG from "@/asset/img/radioBooth.png";
import SocketManager from "@/connect/socket/socket";
import { musicState } from "@/atoms/song.atom";

export const Broadcast = () => {
  const nowMainPage = useRecoilValue(nowMainPageState);
  const [playNow, setPlayNow] = useRecoilState(playNowState);
  const socket = socketConnection();
  const stopRadio = useResetRecoilState(musicState);
  const socketManager = SocketManager.getInstance();

  useEffect(() => {
    if (playNow) socket();
  }, [playNow]);

  return (
    <div
      className={
        nowMainPage ? style.broadcast : style.broadcast + " " + style.mypage
      }
    >
      <img
        src={radioBoothIMG}
        alt=""
        className={style.radioBooth}
        style={!nowMainPage ? { display: "none" } : undefined}
      />
      <div
        className={style.broad_back}
        style={nowMainPage ? { display: "none" } : undefined}
      >
        <FontAwesomeIcon
          icon={faPlayCircle}
          className={style.play}
          style={!playNow ? undefined : { display: "none" }}
          onClick={() => {
            setPlayNow(true);
          }}
        />
        <FontAwesomeIcon
          icon={faStopCircle}
          className={style.play}
          style={playNow ? undefined : { display: "none" }}
          onClick={() => {
            setPlayNow(false);
            stopRadio();
            socketManager.disconnect();
          }}
        />
      </div>
      <GraphicCanvas />
      <RadioPlayer />
    </div>
  );
};

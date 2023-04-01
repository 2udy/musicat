import { nowMainPageState } from "@/atoms/common.atom";
import { LoadingSpinner } from "@/pages/common/loadingSpinner/LoadingSpinner";
import { useRecoilValue } from "recoil";
import style from "./Broadcast.module.css";
import { GraphicCanvas } from "./graphicCanvas/GraphicCanvas";
import { YoutubePlay } from "./youtube/YoutubePlay";

export const Broadcast = () => {
  const nowMainPage = useRecoilValue(nowMainPageState);

  return (
    <div
      className={
        nowMainPage ? style.broadcast : style.broadcast + " " + style.mypage
      }
    >
      <YoutubePlay />
      <GraphicCanvas />
      {/* <div className={nowMainPage ? style.none : style.mybackground} /> */}
      {/* <LoadingSpinner /> */}
    </div>
  );
};

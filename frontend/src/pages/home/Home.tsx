import { nowMainPageState } from "@/atoms/common.atom";
import { TapeNav } from "@/components/sideNav/tapeNav/TapeNav";
import { useEffect } from "react";
import { Outlet } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import style from "./Home.module.css";

export const Home = () => {
  const setNowMainPage = useSetRecoilState(nowMainPageState);

  useEffect(() => {
    setNowMainPage(true);
  }, []);

  return (
    <div
      className={style.home}
      style={{
        backgroundImage: "url(../../../public/img/pagebackground/theme1.png)",
      }}
    >
      <div className={style.leftTab}>
        <TapeNav />
      </div>
      <div className={style.rightTab}>
        <div className={style.content}>
          <Outlet />
        </div>
      </div>
    </div>
  );
};

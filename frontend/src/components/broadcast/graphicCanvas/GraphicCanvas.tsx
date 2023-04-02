/* eslint-disable */
import { Canvas, useFrame } from "@react-three/fiber";
// import { Whirligig } from "./Whirligig";
import { Cat } from "./Cat";
import { Background } from "./background/Background";
import { OrbitControls } from "@react-three/drei";
import * as THREE from "three";

export const GraphicCanvas = () => {

  return (
    <Canvas camera={{ fov: 50, near: 0.1, far: 500, position: [-1.34, 0.95, 1.70], scale:1 }}> 
      <Cat scale={0.5}  position={new THREE.Vector3(0, 0.1, -0.7)}/>

      <Background />
      <pointLight intensity={0.3} position={[0,1,0]}/>
      <ambientLight intensity={0.13} />
      {/* <Whirligig position={[0,-30,-100]} scale={1}/> */}

      {/* <OrthographicCamera />*/}
      {/* <PerspectiveCamera position={[-1.991, 1.340,1.789]} rotation={[-36.05, -42.34, -26.11]} fov={50} far={1000} near={0.01}/> */}
      {/* <OrbitControls /> */}
    </Canvas>
  );
};

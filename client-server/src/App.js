import logo from './logo.svg';
import './App.css';
import { Route, Routes } from "react-router-dom";
import Maps from "./component/page/Maps";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Maps />} />
    </Routes>
  );
}

export default App;

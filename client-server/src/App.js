import logo from "./logo.svg";
import "./App.css";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Maps from "./component/page/Maps";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/map" element={<Maps />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

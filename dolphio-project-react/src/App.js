import './App.css';
import { BrowserRouter as Router, Route } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";
import Game from "./game";

function App() {

  
  return (
    <Provider store={store}>
    <Router>
      <div>
       <Game/>
      </div>
      </Router>
    </Provider>
   
  );
}

export default App;
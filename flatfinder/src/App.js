import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import "./App.css";
import Navbar from "./components/layout/Navbar";
import Home from "./components/pages/Home";
import UserOffers from "./components/pages/UserOffers";
import LoginBox from "./components/login/Login";
import RegisterBox from "./components/register/RegisterBox";

function App() {
  return (
    <Router>
      <Navbar />
      <Switch>
        <Route exact path="/login" component={LoginBox} />
        <Route exact path="/register" component={RegisterBox} />
        <Route exact path="/" component={Home} />
        <Route exact path="/my_offers" component={UserOffers} />
      </Switch>
    </Router>
  );
}

export default App;

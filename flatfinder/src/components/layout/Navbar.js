import React, { useContext, useEffect, useState } from "react";
import "./Navbar.css";
import logo from "../../images/logo.png";
import heart from "../../images/heart.png";
import logout from "../../images/logout.png";
// import { logOut } from "../actions/auth";
// import { Redirect, Link } from "react-router-dom";
// import { AuthContext } from "../contexts/AuthContext";

import { Link, NavLink, Redirect } from "react-router-dom";
export const Navbar = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(true);
  const logOut = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("access");
  };
  return (
    <div className="navbar">
      <div className="logo-container">
        {/* <img src={logo} alt="Logo" className="logo" /> */}

        <h1>
          {" "}
          <b>Flat Finder</b>
        </h1>
      </div>
      <ul>
        <Link to="/">
          <li className="">Home</li>
        </Link>
      </ul>

      <div className="icons-container">
        {isAuthenticated && (
          <Link to="/login">
            <img src={logout} alt="Logo" className="icon" onClick={logOut} />
          </Link>
        )}
      </div>
    </div>
  );
};

export default Navbar;

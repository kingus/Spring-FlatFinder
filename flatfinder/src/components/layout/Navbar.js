import React, { useContext, useEffect, useState } from "react";
import "./Navbar.css";
import logo from "../../images/logo.png";
import heart from "../../images/heart.png";
import logout_icon from "../../images/logout.png";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";

import * as authActions from "../../store/actions/auth";
// import { logOut } from "../actions/auth";
// import { Redirect, Link } from "react-router-dom";
// import { AuthContext } from "../contexts/AuthContext";

import { Link, NavLink, Redirect } from "react-router-dom";
export const Navbar = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const logOut = () => {
    console.log("usuwam token");
    localStorage.removeItem("token");
    history.push("/login");
    console.log("usunalem token");
  };
  useEffect(() => {
    dispatch(authActions.checkIfAuthenticated());
    if (isAuthenticated) {
      history.push("/");
    } else {
      history.push("/login");
    }
  }, [isAuthenticated]);
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
        {isAuthenticated && (
          <Link to="/">
            <li className="">Home</li>
          </Link>
        )}
      </ul>
      <ul>
        {isAuthenticated && (
          <Link to="/my_offers">
            <li className="">My offers</li>
          </Link>
        )}
      </ul>

      <div
        className="icons-container"
        onClick={() => {
          console.log("wylogowywanie");
          logOut();
        }}
      >
        {isAuthenticated && (
          <img src={logout_icon} alt="Logo" className="icon" />
        )}
      </div>
    </div>
  );
};

export default Navbar;

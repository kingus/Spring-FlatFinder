import React, { useEffect } from "react";
import "./Navbar.css";
import logout_icon from "../../images/logout.png";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import * as authActions from "../../store/actions/auth";
import { Link } from "react-router-dom";


// navbar is styling from external css file because easiest way to styling list elements
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

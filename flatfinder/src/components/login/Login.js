import React, { useState, useEffect } from "react";
import { Link, Redirect } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import * as authActions from "../../store/actions/auth";

import "./LoginBox.css";
// import Navbar from "./Navbar";
import flat from "../../images/flat.jpg";
import { useHistory } from "react-router-dom";
// import { AuthContext } from "../contexts/AuthContext";
// import Footer from "./Footer";

const LoginBox = () => {
  const history = useHistory();
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });
  const { username, password } = formData;
  // const context = useContext(AuthContext);

  const onChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const onSubmit = (e) => {
    e.preventDefault();
    login(username, password);
  };

  const login = (username, password) => {
    dispatch(authActions.login(username, password));
    //load_user();
    // console.log(context.isAuthenticated);
  };
  useEffect(() => {
    dispatch(authActions.checkIfAuthenticated());
    if (isAuthenticated) {
      history.push("/");
    }
  }, [isAuthenticated]);

  return (
    <div className="container">
      {/* <Navbar></Navbar> */}
      <div className="page-container">
        <div className="loginbox">
          <div className="photo-div">
            <img src={flat} alt="Logo" className="photo" />
          </div>
          <div className="form_container">
            <h1>Sign in</h1>
            <form onSubmit={(e) => onSubmit(e)} className="form-style">
              <div className="input-group">
                <input
                  type="username"
                  name="username"
                  className="login_input"
                  placeholder="Username"
                  value={username}
                  onChange={(e) => onChange(e)}
                ></input>
              </div>
              <div className="input-group">
                <input
                  type="password"
                  name="password"
                  value={password}
                  className="login_input"
                  placeholder="Password"
                  onChange={(e) => onChange(e)}
                ></input>
              </div>
              <div className="btn-div">
                <button className="btn-login" type="submit">
                  Login
                </button>
                {/* </Link> */}
              </div>
            </form>

            <div className="bottom-btns">
              <Link to="/register">
                <h5>Don't have an account?</h5>
                <h6>Register</h6>
              </Link>
            </div>
          </div>
        </div>
      </div>
      {/* <Footer></Footer> */}
    </div>
  );
};

export default LoginBox;

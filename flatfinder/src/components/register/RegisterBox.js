import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import "../login/LoginBox.css";
import flat from "../../images/flat.jpg";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import * as authActions from "../../store/actions/auth";
import { useHistory } from "react-router-dom";

const RegisterBox = () => {
  const history = useHistory();
  const dispatch = useDispatch();
  const [formData, setFormData] = useState({
    username: "szymon.gruszczynski@student.wat.edu.pl",
    email: "",
    password: "",
    re_password: "",
  });

  const { username, email, password, re_password } = formData;

  const onChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const onSubmit = (e) => {
    e.preventDefault();
    console.log("SUBMIT");
    if (password === re_password) {
      console.log("Hasla sie zgadzaja");
      dispatch(authActions.register(username, email, password, "wesola"));
      // signup(formData.username, formData.email, formData.password, "wesola");
    } else {
      console.log("Password aren't the same");
    }
  };

  // return <Redirect to="/login" />;

  const signup = (username, email, password, preffered_district) => {
    console.log("Zostanie odpalona akcja");

    dispatch(
      authActions.register((username, email, password, preffered_district))
    );
  };

  return (
    <div className="container">
      <div className="page-container">
        <div className="registerbox">
          <div className="photo-div-register">
            <img src={flat} alt="Logo" className="photo-register" />
          </div>
          <div className="form_container">
            <h1>Sign up</h1>
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
                  type="email"
                  name="email"
                  value={email}
                  className="login_input"
                  placeholder="Email"
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
              <div className="input-group">
                <input
                  type="password"
                  name="re_password"
                  className="login_input"
                  placeholder="Repeat password"
                  value={re_password}
                  onChange={(e) => onChange(e)}
                ></input>
              </div>
              <div className="btn-div">
                <button className="btn-login" type="submit">
                  Register
                </button>
              </div>
            </form>

            <div className="bottom-btns">
              <Link to="/login">
                <h5>Already have an account? </h5>
                <h6> Login</h6>
              </Link>
            </div>
          </div>
        </div>
      </div>
      {/* <Footer></Footer> */}
    </div>
  );
};

export default RegisterBox;

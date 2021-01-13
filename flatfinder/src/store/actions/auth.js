import axios from "axios";
export const LOGIN = "LOGIN";
export const LOGOUT = "LOGOUT";
export const REGISTER = "REGISTER";

export const login = (username, password) => {
  return async (dispatch, getState) => {
    const body = JSON.stringify({ username, password });
    axios
      .post("http://localhost:8080/login", body)
      .then(function (response) {
        localStorage.setItem("token", response.headers.authorization);
        dispatch({
          type: LOGIN,
        });
      })
      .catch(function (error) {})
      .then(function () {});
  };
};

export const register = (email, username, password, preffered_district) => {
  return async (dispatch, getState) => {
    const body = {
      email: email,
      username: username,
      password: password,
      preffered_district: preffered_district,
    };
    axios
      .post("http://localhost:8080/register", body)
      .then(function (response) {
        dispatch({
          type: REGISTER,
        });
      })
      .catch(function (error) {})
      .then(function () {});
  };
};

export const checkIfAuthenticated = () => {
  return async (dispatch, getState) => {
    if (localStorage.hasOwnProperty("token")) {
      const config = {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      };
      axios
        .get("http://localhost:8080/api/user-offers", config)
        .then(function (response) {
          dispatch({
            type: LOGIN,
          });
        })
        .catch(function (error) {
          localStorage.removeItem("token");
          dispatch({
            type: LOGOUT,
          });
        })
        .then(function () {});
    } else {
      dispatch({
        type: LOGOUT,
      });
    }
  };
};

export const logout = () => {
  return (dispatch, getState) => {
    localStorage.removeItem("token");
    checkIfAuthenticated();
  };
};

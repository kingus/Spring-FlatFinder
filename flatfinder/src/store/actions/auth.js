import axios from "axios";
export const LOGIN = "LOGIN";
export const LOGOUT = "LOGOUT";
export const REGISTER = "REGISTER";

export const login = (username, password) => {
  return async (dispatch, getState) => {
    console.log("Auth");
    const body = JSON.stringify({ username, password });
    axios
      .post("http://localhost:8080/login", body)
      .then(function (response) {
        console.log(response.headers.authorization);
        localStorage.setItem("token", response.headers.authorization);
        dispatch({
          type: LOGIN,
        });
      })
      .catch(function (error) {
        // handle error
        console.log("Wrong cred");
        console.log(error);
      })
      .then(function () {});
  };
};

export const register = (email, username, password, preffered_district) => {
  return async (dispatch, getState) => {
    console.log("Register Action");
    console.log(email);
    console.log(username);
    console.log(password);
    console.log(preffered_district);
    console.log("Register Action1");

    const body = {
      email: email,
      username: username,
      password: password,
      preffered_district: preffered_district,
    };
    console.log(body);
    axios
      .post("http://localhost:8080/register", body)
      .then(function (response) {
        console.log(response.status);
        console.log("register success");
        dispatch({
          type: REGISTER,
        });
      })
      .catch(function (error) {
        // handle error
        console.log("Wrong REGISTER");
        console.log(error);
      })
      .then(function () {});
  };
};

export const checkIfAuthenticated = () => {
  console.log("sprawdzam stan zalogowania");
  return async (dispatch, getState) => {
    if (localStorage.hasOwnProperty("token")) {
      console.log("Token jest w localstorage");

      const config = {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      };
      axios
        .get("http://localhost:8080/api/user-offers", config)
        .then(function (response) {
          console.log("Token jest poprawny");

          dispatch({
            type: LOGIN,
          });
        })
        .catch(function (error) {
          console.log("Token jest błedny");

          localStorage.removeItem("token");
          dispatch({
            type: LOGOUT,
          });
        })
        .then(function () {});
    } else {
      console.log("Nie ma tokena");

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

// export const removeMarkers = () => {
//   return async (dispatch, getState) => {
//     dispatch({
//       type: REMOVE_MARKERS,
//     });
//   };
// };
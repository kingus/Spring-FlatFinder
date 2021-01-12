import axios from "axios";
export const GET_APARTMENTS = "GET_APARTMENTS";

export const getApartments = () => {
  return async (dispatch, getState) => {
    let apartments = [];
    const config = {
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };

    await fetch("http://localhost:8080/api/offers", config)
      .then((response) => response.json())
      .then((json) => {
        console.log(json);
        apartments = json;
      })
      .catch((error) => {
        console.error(error);
      });
    dispatch({
      type: GET_APARTMENTS,
      apartments: apartments,
    });
  };
};

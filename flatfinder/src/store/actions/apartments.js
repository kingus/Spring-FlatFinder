import axios from "axios";
export const GET_APARTMENTS = "GET_APARTMENTS";

export const getApartments = () => {
  return async (dispatch, getState) => {
    console.log("Action here!");
    let apartments = [];
    const config = {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "*",
      },
    };

    await fetch("http://localhost:8080/api/offers")
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

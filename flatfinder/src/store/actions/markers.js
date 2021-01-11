export const ADD_MARKER = "ADD_MARKER";
export const REMOVE_MARKERS = "REMOVE_MARKERS";

export const addMarker = (marker) => {
  return async (dispatch, getState) => {
    dispatch({
      type: ADD_MARKER,
      marker: marker,
    });
  };
};

export const removeMarkers = () => {
  return async (dispatch, getState) => {
    dispatch({
      type: REMOVE_MARKERS,
    });
  };
};

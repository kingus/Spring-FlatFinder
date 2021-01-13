import { ADD_MARKER, REMOVE_MARKERS } from "../actions/markers";

const initialState = {
  markers: [],
};
export default (state = initialState, action) => {
  switch (action.type) {
    case ADD_MARKER:
      return {
        markers: state.markers.concat(action.marker),
      };
    case REMOVE_MARKERS:
      for (let i = 0; i < state.markers.length; i++) {
        state.markers[i].setMap(null);
      }
      return {
        markers: [],
      };
    default:
      return state;
  }
};

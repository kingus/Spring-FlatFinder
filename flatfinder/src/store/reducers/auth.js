import { LOGIN, LOGOUT, REGISTER } from "../actions/auth";

const initialState = {
  isAuthenticated: false,
  userData: [],
};
export default (state = initialState, action) => {
  switch (action.type) {
    case LOGIN:
      return {
        isAuthenticated: true,
      };
    case LOGOUT:
      return {
        isAuthenticated: false,
      };

    // case GET_APARTMENTS:
    //   console.log("Reducer here!");
    //   return {
    //     apartments: action.apartments,
    //   };
    // case ADD_MARKER:
    //   return {
    //     markers: state.markers.concat(action.marker),
    //   };
    // case REMOVE_MARKERS:
    //   console.log("Było markerow: " + state.markers.length);
    //   for (let i = 0; i < state.markers.length; i++) {
    //     state.markers[i].setMap(null);
    //   }
    //   return {
    //     markers: [],
    //   };
    // case EDIT_EVENT:
    //   let tmpTripEvents = state.tripEvents;
    //   tmpTripEvents.forEach(function (event, index, array) {
    //     if (event.id == action.eventData.id) {
    //       array[index] = {
    //         ...event,
    //         title: action.eventData.eventName,
    //         description: action.eventData.eventDesc,
    //         date: action.eventData.eventDate,
    //         time: action.eventData.time,
    //       };
    //     }
    //   });
    //   return {
    //     ...state,
    //     tripEvents: tmpTripEvents,
    //   };

    // case DELETE_EVENT:
    //   return {
    //     //...state,
    //     tripEvents: state.tripEvents.filter(
    //       (event) => event.id !== action.eventId
    //     ),
    //   };
    default:
      return state;
  }
};

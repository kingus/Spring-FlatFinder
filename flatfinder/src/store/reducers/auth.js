import { LOGIN, LOGOUT } from "../actions/auth";

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
    default:
      return state;
  }
};

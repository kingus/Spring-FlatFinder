import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { createStore, combineReducers, applyMiddleware } from "redux";

import { Provider } from "react-redux";
import ReduxThunk from "redux-thunk";
import thunk from "redux-thunk";
import apartmentsReducer from "./store/reducers/apartments";

const rootReducer = combineReducers({
  apartments: apartmentsReducer,
});

const store = createStore(
  rootReducer,
  applyMiddleware(ReduxThunk)
  // jakies firebasowe rzeczy w compose
);

ReactDOM.render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

import React from "react";
import { BrowserRouter as Router, Route, Redirect } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import SignIn from "./pages/SignIn";

export default function App() {
  return (
    <div className="App">
      <Router>
        <div>
          <Route exact path="/" component={SignIn} />
          <Route exact path="/dashboard" component={Dashboard} />
          <Redirect to="/" />
        </div>
      </Router>
    </div>
  );
}

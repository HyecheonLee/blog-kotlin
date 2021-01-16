import React, { useEffect } from 'react';
import { isLogged } from "../../actions/auth";
import Router from "next/router";

const Private = ({children}) => {
  useEffect(() => {
    if (!isLogged()) {
      Router.push('/user/signIn')
    }
  }, [])
  return (<>{children}</>);
};

export default Private;
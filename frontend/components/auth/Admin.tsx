import React, { useEffect } from 'react';
import { isAdmin } from "../../actions/auth";
import Router from "next/router";

const Admin = ({children}) => {
  useEffect(() => {
    if (!isAdmin()) {
      Router.push('/user/signIn')
    }
  }, [])
  return (<>{children}</>);
};

export default Admin;
import React, { ReactNode } from 'react';
import Header from "./Header";
import { fetcher, isValidToken } from "../actions/auth";
import { API } from "../config";
import { useRecoilState } from "recoil";
import { userState } from "../states/UserState";

type LayoutProps = {
  children: ReactNode
}

const Layout = ({children}) => {
  if (isValidToken()) {
    const [user, setUser] = useRecoilState(userState);
    if (user.id == -1) {
      fetcher(`${API}/api/user`).then(data => {
        setUser(data);
      });
    }
  }
  return (
    <>
      <Header/>
      {children}
      <p>Footer</p>
    </>
  );
};

export default Layout;
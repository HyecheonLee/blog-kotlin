import React, { ReactNode } from 'react';
import Header from "./Header";

type LayoutProps = {
  children: ReactNode
}

const Layout = ({children}) => {
  return (
    <>
      <Header/>
      {children}
      <p>Footer</p>
    </>
  );
};

export default Layout;
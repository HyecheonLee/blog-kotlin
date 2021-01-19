import React from 'react';
import { APP_NAME } from '../config';
import Link from 'next/link'
import { useRouter } from 'next/router'

import { isAdmin, isLogged, loggedEmail } from "../actions/auth";


const Header = (props) => {
  const router = useRouter();
  const logout = async (e) => {
    localStorage.removeItem("authToken");
    await router.push("/user/signIn")
  }
  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <Link href="/">
          <a className="navbar-brand" href="#">{APP_NAME}</a>
        </Link>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"/>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav ml-auto">
            {isLogged() ?
              <>
                <li className="nav-item">
                  <a className="nav-link" href="" onClick={logout}>로그아웃</a>
                </li>
                {isAdmin() ?
                  (<li className="nav-item">
                    <Link href="/admin">
                      <a className={`nav-link ${router.pathname === '/admin' && 'active'}`} href="">{loggedEmail()}</a>
                    </Link>
                  </li>)
                  : (<li className="nav-item">
                    <Link href="/user">
                      <a className={`nav-link ${router.pathname === '/user' && 'active'}`} href="">{loggedEmail()}</a>
                    </Link>
                  </li>)}
              </>
              : <>
                <li className="nav-item">
                  <Link href="/user/signIn">
                    <a className={`nav-link ${router.pathname === '/user/signIn' && 'active'}`} href="">로그인</a>
                  </Link>
                </li>
                <li className="nav-item">
                  <Link href="/user/signUp">
                    <a className={`nav-link ${router.pathname === '/user/signUp' && 'active'}`} href="">회원가입</a>
                  </Link>
                </li>
              </>
            }
          </ul>
        </div>
      </nav>
    </>
  )
}

export default Header;

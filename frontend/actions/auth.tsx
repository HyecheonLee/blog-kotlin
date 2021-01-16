import axios from "axios";
import { API } from "../config";
import cookie from 'js-cookie'
import jwtDecode from "jwt-decode";
import useSWR from "swr";

interface TokenInfo {
  userId: number;
  email: string | null;
  exp: number;
  roles: string[] | null;
}

export const fetcher = async (url) => {
  let config = null
  if (isValidToken()) {
    const token = localStorage.getItem("authToken");
    config = {
      headers: {"Authorization": `Bearer ${token}`}
    }
  }
  const response = await axios.get(url, config);
  return response.data;
}
export const signUp = async (singUpRequest: { username: string, email: string, password: string }) => {
  try {
    const response = await axios.post(`${API}/api/user/signUp`,
      JSON.stringify(singUpRequest),
      {withCredentials: true})
      .then(response => response.data);
    localStorage.setItem("user", JSON.stringify(response));
    return {
      user: response,
      message: "가입에 성공했습니다.",
      error: null,
    }
  } catch (e) {
    const {data} = e.response;
    return {
      user: null,
      message: null,
      error: data.message || "",
    }
  }
}
export const signIn = async (signUpRequest: { email: string, password: string }) => {
  try {
    const response = await axios.post(`${API}/api/user/signIn`,
      JSON.stringify(signUpRequest), {withCredentials: true})
      .then(response => {
        if (response.headers["authorization"]) {
          localStorage.setItem("authToken", response.headers["authorization"].replace("Bearer", "").trim());
        }
        return response.data;
      });
    return {
      user: response,
      error: null,
    }
  } catch (e) {
    console.log(e)
    const {data} = e.response;
    return {
      user: null,
      error: data.message || "",
    }
  }
}
export const getLoggedUserInfo = async () => {
  const {data, error} = isValidToken() && useSWR(`${API}/api/user`, () => {
    const token = localStorage.getItem("authToken");
    axios.get(`${API}/api/user`, {
      headers: {
        "Authorization": `Bearer ${token}`
      }
    })
  })
  console.log(data, error);
  return {data, error};
}
export const getUserInfo = async (userId: string = null, email: string = null) => {
  if (userId) {
  
  }
}

export const setCookie = (key, value) => {
  if (process.browser) {
    cookie.set(key, value, {
      expires: 1
    })
  }
}
export const removeCookie = (key, value) => {
  if (process.browser) {
    cookie.remove(key, {
      expires: 1
    })
  }
}
export const getCookie = (key) => {
  if (process.browser) {
    cookie.get(key);
  }
}
export const setLocalStorage = (key, value) => {
  if (process.browser) {
    localStorage.setItem(key, JSON.stringify(value));
  }
}
export const removeLocalStorage = (key) => {
  if (process.browser) {
    localStorage.removeItem(key);
  }
}
const getTokenInfo = () => {
  if (typeof window !== 'undefined') {
    const token = localStorage.getItem("authToken");
    if (token) {
      let decodedToken = jwtDecode<TokenInfo>(token);
      if (decodedToken.exp > (Date.now() / 1000)) {
        return decodedToken;
      }
      localStorage.removeItem("authToken");
    }
  }
  return null;
}
export const isValidToken = () => {
  
  return getTokenInfo() !== null
}
export const isLogged = () => {
  let tokenInfo = getTokenInfo();
  return tokenInfo && tokenInfo.roles.includes("USER")
}
export const isAdmin = () => {
  let tokenInfo = getTokenInfo();
  return tokenInfo && tokenInfo.roles.includes("ADMIN")
}
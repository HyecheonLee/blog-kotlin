import axios from "axios";
import { API } from "../config";
import jwtDecode from "jwt-decode";
import useSWR from "swr";
import exp from "constants";

interface TokenInfo {
  userId: number;
  email: string | null;
  exp: number;
  roles: string[] | null;
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
    axios.get(`${API}/api/user`, getAuthConfig())
  })
  return {data, error};
}
export const getUserInfo = async (userId: string = null, email: string = null) => {
  if (userId) {
  
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
export const loggedEmail = () => {
  let tokenInfo = getTokenInfo();
  return tokenInfo ? tokenInfo.email : "";
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

export function getAuthConfig() {
  const token = localStorage.getItem("authToken");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
      "Content-Type": "application/json"
    }
  };
}

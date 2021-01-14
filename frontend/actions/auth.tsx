import axios from "axios";
import { API } from "../config";
import cookie from 'js-cookie'

export const signUp = async (singUpRequest: { username: string, email: string, password: string }) => {
  try {
    const response = await axios({
        method: 'post',
        url: `${API}/api/user/signUp`,
        headers: {
          'Content-Type': 'application/json'
        },
        data: JSON.stringify(singUpRequest)
      }
    )
      .then(response => response.data);
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
export const signIn = async (singUpRequest: { email: string, password: string }) => {
  try {
    const response = await axios.post(`${API}/api/user/signIn`, JSON.stringify(singUpRequest), {
        headers: {
          'Content-Type': 'application/json'
        },
        withCredentials: true
      }
    )
      .then(response => {
        console.log(response.headers);
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

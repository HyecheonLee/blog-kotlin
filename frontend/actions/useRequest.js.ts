import useSWR from "swr"
import { API } from "../config";
import axios from "axios";
import { getAuthConfig, isValidToken } from "./auth";
import { errorAuth } from "../states/ErrorState";

export const postRequest = (uri, requestBody) => {
  return validRequest(() => {
    const {data, error, mutate} = useSWR(`${API}${uri}`, url => axios.post(url, requestBody, getAuthConfig()).then(res => res.data))
    return {
      data, mutate, isLoading: !error && !data, error
    }
  })
}

export const getRequest = uri => {
  return validRequest(() => {
    const {data, error, mutate} = useSWR(`${API}${uri}`, url => axios.get(url, getAuthConfig()).then(res => res.data))
    return {data, mutate, isLoading: !error && !data, error}
  })
}

export const deleteRequest = uri => {
  return validRequest(() => {
    const token = localStorage.getItem("authToken");
    const {data, mutate, error} = useSWR(`${API}${uri}`, url => axios.delete(url, getAuthConfig()).then(res => res.data))
    return {data, mutate, isLoading: !error && !data, error}
  })
}

const validRequest = (func) => {
  if (isValidToken()) {
    return func()
  } else {
    return {
      data: null,
      mutate: null,
      isLoading: false,
      error: errorAuth
    };
  }
}
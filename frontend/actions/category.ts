import axios from "axios";
import { API } from "../config";
import { isValidToken } from "./auth";
import { errorAuth, ErrorType } from "../states/ErrorState";

export type CategoryType = {
  id: number | null,
  name: string,
  slug: string | null
}
export const create = async (name: string) => {
  const token = localStorage.getItem("authToken");
  if (isValidToken()) {
    try {
      const data = await axios.post(`${API}/api/v1/category`, {name}, {
        headers: {
          Authorization: `Bearer ${token}`,
          Accept: 'application/json',
          "Content-Type": "application/json"
        }
      }).then(response => {
        return response.data
      });
      const result: { category: CategoryType, error: ErrorType } = {
        category: data,
        error: null
      }
      return result
    } catch (e) {
      const result: { category: CategoryType, error: ErrorType } = {
        category: null,
        error: e.response.data
      }
      return result
    }
  } else {
    const result: { category: CategoryType, error: ErrorType } = {
      category: null,
      error: errorAuth
    }
    return result
  }
}

export const getCategories = (url: string) => {
  const token = localStorage.getItem("authToken");
  return axios.get(url, {
    headers: {
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
      "Content-Type": "application/json"
    }
  }).then(response => {
    const data = response.data;
    return data["content"]
  })
}

export const getCategoryBySlug = (url) => {
  const token = localStorage.getItem("authToken");
  return axios.get(`${url}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
      "Content-Type": "application/json"
    }
  }).then(response => {
    const data = response.data;
    return data["content"]
  })
}
export const deleteCategoryBySlug = (url) => {
  const token = localStorage.getItem("authToken");
  return axios.delete(`${url}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      Accept: 'application/json',
      "Content-Type": "application/json"
    }
  }).then(response => {
    const data = response.data;
    return data["content"]
  })
}
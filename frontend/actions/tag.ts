import axios from "axios";
import { API } from "../config";
import { getAuthConfig, isValidToken } from "./auth";
import { errorAuth, ErrorType } from "../states/ErrorState";

export type TagType = {
  id: number | null,
  name: string,
  slug: string | null
}
export const createTag = async (name: string) => {
  if (isValidToken()) {
    try {
      const data = await axios.post(`${API}/api/v1/tag`, {name}, getAuthConfig()).then(response => {
        return response.data
      });
      const result: { tag: TagType, error: ErrorType } = {
        tag: data,
        error: null
      }
      return result
    } catch (e) {
      const result: { tag: TagType, error: ErrorType } = {
        tag: null,
        error: e.response.data
      }
      return result
    }
  } else {
    const result: { tag: TagType, error: ErrorType } = {
      tag: null,
      error: errorAuth
    }
    return result
  }
}

export const getTag = (url: string) => {
  return axios.get(url, getAuthConfig()).then(response => {
    return response.data;
  })
}

export const getTagBySlug = (url) => {
  return axios.get(`${url}`, getAuthConfig()).then(response => {
    return response.data;
  })
}
export const deleteTagBySlug = (url) => {
  return axios.delete(`${url}`, getAuthConfig()).then(response => {
    return response.data;
  })
}
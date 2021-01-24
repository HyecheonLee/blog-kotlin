import axios from "axios";
import {API} from "../config";
import {getAuthConfig} from "./auth";
import {ErrorType} from "../states/ErrorState";
import {CategoryType} from "./category";
import {TagType} from "./tag";

export type BlogType = {
	"id": number | null,
	"title": string,
	"slug": string,
	"body": string,
	"excerpt": string | null,
	"categories": CategoryType[],
	"tags": TagType[],
	"createdDate": string,
	"lastModifiedDate": string,
	"createdBy": string,
	"lastModifiedBy": string,
	"mtitle": string,
	"mdesc": string
}
export const createBlog = async (blog) => {
	try {
		const data = await axios.post(`${API}/blog`, blog, getAuthConfig())
		.then(response => response.data);
		
		const result: { blog: BlogType, error: ErrorType } = {
			blog: null,
			error: null
		}
		return result
	} catch (e) {
		const result: { blog: BlogType, error: ErrorType } = {
			blog: null,
			error: e.response.data
		}
		return result
	}
}
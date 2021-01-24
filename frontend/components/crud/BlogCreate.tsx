import React, {useEffect, useState} from 'react';
import {withRouter} from "next/router";
import {getRequest} from "../../actions/useRequest.js";
import dynamic from "next/dynamic";
import 'react-quill/dist/quill.snow.css'
import axios from "axios";
import {API} from "../../config";
import {getAuthConfig} from "../../actions/auth";

const ReactQuill = dynamic(() => import("react-quill"), {ssr: false})
const blogFromLS = () => {
	if (localStorage.getItem('blog')) {
		return JSON.parse(localStorage.getItem('blog'));
	} else {
		return "";
	}
}
const BlogCreate = ({router}) => {
	const [title, setTitle] = useState("")
	const [body, setBody] = useState("")
	const [file, setFile] = useState(null)
	const [checkedCategories, setCheckedCategory] = useState([])
	const [checkedTags, setCheckedTag] = useState([])
	const [error, setError] = useState("")
	const [success, setSuccess] = useState("")
	useEffect(() => {
		setBody(blogFromLS())
	}, [router])
	let {data: categories, isLoading: isCategoryLoading, error: categoryError} = getRequest("/api/v1/category");
	let {data: tags, isLoading: isTagLoading, error: tagError} = getRequest("/api/v1/tag");
	
	const publishBlog = async (e) => {
		e.preventDefault();
		let formData = new FormData();
		formData.set("title", title);
		formData.set('body', body)
		formData.set('file', file)
		for (const checkedCategoryKey in checkedCategories) {
			formData.set(`categories[${checkedCategoryKey}].id`, checkedCategories[checkedCategoryKey])
		}
		for (const checkedTagKey in checkedTags) {
			formData.set(`tags[${checkedTagKey}].id`, checkedTags[checkedTagKey])
		}
		const config = getAuthConfig();
		config["content-type"] = "form-data";
		await axios.post(`${API}/api/v1/blog`, formData, config)
		.then((response) => response.data)
		.then(data => {
			setBody("")
			setTitle("")
			file(null)
			setCheckedCategory([])
			setCheckedTag([])
			setSuccess("저장을 완료했습니다.")
			return data
		})
		.catch((error) => {
			error.response && setError(JSON.stringify(error.response.data.message))
		})
	}
	
	function handleChangeFile(e: React.ChangeEvent<HTMLInputElement>) {
		setFile(e.target.files[0])
	}
	
	function handleChangeBody(value) {
		setBody(value)
		if (typeof window !== 'undefined') {
			localStorage.setItem('blog', JSON.stringify(value))
		}
	}
	
	function handleChangeTitle(e: React.ChangeEvent<HTMLInputElement>) {
		setTitle(e.target.value)
	}
	
	const handleCategoryToggle = (id) => (e) => {
		if (e.target.checked) {
			if (checkedCategories.indexOf(id) === -1)
				setCheckedCategory([...checkedCategories, id])
		} else {
			setCheckedCategory(checkedCategories.filter(value => value !== id))
		}
	}
	const handleTagToggle = (id) => (e) => {
		if (e.target.checked) {
			if (checkedTags.indexOf(id) === -1)
				setCheckedTag([...checkedTags, id])
		} else {
			setCheckedTag(checkedTags.filter(value => value !== id))
		}
	}
	
	const showCategories = () => {
		return (
				categories && categories.map(c => (
						<li key={`category-${c.id}`} className="list-unstyled">
							<label className="form-check-label">
								<input onChange={handleCategoryToggle(c.id)} type="checkbox" className="mr-2"/>
								{c.name}
							</label>
						</li>
				))
		)
	}
	const showTags = () => {
		return (
				tags && tags.map(t => (
						<li key={`tag-${t.id}`} className="list-unstyled">
							<label className="form-check-label">
								<input onChange={handleTagToggle(t.id)} type="checkbox" className="mr-2"/>
								{t.name}
							</label>
						</li>
				))
		)
	}
	
	const showError = () => {
		return (<div className="alert alert-danger" style={{display: error ? '' : "none"}}>{error}</div>)
	}
	
	const showSuccess = () => {
		return (<div className="alert alert-success" style={{display: success ? '' : "none"}}>{success}</div>)
	}
	
	
	const createBlogForm = () => {
		return (
				<form onSubmit={publishBlog}>
					<div className={"form-group"}>
						<label className="text-muted">제목</label>
						<input type="text" className="form-control" value={title} onChange={handleChangeTitle}/>
					</div>
					<div className="form-group">
						<ReactQuill
								modules={BlogCreate.modules}
								formats={BlogCreate.formats}
								theme="snow"
								onChange={handleChangeBody} value={body}
								placeholder={"본문을 입력해주세요"}/>
					</div>
					<div>
						<button type="submit" className="btn btn-primary">저장</button>
					</div>
				</form>)
	}
	return (
			<div className="container-fluid">
				<div className="row">
					<div className="col-md-8">
						<h2>블로그 생성</h2>
						{createBlogForm()}
						<div className="pt-3">
							{showError()}
							{showSuccess()}
						</div>
					</div>
					<div className="col-md-4">
						<div>
							<div className="form-group pb-2">
								<h5>이미지</h5>
								<hr/>
								<small className="text-muted">Max size: 10mb</small>
								<label className="btn btn-outline-info">이미지 업로드
									<input hidden type="file" accept="image/*" onChange={handleChangeFile}/>
								</label>
							</div>
						</div>
						<div>
							<h5>카테고리</h5>
							<hr/>
							<ul style={{maxHeight: "200px", overflowY: "auto"}}>{showCategories()}</ul>
						</div>
						<div>
							<h5>태그</h5>
							<hr/>
							<ul style={{maxHeight: "200px", overflowY: "auto"}}>{showTags()}</ul>
						</div>
					</div>
				</div>
			</div>
	);
};

BlogCreate.modules = {
	toolbar: [
		[{'header': [1, 2, 3, 4, 5, 6]}],
		[{size: []}],
		['bold', 'italic', 'underline', 'strike', 'blockquote'],
		[{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
		['link', 'image'],
		['clean'],
		["code-block"]
	],
}
BlogCreate.formats = [
	'header',
	'bold', 'italic', 'underline', 'strike', 'blockquote',
	'list', 'bullet', 'indent',
	'link', 'image'
]
export default withRouter(BlogCreate);
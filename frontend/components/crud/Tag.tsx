import React, { useState } from 'react';
import { getRequest } from "../../actions/useRequest.js";
import axios from "axios";
import { API } from "../../config";
import { getAuthConfig } from "../../actions/auth";
import { createTag } from "../../actions/tag";

const Tag = () => {
  
  const [values, setValues] = useState({success: false, removed: false, error: {isError: false, message: ""}});
  const {data: tags, error, mutate, isLoading} = getRequest("/api/v1/tag");
  const [name, setName] = useState("");
  
  const nameHandleChange = (e) => {
    setName(e.target.value);
  }
  const showTag = () => {
    return tags && tags
      .map(category => <button data-bs-toggle="tooltip" data-bs-placement="top" title="두번 클릭시 삭제" onDoubleClick={e => deleteConfirm(category.slug)}
                               className="btn btn-outline-primary mx-1 mt-3" key={`category-${category.id}`}>{category.name}</button>
      )
  }
  
  const deleteConfirm = (slug) => {
    if (window.confirm(`태그를 삭제하시겠습니까?`)) {
      axios.delete(`${API}/api/v1/tag/${slug}`, getAuthConfig())
        .then(() => {
          setValues({removed: true, success: false, error: {isError: false, message: ""}});
          mutate(categories => {
            return categories.filter(category => category.slug !== slug)
          }, false)
        })
        .catch(err => {
          setValues({removed: true, success: false, error: {isError: false, message: err.response.data.message}});
        })
    }
  }
  const showSuccess = () => {
    if (values.success) {
      return <p className="text-success">태그를 생성했습니다.</p>
    }
  }
  const showRemove = () => {
    if (values.removed) {
      return <p className="text-info">태그를 삭제했습니다.</p>
    }
  }
  const showError = () => {
    if (values.error.isError) {
      return <p className="text-danger">{values.error.message}</p>
    }
  }
  
  const clickSubmit = async (e) => {
    e.preventDefault();
    const {tag, error} = await createTag(name);
    if (error) {
      setValues({removed: false, success: false, error: {isError: true, message: error.message}});
    } else {
      setValues({removed: false, success: true, error: {isError: false, message: ""}})
      setName("")
      await mutate(currentValue => ([tag, ...currentValue]), false);
    }
  }
  const mouseMoveHandler = (e) => {
    setValues({removed: false, success: false, error: {isError: false, message: ""}})
  }
  
  const newTagForm = () => {
    return <form onSubmit={clickSubmit}>
      <div className="form-group">
        <label className="text-muted">태그 이름</label>
        <input type="text" value={name} className="form-control" onChange={nameHandleChange} required/>
      </div>
      <div>
        <button type="submit" className="btn btn-primary">생성</button>
      </div>
    </form>
  }
  return (
    <>
      {showSuccess()}
      {showError()}
      {showRemove()}
      <div onMouseMove={mouseMoveHandler}>
        {newTagForm()}
        {showTag()}
      </div>
    </>
  );
};

export default Tag;
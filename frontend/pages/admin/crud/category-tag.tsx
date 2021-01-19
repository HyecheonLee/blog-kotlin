import React from 'react';
import Admin from '../../../components/auth/Admin';
import Category from "../../../components/crud/Category";
import Tag from "../../../components/crud/Tag";

const CategoryTag = () => {
  return (
    <Admin>
      <div className="container-fluid">
        <div className="row">
          <div className="col-md-12 py-5">
            <h2>카테고리 & 태그 관리</h2>
          </div>
          <div className="col-md-6">
            <Category/>
          </div>
          <div className="col-md-6">
            <Tag/>
          </div>
        </div>
      </div>
    </Admin>
  );
};

export default CategoryTag;
import React from 'react';
import Admin from '../../../components/auth/Admin';
import BlogCreate from "../../../components/crud/BlogCreate";

const Blog = () => {
  return (
    <Admin>
      <div className="container-fluid">
        <div className="row">
          <div className="col-md-12 py-5">
            <h2>블로그 관리</h2>
          </div>
          <div className="col-md-12">
            <BlogCreate/>
          </div>
        </div>
      </div>
    </Admin>
  );
};

export default Blog;
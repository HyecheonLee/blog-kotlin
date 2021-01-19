import React from 'react';
import Admin from "../../components/auth/Admin";
import Link from 'next/link';

const AdminIndex = () => {
  return (
    <Admin>
      <div className="container-fluid">
        <div className="row">
          <div className="col-md-12 py-5">
            <h2>어드민 패널</h2>
          </div>
          <div className="col-md-4">
            <ul className="list-group">
              <li className="list-group-item">
                <Link href="/admin/crud/category-tag">
                  <a>카테고리 관리기</a>
                </Link>
              </li>
              <li className="list-group-item">
                <Link href="/admin/crud/category-tag">
                  <a> 태그 관리기</a>
                </Link>
              </li>
            </ul>
          </div>
          <div className="col-md-8">
            right
          </div>
        </div>
      </div>
    </Admin>
  );
};

export default AdminIndex;
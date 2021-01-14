import React from 'react';
import SingInComponent from "../../components/user/SingInComponent";

const SingIn = () => {
  return (
    <>
      <h2 className="text-center pt-4 pb-4">로그인 페이지</h2>
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <SingInComponent/>
        </div>
      </div>
    </>
  );
};

export default SingIn;
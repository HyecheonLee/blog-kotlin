import React from 'react';
import SingUpComponent from "../../components/user/SingUpComponent";

const SingUp = () => {
  return (
    <>
      <h2 className="text-center pt-4 pb-4">회원가입 페이지</h2>
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <SingUpComponent/>
        </div>
      </div>
    </>
  );
};

export default SingUp;
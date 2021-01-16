import React from 'react';
import SignUpComponent from "../../components/user/SignUpComponent";

const SignUp = () => {
  return (
    <>
      <h2 className="text-center pt-4 pb-4">회원가입 페이지</h2>
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <SignUpComponent/>
        </div>
      </div>
    </>
  );
};

export default SignUp;
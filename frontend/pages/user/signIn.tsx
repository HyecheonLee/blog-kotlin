import React from 'react';
import SignInComponent from "../../components/user/SignInComponent";

const SignIn = () => {
  return (
    <>
      <h2 className="text-center pt-4 pb-4">로그인 페이지</h2>
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <SignInComponent/>
        </div>
      </div>
    </>
  );
};

export default SignIn;
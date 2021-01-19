import React, { useState } from 'react';
import { isAdmin, signIn } from "../../actions/auth";
import { useRouter } from "next/router";

const SignInComponent = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const router = useRouter()
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    await signIn({email, password})
      .then(data => {
        setLoading(false);
        if (data.error) {
          setError(data.error);
        } else {
          if (isAdmin()) {
            router.push("/admin")
          } else {
            router.push("/user")
          }
        }
      })
      .catch(data => {
        setLoading(false);
        setError(data.error);
      })
  }
  const handleChange = func => (e) => {
    func(e.target.value)
  }
  const singInForm = () => {
    return (
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <input value={email} name="email" onChange={handleChange(setEmail)} type="email" className="form-control" placeholder="이메일을 입력해 주세요"/>
        </div>
        <div className="form-group">
          <input value={password} name="password" onChange={handleChange(setPassword)} type="password" className="form-control" placeholder="비밀번호을 입력해 주세요"/>
        </div>
        <div>
          <button className="btn btn-primary">로그인</button>
        </div>
      </form>
    )
  }
  const showLoading = () => loading ? <div className="alert alert-info">Loading...</div> : ''
  const showError = () => error ? <div className="alert alert-danger">{error}</div> : ''
  
  return (
    <>
      {showError()}
      {showLoading()}
      {singInForm()}
    </>
  );
};

export default SignInComponent;
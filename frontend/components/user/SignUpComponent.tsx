import React, { useState } from 'react';
import { signUp } from "../../actions/auth";

const SignUpComponent = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [showForm, setShowForm] = useState(true);
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const {message, error} = await signUp({username, email, password});
    setLoading(false);
    setError(error);
    setMessage(message);
    if (!error) {
      setShowForm(false)
    }
  }
  
  const handleChange = func => (e) => {
    func(e.target.value)
  }
  const showLoading = () => loading ? <div className="alert alert-info">Loading...</div> : ''
  const showError = () => error ? <div className="alert alert-danger">{error}</div> : ''
  const showMessage = () => message ? <div className="alert alert-info">{message}</div> : ''
  
  const singUpForm = () => {
    return (
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <input value={username} name="username" onChange={handleChange(setUsername)} type="text" className="form-control" placeholder="이름을 입력해 주세요"/>
        </div>
        <div className="form-group">
          <input value={email} name="email" onChange={handleChange(setEmail)} type="email" className="form-control" placeholder="이메일을 입력해 주세요"/>
        </div>
        <div className="form-group">
          <input value={password} name="password" onChange={handleChange(setPassword)} type="password" className="form-control" placeholder="비밀번호을 입력해 주세요"/>
        </div>
        <div>
          <button className="btn btn-primary">회원가입</button>
        </div>
      </form>
    )
  }
  return (
    <>
      {showError()}
      {showLoading()}
      {showMessage()}
      {showForm && singUpForm()}
    </>
  );
};

export default SignUpComponent;
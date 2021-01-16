import React from 'react';
import { useRecoilValue } from "recoil";
import { userState } from "../../states/UserState";

const AdminIndex = () => {
  const user = useRecoilValue(userState);
  return (
    <>
      <h2>어드민 패널{user.username}</h2>
    </>
  );
};

export default AdminIndex;
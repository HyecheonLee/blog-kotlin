import React from 'react';
import Private from "../../components/auth/Private";

const UserIndex = () => {
  return (
    <>
      <Private>
        <h2>사용자 패널</h2>
      </Private>
    </>
  );
};

export default UserIndex;
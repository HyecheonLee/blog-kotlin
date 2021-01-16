import { atom } from "recoil";

export type User = {
  id: number;
  username: string;
  name: string | null;
  email: string,
  profile: string | null,
  about: string | null,
  roles: string[];
  photo: string | null;
}
export const initUser = {
  id: -1,
  username: "",
  name: "",
  email: "",
  profile: "",
  about: "",
  roles: [],
  photo: "",
};
const userState = atom<User>({
  key: "userState",
  default: initUser
});
export { userState }